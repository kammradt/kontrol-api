package com.kammradt.learning.listeners;

import com.kammradt.learning.domain.RequestFile;
import com.kammradt.learning.service.s3.S3Service;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.PreRemove;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class RequestFileListener {

    S3Service s3Service;

    @PreRemove
    void removing(RequestFile requestFile) {
        s3Service.delete(requestFile.getS3Name());
    }

}
