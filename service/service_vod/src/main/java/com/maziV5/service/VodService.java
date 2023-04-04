package com.maziV5.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface VodService {
    String uploadVideo(MultipartFile file);

    void deleteVideo(String id);

    void deleteMoreVideo(List idList);
}
