package com.ssgroup.zelu.controller.manager.admin;

import com.ssgroup.zelu.annotation.RequestToken;
import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.department.School;
import com.ssgroup.zelu.pojo.type.Role;
import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.service.manager.UserManagerService;
import com.ssgroup.zelu.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin/api/manager/user")
@Validated
@Permission(Role.ADMIN)
@Slf4j
public class AdminManagerController {

    @Autowired
    private UserManagerService userManagerService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private InfoContrller infoContrller;

    /**
     * 添加用户利用User对象
     *
     * HTTP POST请求方法
     * 请求路径：/user/add
     * 请求参数：
     *   - User对象（请求体）
     * 返回结果：
     *   - Result<User>对象
     */
    @PostMapping("/add")
    public Result<User> add(@Validated @RequestBody User user) {
        try {
            userManagerService.addUserByAdmin(user);
            return Result.success("添加成功");
        } catch (Exception e) {
            log.warn(e.getMessage());
            return Result.failure("添加失败");
        }
    }

    /**
     * 更新用户信息
     *
     * 请求路径：/user/update
     * 请求方法：POST
     * 参数：
     *   - user: 当前用户信息
     *   - userData: 待更新的用户信息
     * 返回值：Result<String>
     *   - success: 修改成功
     *   - failure: 修改失败
     */
    @PostMapping("/update")
    public Result<String> update(@Validated @RequestBody User userData) {
        try {
            userManagerService.updateUser(userData);
            return Result.success("修改成功");
        }catch (Exception e){
            log.warn(e.getMessage());
            return Result.failure("修改失败");
        }
    }

    /**
     * 删除用户
     *
     * @param users 需要删除的用户ID数组
     * @return 结果对象，包含操作是否成功的提示信息
     */
    @GetMapping("/delete")
    public Result<String> delete(@RequestParam @NotNull Long[] users) {
        // 调用ManagerService的deleteUsers方法删除用户
        int count = userManagerService.deleteUsers(users);
        // 判断删除结果，如果删除成功返回成功提示信息，否则返回删除失败提示信息
        return count > 0?
                Result.success("删除成功 [" + count + "/" + users.length + "]") : Result.failure("删除失败");
    }

    @RequestMapping("/{test}")
    public Object test(@RequestToken("school") School school){
        return school;
    }

}
