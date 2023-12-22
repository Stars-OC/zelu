package com.ssgroup.zelu.service.discuss;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssgroup.zelu.mapper.DiscussMapper;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.discuss.Discuss;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DiscussService {

    @Autowired
    private DiscussMapper discussMapper;

    /**
     * 根据讨论ID获取讨论信息
     *
     * @param discussId 讨论ID
     * @return 讨论信息
     */
    public Discuss getDiscuss(String discussId) {
        return discussMapper.selectById(discussId);
    }

    /**
     * 根据课程ID获取讨论列表
     *
     * @param courseId 课程ID
     * @param page 当前页码
     * @param size 每页显示条数
     * @return 包含讨论列表的分页对象
     */
    public PageList<Discuss> getDiscussByCourseId(Long courseId, int page, int size) {
        // 创建查询条件封装对象
        QueryWrapper<Discuss> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        // 创建分页对象
        Page<Discuss> pageList = new Page<>(page, size);
        // 查询并获取讨论列表的分页结果
        Page<Discuss> selectPage = discussMapper.selectPage(pageList, wrapper);
        // 将分页结果转换为分页列表对象并返回
        return new PageList<>(selectPage);
    }

    /**
     * 添加讨论
     *
     * @param discuss 需要添加的讨论对象
     */
    public void addDiscuss(Discuss discuss) {
        discussMapper.insert(discuss);
    }

    /**
     * 更新讨论信息
     * @param discuss 需要更新的讨论对象
     */
    public void updateDiscuss(Discuss discuss) {
        discussMapper.updateById(discuss);
    }

    /**
     * 删除讨论
     * @param discuss 需要删除的讨论对象
     */
    public void deleteDiscuss(Discuss discuss) {
        // 创建查询条件
        QueryWrapper<Discuss> wrapper = new QueryWrapper<>();
        wrapper.eq("discuss_id", discuss.getDiscussId()); // 根据讨论ID进行等值查询
        wrapper.eq("course_id", discuss.getCourseId()); // 根据课程ID进行等值查询
        wrapper.eq("username", discuss.getUsername()); // 根据用户名进行等值查询

        // 将讨论的删除标记置为1
        discuss.setDeleted(1);

        // 更新数据库中符合条件的记录
        discussMapper.update(discuss, wrapper);
    }
}
