package com.kammradt.learning.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kammradt.learning.file.dtos.RequestFileDTO;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class S3Service {
    private AmazonS3 s3;
    private String bucketName;
    private String region;

    @Autowired
    public S3Service(AmazonS3 s3, String awsS3Bucket, String awsRegion) {
        this.s3 = s3;
        this.bucketName = awsS3Bucket;
        this.region = awsRegion;
    }

    public List<RequestFileDTO> uploadMultipleFiles(List<MultipartFile> files) {
        return files
                .stream()
                .map(this::uploadFileToS3)
                .collect(Collectors.toList());
    }

    @SneakyThrows
    private RequestFileDTO uploadFileToS3(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String s3FileName = generateUniqueFileName(file.getOriginalFilename());

        ObjectMetadata metadata = setObjectmetadata(file);

        PutObjectRequest request = new PutObjectRequest(this.bucketName, s3FileName, file.getInputStream(), metadata)
                .withCannedAcl(CannedAccessControlList.PublicRead);

        s3.putObject(request);

        String location = getFileLocacation(s3FileName);
        return new RequestFileDTO(originalFilename, s3FileName, location);
    }

    private String getFileLocacation(String filename) {
        return "https://" + this.bucketName + ".s3." + this.region + ".amazonaws.com/" + filename;
    }

    private ObjectMetadata setObjectmetadata(MultipartFile file) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(file.getSize());
        return metadata;
    }

    private String generateUniqueFileName(String filename) {
        return UUID.randomUUID().toString() + "_" + filename;
    }

    public void delete(String S3filename) {
        this.s3.deleteObject(bucketName, S3filename);
    }
}
