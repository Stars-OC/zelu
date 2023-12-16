package com.ssgroup.zelu.controller.manager.admin;

import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.annotation.RequestPage;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.department.Course;
import com.ssgroup.zelu.pojo.department.School;
import com.ssgroup.zelu.pojo.type.Role;
import com.ssgroup.zelu.pojo.user.User;
import com.ssgroup.zelu.service.UserService;
import com.ssgroup.zelu.service.manager.CourseManagerService;
import com.ssgroup.zelu.service.manager.SchoolManagerService;
import com.ssgroup.zelu.service.manager.UserManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/info")
public class InfoContrller {
    @Autowired
    private SchoolManagerService schoolManagerService;

    @Autowired
    private UserManagerService userManagerService;

    @Autowired
    private CourseManagerService courseManagerService;

    @Autowired
    private UserService userService;

    /**
     * 获取信息
     *
     * @param type 筛选类型（默认为"normal"）
     * @param page 分页数组，默认为 page[0] -> page页 = 1 page[1] -> limit数量 = 10
     * @param path 路径 用来接受/info下不同的对象
     *
     * @return 结果对象，包含用户信息和查询结果状态
     */
    @GetMapping("/manager/{path}")
    @Permission(Role.ADMIN)
    public Result<PageList<?>> getInfoByAdmin(@RequestParam(required = false, defaultValue = "normal") String type,
                                              @RequestPage int[] page,
                                              @PathVariable String path) {
        PageList<?> pageList = null;
        switch (path){
            case "users":
                pageList = userManagerService.getUsers(page[0], page[1]);
                break;
            case "schools":
                pageList = schoolManagerService.getSchools(page[0], page[1]);
                break;
            case "courses":
                pageList = courseManagerService.getAllCourses(page[0], page[1]);
                break;
            default:
                break;
        }
        // 如果页面列表不为空，返回查询成功的结果对象，否则返回查询失败的结果对象
        return pageList != null?
                Result.success(pageList) : Result.failure("查询失败");
    }


}
