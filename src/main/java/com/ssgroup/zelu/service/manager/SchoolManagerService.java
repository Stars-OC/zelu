package com.ssgroup.zelu.service.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ssgroup.zelu.mapper.SchoolMapper;
import com.ssgroup.zelu.pojo.PageList;
import com.ssgroup.zelu.pojo.department.School;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class SchoolManagerService{

    @Autowired
    private SchoolMapper schoolMapper;

    /** School 相关的CRUD **/

    public void addByAdmin(School school) {
        schoolMapper.insert(school);
    }

    public void updateByAdmin(School schoolData) {
        schoolMapper.updateById(schoolData);
    }

    public int deleteByAdmin(Long[] schools) {
        return schoolMapper.deleteBatchIds(Arrays.stream(schools).toList());
    }

    public PageList<School> getSchools(int page, int limit) {
        Page<School> userPage = new Page<>(page,limit);

        Page<School> selectPage = schoolMapper.selectPage(userPage, null);
        return new PageList<>(selectPage);
    }

}
