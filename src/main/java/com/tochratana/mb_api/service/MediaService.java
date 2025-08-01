package com.tochratana.mb_api.service;

import com.tochratana.mb_api.dto.MediaResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {
    /**
     * upload single file
     * @param file from HTTP request
     * @return MediaResponse
     */
    MediaResponse upload(MultipartFile file);
}
