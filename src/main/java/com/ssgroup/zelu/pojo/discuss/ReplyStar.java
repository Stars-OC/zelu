package com.ssgroup.zelu.pojo.discuss;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("reply_star")
public class ReplyStar {
    private Long contentId;
    private Long username;
}
