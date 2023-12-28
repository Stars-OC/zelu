package com.ssgroup.zelu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssgroup.zelu.pojo.department.Course;
import com.ssgroup.zelu.pojo.request.SchoolAndCourseId;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 更新课程是否删除
     *
     * @param id 课程id
     * @param deleted 是否删除(0,1)
     */
    @Update("update course set deleted = #{deleted} where course_info = #{id}")
    void updateDeleted(long id,int deleted);

    /**
     * 检查用户是否有权限访问课程
     *
     * @param username 用户名
     * @param schoolAndCourseId 封装课程和学校id
     */
    @Select("select role from course,course_user where course.course_id = course_user.course_id = #{schoolAndCourseId.courseId} and course_user.username = #{username} and course.school_id = #{schoolAndCourseId.schoolId}")
    Integer getCourseRoleToCheck(long username, SchoolAndCourseId schoolAndCourseId);

}
