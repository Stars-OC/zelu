package com.ssgroup.zelu.pojo.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SchoolAndCourseId {
    @NotNull
    private long schoolId;

    private long courseId;
}
