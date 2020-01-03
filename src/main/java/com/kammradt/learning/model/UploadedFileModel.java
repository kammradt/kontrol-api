package com.kammradt.learning.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class UploadedFileModel {
    private String name;
    private String location;
}
