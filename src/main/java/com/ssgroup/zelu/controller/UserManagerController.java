package com.ssgroup.zelu.controller;

import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.service.UserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/user/manager")
public class UserManagerController {

    @Autowired
    private UserManagerService userManagerService;

    @RequestMapping("/getUsers")
    public Result<PageList<User>> get(@RequestHeader String token,
                                      @RequestParam String type,
                                      @RequestParam(required = false, defaultValue = "1") int page,
                                      @RequestParam(required = false, defaultValue = "10") int size) {
        return userManagerService.getUsers(token,type,page,size);
    }

}
