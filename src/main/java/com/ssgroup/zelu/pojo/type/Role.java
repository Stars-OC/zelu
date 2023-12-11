package com.ssgroup.zelu.pojo.type;

import lombok.Getter;

public enum Role {

    /**
     * 普通用户
     */
    USER(0),
    /**
     * 学生
     */
    STUDENT(1),
    /**
     * 教师
     */
    TEACHER(2),
    /**
     * 学校管理员
     */
    SCHOOL_ADMIN(3),
    /**
     * 管理员
     */
    ADMIN(4),
    ;

    Role(int role) {
        this.role = role;
    }


    @Getter
    private int role;

}
