package com.ssgroup.zelu.service;

import com.ssgroup.zelu.mapper.CompanyMapper;
import com.ssgroup.zelu.mapper.SchoolMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeptService {

    @Autowired
    private SchoolMapper schoolMapper;

    @Autowired
    private CompanyMapper companyMapper;

    public Object checkDept(long deptId){
        if (deptId == 0) return null;
        if (deptId < 500000) {
            return schoolMapper.selectById(deptId);
        } else {
            return companyMapper.selectById(deptId);
        }
    }
}
