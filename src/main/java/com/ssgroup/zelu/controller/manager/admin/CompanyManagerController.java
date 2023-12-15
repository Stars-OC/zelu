package com.ssgroup.zelu.controller.manager.admin;

import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.pojo.type.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin/api/manager/company")
@Validated
@Permission(Role.ADMIN)
@Slf4j
public class CompanyManagerController {
}
