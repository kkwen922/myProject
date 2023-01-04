package my.project.modules.ums.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import lombok.extern.slf4j.Slf4j;
import my.project.modules.ums.mapper.UmsMenuMapper;
import my.project.modules.ums.mapper.UmsResourceMapper;
import my.project.modules.ums.mapper.UmsRoleMapper;
import my.project.modules.ums.model.*;
import my.project.modules.ums.service.UmsRoleMenuRelationService;
import my.project.modules.ums.service.UmsRoleResourceRelationService;
import my.project.modules.ums.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 後台角色管理Service實現類
 *
 * @author : kevin Chang
 */
@Service
@Slf4j
public class UmsRoleServiceImpl extends ServiceImpl<UmsRoleMapper, UmsRole> implements UmsRoleService {

    @Autowired
    private UmsRoleMenuRelationService roleMenuRelationService;

    @Autowired
    private UmsRoleResourceRelationService roleResourceRelationService;

    @Autowired
    private UmsMenuMapper menuMapper;

    @Autowired
    private UmsResourceMapper resourceMapper;

    @Autowired
    private UmsRoleMapper umsRoleMapper;

    @Override
    public boolean create(UmsRole role) {
        role.setCreateTime(new Date());
        role.setAdminCount(0);
        role.setSort(0);
        return save(role);
    }

    @Override
    public boolean delete(List<Long> ids) {
        boolean success = removeByIds(ids);
        return success;
    }

    @Override
    public Page<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        Page<UmsRole> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsRole> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UmsRole> lambda = wrapper.lambda();
        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(UmsRole::getName, keyword);
        }
        return page(page, wrapper);
    }


    @Override
    public Page<UmsRole> listByCompanyId(Long companyId, String keyword, Integer pageSize, Integer pageNum) {

        log.info("Keyword:" + keyword);
        Page<UmsRole> page = new Page<>(pageNum, pageSize);
        if (StrUtil.isNotEmpty(keyword)) {
            Page<UmsRole> umsRolePage = umsRoleMapper.getRoleListByRoleNameAndCompanyId(page, keyword, companyId);
        } else {
            Page<UmsRole> umsRolePage = umsRoleMapper.getRoleListByCompanyId(page, companyId);
        }
        return page;
    }


    @Override
    public List<UmsRole> listRoleByCompanyId(Long companyId) {

        return umsRoleMapper.getRoleListByCompanyId(companyId);
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        return menuMapper.getMenuList(adminId);
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return menuMapper.getMenuListByRoleId(roleId);
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        return resourceMapper.getResourceListByRoleId(roleId);
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有關聯
        QueryWrapper<UmsRoleMenuRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleMenuRelation::getRoleId, roleId);
        roleMenuRelationService.remove(wrapper);
        //批量插入新關聯
        List<UmsRoleMenuRelation> relationList = new ArrayList<>();
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            relationList.add(relation);
        }
        roleMenuRelationService.saveBatch(relationList);
        return menuIds.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有關聯
        QueryWrapper<UmsRoleResourceRelation> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UmsRoleResourceRelation::getRoleId, roleId);
        roleResourceRelationService.remove(wrapper);
        //批量插入新關聯
        List<UmsRoleResourceRelation> relationList = new ArrayList<>();
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            relationList.add(relation);
        }
        roleResourceRelationService.saveBatch(relationList);
        return resourceIds.size();
    }
}
