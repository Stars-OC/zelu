package com.ssgroup.zelu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssgroup.zelu.pojo.department.School;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface SchoolMapper extends BaseMapper<School> {
    /**
     * 更新学校是否删除
     *
     * @param id 学校id
     * @param deleted 是否删除(0,1)
     */
    @Update("update school_info set deleted = #{deleted} where school_id = #{id}")
    void updateDeleted(long id,int deleted);
}
