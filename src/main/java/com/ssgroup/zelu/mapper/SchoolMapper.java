package com.ssgroup.zelu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssgroup.zelu.pojo.department.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SchoolMapper extends BaseMapper<School> {
    @Update("update school_info set deleted = #{deleted} where school_id = #{schoolId}")
    void updateDeleted(long schoolId,int deleted);
}
