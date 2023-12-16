package com.ssgroup.zelu.controller.manager.admin;

import com.ssgroup.zelu.annotation.Permission;
import com.ssgroup.zelu.pojo.Result;
import com.ssgroup.zelu.pojo.department.School;
import com.ssgroup.zelu.pojo.type.Role;
import com.ssgroup.zelu.service.manager.SchoolManagerService;
import jakarta.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin/api/manager/school")
@Validated
@Permission(Role.ADMIN)
@Slf4j
public class ManagerController {

    @Autowired
    private SchoolManagerService schoolManagerService;


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
    @PostMapping("/add")
    public Result<School> addSchool(@RequestBody School school) {
        try {
            schoolManagerService.addByAdmin(school);
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.failure("修改失败");
        }
    }

    /**
     * 更新学校信息
     *
     * @param schoolData 更新的学校信息
     * @return 结果对象，如果成功更新返回"修改成功"，否则返回"修改失败"
     */
    @PostMapping("/update")
    public Result<String> updateSchool(School school, @RequestBody School schoolData) {
        try {
            schoolManagerService.updateByAdmin(schoolData);
            log.info(school.getSchoolName());
            return Result.success("修改成功");
        } catch (Exception e) {
            return Result.failure("修改失败");
        }
    }

    /**
     * 删除学校
     *
     * @param schools 学校ID数组
     * @return 结果对象，包含操作是否成功的信息和提示消息
     */
    @GetMapping("/delete")
    public Result<String> deleteSchool(@RequestParam @NotNull Long[] schools) {
        // 调用schoolManagerService的删除学校方法，返回删除的学校数量
        int count = schoolManagerService.deleteByAdmin(schools);
        // 如果删除的学校数量大于0，则表示删除成功，返回成功的结果对象；否则返回删除失败的结果对象
        return count > 0 ?
                Result.success("删除 [" + count + "/" + schools.length + "] 成功") :
                Result.failure("删除失败");
    }
}
