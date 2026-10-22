package com.lmy.controller;

import com.lmy.pojo.Result;
import com.lmy.utils.AliyunOSSOperator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class UPloadController {
    /**
     * 本地磁盘存储方案
     */
/*    @RequestMapping("/upload")
    public Result upload(String name, Integer age, MultipartFile file) throws IOException {
        log.info("接收参数：{}{}{}", name, age, file);
        //1.接受文件名
        String fileName = file.getOriginalFilename();

        //新的文件名
        String extension = fileName.substring(fileName.lastIndexOf("."));
        String newFileName = UUID.randomUUID().toString() + extension;
        //2.把文件保存到本地
        file.transferTo(new File("D:/images/" + newFileName));
        return Result.success();
    }*/

    /**
     * 阿里云OSS存储方案
     */
    @Autowired
    private AliyunOSSOperator aliyunOSSOperator;

    @RequestMapping("/upload")
    public Result upload(MultipartFile file) throws Exception {
        log.info("文件上传：{}", file);
        //将文件交给阿里云OSS
        String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传结果,url：{}", url);
        return Result.success(url);
    }
}
