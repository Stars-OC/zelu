package com.ssgroup.zelu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ssgroup.zelu.pojo.department.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CompanyMapper extends BaseMapper<Company> {

    /**
     * 更新公司是否删除
     *
     * @param id 公司id
     * @param deleted 是否删除(0,1)
     */
    @Update("update company set deleted = #{deleted} where company_id = #{id}")
    void updateDeleted(long id,int deleted);
}
