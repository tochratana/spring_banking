package com.tochratana.mb_api.controller;

import com.tochratana.mb_api.dto.MediaResponse;
import com.tochratana.mb_api.service.MediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/v1/medias")
@RequiredArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping
    public MediaResponse upload(@RequestPart MultipartFile multipartFile){
        return mediaService.upload(multipartFile);
    }
}
