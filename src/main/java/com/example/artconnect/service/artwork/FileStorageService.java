package com.example.artconnect.service.artwork;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;

    @Value("${aliyun.oss.aliDomain}")
    private String aliDomain;

    public  String  storeFile(MultipartFile file) throws IOException {
        String originFileName = file.getOriginalFilename();
        String ext = "." + FilenameUtils.getExtension(originFileName);
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String fileName = uuid + ext;

        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        ossClient.putObject(bucketName, fileName, file.getInputStream(), metadata);
        ossClient.shutdown();
         // try-with-resources自动关闭资源

        return aliDomain + fileName;
    }
}
