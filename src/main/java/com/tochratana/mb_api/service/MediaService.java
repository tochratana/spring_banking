package com.tochratana.mb_api.service;

import com.tochratana.mb_api.dto.MediaResponse;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MediaService {
    /**
     * upload single file
     * @param file from HTTP request
     * @return MediaResponse
     */
    MediaResponse upload(MultipartFile file);

    /**
     * upload multiple files
     * @param files array of files from HTTP request
     * @return List of MediaResponse
     */
    List<MediaResponse> uploadMultiple(MultipartFile[] files);

    /**
     * download media by filename (name + extension)
     * @param filename the filename with extension
     * @return Resource containing the file data
     */
    Resource downloadByName(String filename);

    /**
     * delete media by filename (name + extension) - soft delete
     * @param filename the filename with extension
     * @return boolean indicating success
     */
    boolean deleteByName(String filename);
}
