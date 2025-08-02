package com.tochratana.mb_api.service.impl;

import com.tochratana.mb_api.domain.Media;
import com.tochratana.mb_api.dto.MediaResponse;
import com.tochratana.mb_api.repository.MediaRepository;
import com.tochratana.mb_api.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MediaServiceImpl implements MediaService {

    @Value("${media.server-path}")
    private String serverPath;

    @Value("${media.base-uri}")
    private String baseUri;

    private final MediaRepository mediaRepository;

    @Override
    public MediaResponse upload(MultipartFile file) {

        String name = UUID.randomUUID().toString();

        // find last index of dot
        int lastIndex = Objects.requireNonNull(file.getOriginalFilename()).lastIndexOf('.');

        // extract extension
        String extension = file.getOriginalFilename().substring(lastIndex + 1);

        // create a path object
        Path path = Paths.get(serverPath + String.format("%s.%s", name, extension));

        try {
            Files.copy(file.getInputStream(), path);
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "File upload fail");
        }

        Media media = new Media();
        media.setName(name);
        media.setExtension(extension);
        media.setMimeTypeFIle(file.getContentType());
        media.setIsDeleted(false);


        mediaRepository.save(media);
        return MediaResponse.builder()
                .name(media.getName())
                .mimeType(media.getMimeTypeFIle())
                .size(file.getSize())
                .uri(baseUri + String.format("%s.%s", name, extension))
                .build();
    }

    @Override
    public List<MediaResponse> uploadMultiple(MultipartFile[] files) {
        if (files == null || files.length == 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No files provided");
        }

        List<MediaResponse> responses = new ArrayList<>();
        List<Media> mediaList = new ArrayList<>();

        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue; // Skip empty files
            }

            try {
                validateFile(file);

                String name = UUID.randomUUID().toString();
                String extension = extractExtension(file.getOriginalFilename());

                Path path = Paths.get(serverPath + String.format("%s.%s", name, extension));
                Files.copy(file.getInputStream(), path);

                Media media = new Media();
                media.setName(name);
                media.setExtension(extension);
                media.setMimeTypeFIle(file.getContentType());
                media.setIsDeleted(false);

                mediaList.add(media);

                responses.add(MediaResponse.builder()
                        .name(media.getName())
                        .mimeType(media.getMimeTypeFIle())
                        .size(file.getSize())
                        .uri(baseUri + String.format("%s.%s", name, extension))
                        .build());

            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Failed to upload file: " + file.getOriginalFilename());
            }
        }

        // Save all media records in batch
        mediaRepository.saveAll(mediaList);

        return responses;
    }

    @Override
    public Resource downloadByName(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filename cannot be empty");
        }

        Media media = mediaRepository.findByFilenameAndIsDeletedFalse(filename)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + filename));

        try {
            Path filePath = Paths.get(serverPath).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found or not readable: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error accessing file: " + filename);
        }
    }

    @Override
    public boolean deleteByName(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Filename cannot be empty");
        }

        Media media = mediaRepository.findByFilenameAndIsDeletedFalse(filename)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found: " + filename));

        // Soft delete - mark as deleted
        media.setIsDeleted(true);
        mediaRepository.save(media);

        // Optionally, you can also delete the physical file
        try {
            Path filePath = Paths.get(serverPath).resolve(filename).normalize();
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // Log the error but don't fail the operation since DB record is marked as deleted
            System.err.println("Warning: Could not delete physical file: " + filename + ". Error: " + e.getMessage());
        }

        return true;
    }

    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File cannot be empty");
        }

        if (file.getOriginalFilename() == null || !file.getOriginalFilename().contains(".")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File must have a valid extension");
        }
    }

    private String extractExtension(String filename) {
        int lastIndex = Objects.requireNonNull(filename).lastIndexOf('.');
        if (lastIndex == -1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File must have an extension");
        }
        return filename.substring(lastIndex + 1);
    }
}
