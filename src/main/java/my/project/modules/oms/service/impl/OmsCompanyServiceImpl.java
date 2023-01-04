package my.project.modules.oms.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import my.project.modules.oms.mapper.OmsCompanyMapper;
import my.project.modules.oms.model.OmsCompany;
import my.project.modules.oms.model.OmsOrganization;
import my.project.modules.oms.service.OmsCompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author kevinchang
 */
@Service
public class OmsCompanyServiceImpl extends ServiceImpl<OmsCompanyMapper, OmsCompany> implements OmsCompanyService {


    @Override
    public boolean create(OmsCompany omsCompany) {
        omsCompany.setCreateTime(new Date());
        return save(omsCompany);
    }

    @Override
    public boolean delete(List<Long> ids) {
        boolean success = removeByIds(ids);
        return success;
    }

    @Override
    public boolean delete(Long id) {
        boolean success = removeById(id);
        return success;
    }

    @Override
    public Page<OmsCompany> list(Integer pageSize, Integer pageNum) {
        Page<OmsCompany> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OmsCompany> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OmsCompany> lambda = wrapper.lambda();
        return page(page, wrapper);
    }

    @Override
    public Page<OmsCompany> listMyCompany(Long companyId, Integer pageSize, Integer pageNum) {
        Page<OmsCompany> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OmsCompany> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OmsCompany> lambda = wrapper.lambda();
        lambda.eq(OmsCompany::getId, companyId);
        return page(page, wrapper);

    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        OmsCompany company = new OmsCompany();
        company.setId(id);
        company.setStatus(status);
        return updateById(company);
    }

    @Override
    public boolean update(Long id, OmsCompany company) {
        company.setId(id);
        boolean success = updateById(company);
        return success;
    }
}
