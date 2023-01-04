package my.project.modules.oms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import my.project.modules.oms.mapper.OmsOrganizationMapper;
import my.project.modules.oms.model.OmsOrganization;
import my.project.modules.oms.service.OmsOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author kevinchang
 */
@Service
@Slf4j
public class OmsOrganizationServiceImpl extends ServiceImpl<OmsOrganizationMapper, OmsOrganization> implements OmsOrganizationService {


    private OmsOrganizationMapper omsOrganizationMapper;

    @Override
    public boolean create(OmsOrganization organization) {
        organization.setCreateTime(new Date());
        updateLevel(organization);
        return save(organization);
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
    public Page<OmsOrganization> list(Long companyId, Long parentId, Integer pageSize, Integer pageNum) {
        Page<OmsOrganization> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OmsOrganization> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OmsOrganization> lambda = wrapper.lambda();

        lambda.eq(OmsOrganization::getParentId, parentId)
                .eq(OmsOrganization::getCompanyId, companyId)
                .orderByDesc(OmsOrganization::getSort);

        return page(page, wrapper);


    }

    @Override
    public Page<OmsOrganization> listLevel(Long companyId, Integer pageSize, Integer pageNum) {
        Page<OmsOrganization> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OmsOrganization> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OmsOrganization> lambda = wrapper.lambda();

        lambda.eq(OmsOrganization::getCompanyId, companyId)
                .in(OmsOrganization::getLevel, 0, 1)
                .orderByDesc(OmsOrganization::getSort);

        return page(page, wrapper);
    }

    @Override
    public boolean updateStatus(Long id, Integer status) {
        OmsOrganization organization = new OmsOrganization();
        organization.setId(id);
        organization.setStatus(status);
        return updateById(organization);
    }

    @Override
    public boolean update(Long id, OmsOrganization organization) {
        organization.setId(id);
        boolean success = updateById(organization);
        return success;
    }

    @Override
    public OmsOrganization getOrganizationByName(String name) {
        return omsOrganizationMapper.getOrgByName(name);
    }


    /**
     * 修改單位層級
     */
    private void updateLevel(OmsOrganization omsOrganization) {
        if (omsOrganization.getParentId() == 0) {
            //没有父菜單時為一級菜單
            omsOrganization.setLevel(0);
        } else {
            //有父菜單時選擇根據父菜單level設置
            OmsOrganization parentOrganization = getById(omsOrganization.getParentId());
            if (parentOrganization != null) {
                omsOrganization.setLevel(parentOrganization.getLevel() + 1);
            } else {
                parentOrganization.setLevel(0);
            }
        }
    }

    @Override
    public List<OmsOrganization> organizationLowList(Long parentId) {
        log.info("organizationLowList.......{}", parentId);
        return omsOrganizationMapper.getOrgListByParentId(parentId);
    }

    @Override
    public Page<OmsOrganization> list(Integer pageSize, Integer pageNum) {

        Page<OmsOrganization> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OmsOrganization> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<OmsOrganization> lambda = wrapper.lambda();

        return page(page, wrapper);
    }

    @Override
    public Page<OmsOrganization> listCombox(Long companyId, Integer pageSize, Integer pageNum) {

        Page<OmsOrganization> page = new Page<>(pageNum, pageSize);
        QueryWrapper<OmsOrganization> wrapper = new QueryWrapper<>();

        LambdaQueryWrapper<OmsOrganization> lambda = wrapper.lambda();
        lambda.eq(OmsOrganization::getCompanyId, companyId)
                .orderByDesc(OmsOrganization::getSort);

        return page(page, wrapper);
    }

    @Override
    public List<OmsOrganization> listOrgByComapnyId(Long companyId) {
        return omsOrganizationMapper.getOrgListByComapnyId(companyId);
    }

    @Override
    public Long getMaxId() {
        return omsOrganizationMapper.getOrgMax();
    }
}
