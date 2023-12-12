package com.ssgroup.zelu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssgroup.zelu.pojo.department.Course;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {

    /**
     * 更新课程是否删除
     *
     * @param id 课程id
     * @param deleted 是否删除(0,1)
     */
    @Update("update course_info set deleted = #{deleted} where course_info = #{id}")
    void updateDeleted(long id,int deleted);
}
