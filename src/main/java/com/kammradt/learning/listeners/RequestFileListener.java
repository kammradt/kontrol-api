package com.kammradt.learning.listeners;

import com.kammradt.learning.domain.RequestFile;
import com.kammradt.learning.service.s3.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.PreRemove;

@Component
public class RequestFileListener {

    @Autowired S3Service s3Service;

    @PreRemove
    void removing(RequestFile requestFile) {
        s3Service.delete(requestFile.getS3Name());
    }

}
