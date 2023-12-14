package com.ssgroup.zelu.controller;

import com.ssgroup.zelu.annotation.RequestPage;
import com.ssgroup.zelu.annotation.RequestUser;
import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.config.PermissionConfig;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.type.Role;
import com.ssgroup.zelu.pojo.department.School;
import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.service.ManagerService;
import com.ssgroup.zelu.service.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("admin/api/manager")
@Validated
@Permission(Role.ADMIN)
@Slf4j
public class ManagerController {

    @Autowired
    private ManagerService ManagerService;

    @Autowired
    private UserService userService;

    /**
     * 获取用户信息
     *
     * @param type 筛选类型（默认为"normal"）
     * @param page 分页数组，默认为page[0]page页 = 1 page[1]limit数量 = 10
     * @return 结果对象，包含用户信息和查询结果状态
     */
    @GetMapping("/info/users")
    public Result<PageList<User>> getUsers(@RequestParam(required = false, defaultValue = "normal") String type,
                                           @RequestPage(limit = 5) int[] page) {
        // 调用ManagerService的getUsers方法获取用户列表
        PageList<User> pageList = ManagerService.getUsers(page[0], page[1]);
        // 如果页面列表不为空，返回查询成功的结果对象，否则返回查询失败的结果对象
        return pageList != null?
                Result.success(pageList) : Result.failure("查询失败");
    }


    /**
     * 获取学校信息
     *
     * @param type 查询类型，可选值为"normal"（默认值）和"all"，表示是否包括已关闭的学校，默认为"normal"
     * @param page 分页数组，默认为page[0]page页 = 1 page[1]limit数量 = 10
     * @return 结果对象，包含学校信息和分页信息
     */
    @GetMapping("/info/schools")
    public Result<PageList<School>> getSchools(@RequestParam(required = false, defaultValue = "normal") String type,
                                               @RequestPage() int[] page) {
        // 调用ManagerService的getSchools方法获取学校信息
        PageList<School> pageList = ManagerService.getSchools(page[0], page[1]);
        // 如果获取到学校信息，则返回成功结果，否则返回查询失败结果
        return pageList != null ?
                Result.success(pageList) :
                Result.failure("查询失败");
    }


    /**
     * 添加用户
     *
     * HTTP POST请求方法
     * 请求路径：/user/add
     * 请求参数：
     *   - User对象（请求体）
     * 返回结果：
     *   - Result<User>对象
     */
    @PostMapping("/user/add")
    public Result<User> add(@RequestBody User user) {
        try {
            ManagerService.addUser(user);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.failure("修改失败");
        }
    }


    /**
     * 添加学校
     *
     * HTTP POST请求
     * 请求路径：/school/add
     * 请求参数：School对象，包含学校信息
     * 返回值：Result<School>对象，包含操作结果和学校信息
     *
     * @param school 请求参数School对象
     * @return 返回Result<School>对象
     */
    @PostMapping("/school/add")
    public Result<School> addSchool(@RequestBody School school) {
        try {
            ManagerService.addSchool(school);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.failure("修改失败");
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
    @PostMapping("/user/update")
    public Result<String> update(@RequestUser User user,@RequestBody User userData) {
        try {
            ManagerService.updateUser(userData);
            log.info(user.getNickname());
            return Result.success("修改成功");
        }catch (Exception e){
            return Result.failure("修改失败");
        }
    }


    /**
     * 更新学校信息
     *
     * @param schoolData 更新的学校信息
     * @return 结果对象，如果成功更新返回"修改成功"，否则返回"修改失败"
     */
    @PostMapping("/school/update")
    public Result<String> updateSchool(School school, @RequestBody School schoolData) {
        try {
            ManagerService.updateSchool(schoolData);
            log.info(school.getSchoolName());
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.failure("修改失败");
        }
    }


    /**
     * 删除用户
     *
     * @param users 需要删除的用户ID数组
     * @return 结果对象，包含操作是否成功的提示信息
     */
    @GetMapping("/user/delete")
    public Result<String> delete(@RequestParam @NotNull Long[] users) {
        // 调用ManagerService的deleteUsers方法删除用户
        int count = ManagerService.deleteUsers(users);
        // 判断删除结果，如果删除成功返回成功提示信息，否则返回删除失败提示信息
        return count > 0?
                Result.success("删除 [" + count + "/" + users.length + "] 成功") : Result.failure("删除失败");
    }


    /**
     * 删除学校
     *
     * @param schools 学校ID数组
     * @return 结果对象，包含操作是否成功的信息和提示消息
     */
    @GetMapping("/school/delete")
    public Result<String> deleteSchool(@RequestParam @NotNull Long[] schools) {
        // 调用ManagerService的删除学校方法，返回删除的学校数量
        int count = ManagerService.deleteSchools(schools);
        // 如果删除的学校数量大于0，则表示删除成功，返回成功的结果对象；否则返回删除失败的结果对象
        return count > 0 ?
                Result.success("删除 [" + count + "/" + schools.length + "] 成功") :
                Result.failure("删除失败");
    }




    @RequestMapping("/test")
    public User test(@RequestUser User user){
        return user;
    }

}
