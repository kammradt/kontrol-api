package com.kammradt.learning.project.dtos;

import com.kammradt.learning.file.entities.File;
import com.kammradt.learning.task.entities.Status;
import com.kammradt.learning.task.entities.Task;
import com.kammradt.learning.user.entities.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Builder
@Data
public class ProjectResponse implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Date creationDate;
    private User user;
    private Status status;
    private Date start;
    private Date end;
    private List<Task> tasks;
    private List<File> files;
    private boolean willBeDelayed;
    private Float completionPercentage;

}
