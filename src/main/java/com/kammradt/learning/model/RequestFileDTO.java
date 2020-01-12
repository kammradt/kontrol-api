package com.kammradt.learning.model;

import com.kammradt.learning.domain.RequestFile;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class RequestFileDTO {
    private String name;
    private String S3Name;
    private String location;

    public RequestFile toRequestFile() {
        RequestFile requestFile = new RequestFile();
        requestFile.setName(this.name);
        requestFile.setS3Name(this.S3Name);
        requestFile.setLocation(this.location);
        return requestFile;
    }
}
