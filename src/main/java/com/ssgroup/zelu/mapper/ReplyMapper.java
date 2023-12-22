package com.ssgroup.zelu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssgroup.zelu.pojo.discuss.Reply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReplyMapper extends BaseMapper<Reply> {

    @Select("select score from reply where content_id = #{contentId}")
    Integer getScore(Long contentId);
}
