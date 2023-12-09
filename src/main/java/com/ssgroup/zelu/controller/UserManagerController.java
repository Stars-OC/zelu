package com.ssgroup.zelu.controller;

import com.ssgroup.zelu.aop.RequestUser;
import com.ssgroup.zelu.aop.permission.Permission;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.User;
import com.ssgroup.zelu.pojo.type.Role;
import com.ssgroup.zelu.service.UserManagerService;
import com.ssgroup.zelu.service.UserService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/manager/user")
@Validated
public class UserManagerController {

    @Autowired
    private UserManagerService userManagerService;

    @Autowired
    private UserService userService;


    @Permission(Role.ADMIN)
    @RequestMapping("/info/getUsers")
    public Result<PageList<User>> get(@RequestParam(required = false, defaultValue = "normal") String type,
                                      @RequestParam(required = false, defaultValue = "1") int page,
                                      @RequestParam(required = false, defaultValue = "10") int size) {
        PageList<User> pageList = userManagerService.getUsers(page, size);
        return pageList != null?
                Result.success(pageList) : Result.failure("查询失败");
    }

    @Permission(Role.ADMIN)
    @RequestMapping("/add")
    public Result<User> add(@RequestBody User user) {
        try {
            userManagerService.addUser(user);
            return Result.success("修改成功");
        }catch (Exception e){
            return Result.failure("修改失败");
        }
    }

    @Permission(Role.ADMIN)
    @PostMapping("/update")
    public Result<String> update(@RequestBody User user) {
        try {
            userManagerService.updateUser(user);
            return Result.success("修改成功");
        }catch (Exception e){
            return Result.failure("修改失败");
        }
    }

    @Permission(Role.ADMIN)
    @GetMapping("/delete")
    public Result<String> delete(@RequestParam @NotNull Long[] users) {
        int count = userManagerService.deleteUsers(users);
        return count > 0?
                Result.success("删除 [" + count + "/" + users.length + "] 成功") : Result.failure("删除失败");
    }

    @RequestMapping("/test")
    @RequestUser("token")
    public User test(User user){
        return user;
    }

}
