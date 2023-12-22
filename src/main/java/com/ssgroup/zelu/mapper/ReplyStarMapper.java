package com.ssgroup.zelu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssgroup.zelu.pojo.discuss.ReplyStar;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReplyStarMapper extends BaseMapper<ReplyStar> {

    @Override
    @Delete("delete from reply_star where content_id = #{contentId} and username = #{username}")
    int deleteById(ReplyStar entity);


    @Select("select count(*) from reply_star where content_id = #{contentId}")
    int getStarsByContentId(Long contentId);
}
