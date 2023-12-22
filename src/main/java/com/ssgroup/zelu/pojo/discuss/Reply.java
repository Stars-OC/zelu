package com.ssgroup.zelu.pojo.discuss;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("reply")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reply {

    @TableId(type = IdType.AUTO)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long contentId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long discussId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long username;

    private String content;

    private String resources;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long updateAt;

    @TableField(fill = FieldFill.INSERT,updateStrategy = FieldStrategy.NEVER)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Long createAt;

    private Long replyId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer stars;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer score;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Integer deleted;

}
