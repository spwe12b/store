package com.me.store.service;

import com.me.store.common.ServerResponse;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {
    ServerResponse upload(MultipartFile file, String path);
}
