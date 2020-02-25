package com.kammradt.learning.file;

import com.kammradt.learning.file.entities.RequestFile;
import com.kammradt.learning.s3.S3Service;
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
