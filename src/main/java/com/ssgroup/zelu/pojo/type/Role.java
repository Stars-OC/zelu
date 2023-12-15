package com.ssgroup.zelu.pojo.type;

import lombok.Getter;

public enum Role {

    /**
     * 普通用户
     */
    USER(0),
    /**
     * 课堂助手(班长)
     */
    COURSE_ASSISTANT(1),
    /**
     * 教师
     */
    TEACHER(2),
    /**
     * 公司管理员
     */
    COMPANY_ADMIN(3),
    /**
     * 学校管理员
     */
    SCHOOL_ADMIN(4),
    /**
     * 超级管理员
     */
    ADMIN(5),
    ;

    Role(int role) {
        this.role = role;
    }


    @Getter
    private int role;

}
