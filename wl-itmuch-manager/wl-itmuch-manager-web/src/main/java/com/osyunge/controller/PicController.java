package com.osyunge.controller;

import com.osyunge.dataobject.PicResult;
import com.osyunge.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 * 在添加商品页面实现图片上传
 */
@Controller
public class PicController {
    @Autowired
    private PictureService pictureService;
    @RequestMapping("/pic/upload")
    @ResponseBody
    public PicResult uploadFile(MultipartFile uploadFile){
        return pictureService.uploadFile(uploadFile);
    }
}
