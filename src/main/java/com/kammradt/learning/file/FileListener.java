package com.kammradt.learning.file;

import com.kammradt.learning.file.entities.File;
import com.kammradt.learning.s3.S3Service;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.PreRemove;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class FileListener {

    S3Service s3Service;

    @PreRemove
    void removing(File file) {
        s3Service.delete(file.getS3Name());
    }

}
