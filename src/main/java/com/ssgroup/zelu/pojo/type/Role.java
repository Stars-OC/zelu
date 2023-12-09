package com.ssgroup.zelu.pojo.type;

import lombok.Getter;

public enum Role {
    /**
     * 管理员
     */
    ADMIN(1, "管理员"),
    /**
     * 普通用户
     */
    USER(0, "普通用户"),
    ;

    Role(int code, String role) {
        this.code = code;
        this.role = role;
    }


    @Getter
    private int code;

    @Getter
    private String role;

    public static Role getRoleType(int code) {
        for (Role roleType : Role.values()) {
            if (roleType.getCode() == code) {
                return roleType;
            }
        }
        return null;
    }
}
