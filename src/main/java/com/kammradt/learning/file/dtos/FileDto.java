package com.kammradt.learning.file.dtos;

import com.kammradt.learning.file.entities.File;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FileDto {
    private String name;
    private String S3Name;
    private String location;

    public File toFile() {
        File file = new File();
        file.setName(this.name);
        file.setS3Name(this.S3Name);
        file.setLocation(this.location);
        return file;
    }
}
