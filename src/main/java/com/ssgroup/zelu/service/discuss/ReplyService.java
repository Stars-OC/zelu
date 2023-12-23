package com.ssgroup.zelu.service.discuss;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssgroup.zelu.mapper.ReplyMapper;
import com.ssgroup.zelu.mapper.ReplyStarMapper;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.discuss.Reply;
import com.ssgroup.zelu.pojo.discuss.ReplyStar;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.concurrent.atomic.AtomicBoolean;

@Service
@AllArgsConstructor
public class ReplyService {

    private ReplyMapper replyMapper;

    private ReplyStarMapper replyStarMapper;


    /**
     * 根据讨论ID获取回复列表
     * @param discussId 讨论ID
     * @param page 页码
     * @param size 每页显示数量
     * @return 返回回复列表PageList<Reply>
     */
    public PageList<Reply> getReplyByDiscussId(Long discussId, int page, int size) {
        // 创建查询条件封装类
        QueryWrapper<Reply> wrapper = new QueryWrapper<>();
        wrapper.eq("discuss_id", discussId);
        // 创建分页列表页
        Page<Reply> pageList = new Page<>(page, size);
        // 使用Mapper查询分页数据
        Page<Reply> selectPage = replyMapper.selectPage(pageList, wrapper);
        // 创建并返回分页列表对象
        return new PageList<>(selectPage);
    }


    /**
     * 添加回复
     *
     * @param reply 回复对象
     */
    public void addReply(Reply reply) {
        replyMapper.insert(reply);
    }


    /**
     * 更新回复信息
     * @param reply 需要更新的回复对象
     */
    public void updateReply(Reply reply) {
        replyMapper.updateById(reply);
    }


    /**
     * 更新点赞
     *
     * @param contentId 内容ID
     * @param username 用户名
     * @param flag true 为增加 false 为减少
     */
    @Transactional
    public AtomicBoolean updateStar(Long contentId, Long username,boolean flag) {

        // 构建点赞实体对象
        ReplyStar replayStar = ReplyStar.builder()
                .contentId(contentId)
                .username(username)
                .build();

        // 操作是否正确，不正确就进行回滚
        int i = 0;

        // 获取内容的点赞数
        try {
            // 插入点赞记录
            i = flag ? replyStarMapper.insert(replayStar) : replyStarMapper.deleteById(replayStar);
        } catch (DuplicateKeyException e){
        }

        if (i == 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 处理主键冲突
            return new AtomicBoolean(false);
        }

        Integer stars = replyStarMapper.getStarsByContentId(contentId);

        // 构建更新后的回复实体对象
        Reply replay = Reply.builder()
                .contentId(contentId)
                .stars(stars)
                .build();

        // 更新点赞记录
        i = replyMapper.updateById(replay);

        if (i == 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            // 处理该点赞内容不存在
            return new AtomicBoolean(false);
        }

        return new AtomicBoolean(true);
    }

    /**
     * 根据内容ID和用户名判断是否已点赞
     * @param contentId 内容ID
     * @param username 用户名
     * @return 如果已点赞，则返回true；否则返回false
     */
    public boolean getStarStatus(Long contentId, Long username) {
        QueryWrapper<ReplyStar> wrapper = new QueryWrapper<ReplyStar>().eq("content_id", contentId).eq("username", username);
        return replyStarMapper.selectCount(wrapper) > 0;
    }


    /**
     * 删除回复
     * @param reply 回复对象
     */
    public void deleteReply(Reply reply) {
        // 创建QueryWrapper对象
        QueryWrapper<Reply> wrapper = new QueryWrapper<>();
        // 设置条件：content_id等于reply的内容ID
        wrapper.eq("content_id", reply.getContentId());
        // 设置条件：reply_id等于reply的回复ID
        wrapper.eq("discuss_id", reply.getDiscussId());
        // 设置条件：username等于reply的用户名
        wrapper.eq("username", reply.getUsername());

        // 设置reply的deleted字段为1，标记为已删除
        reply.setDeleted(1);
        // 使用replyMapper的update方法更新reply对象
        replyMapper.update(reply, wrapper);
    }


    /**
     * 根据内容ID获取评分
     *
     * @param contentId 内容ID
     * @return 评分值
     */
    public int getScore(Long contentId) {
        return replyMapper.getScore(contentId);
    }


    /**
     * 判断是否得分
     *
     * @param reply 回复对象
     * @return 是否得分
     */
    public boolean judgeScore(Reply reply) {

        // 创建查询条件
        QueryWrapper<Reply> wrapper = new QueryWrapper<>();
        // 设置条件：content_id 相等
        wrapper.eq("content_id", reply.getContentId());
        // 设置条件：discuss_id 相等
        wrapper.eq("discuss_id", reply.getDiscussId());
        // 设置条件：reply_id 等于0
        wrapper.eq("reply_id", 0);

        // 更新回复信息，并返回更新结果是否成功
        return replyMapper.update(reply, wrapper) == 1;
    }


}
