package com.ssgroup.zelu.config;

import com.ssgroup.zelu.pojo.type.Role;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PermissionConfig {

    public final static  Role[] ROLE_ADMIN = {Role.ADMIN};
}
