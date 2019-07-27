package com.osyunge.service;

import com.osyunge.dataobject.PicResult;
import org.springframework.web.multipart.MultipartFile;

public interface PictureService {
    PicResult uploadFile(MultipartFile picFile);
}
