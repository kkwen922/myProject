package my.project.modules.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import my.project.modules.ums.mapper.UmsRoleMenuRelationMapper;
import my.project.modules.ums.model.UmsRoleMenuRelation;
import my.project.modules.ums.service.UmsRoleMenuRelationService;
import org.springframework.stereotype.Service;

/**
 * 角色菜單關聯管理Service實現類
 *
 * @author : kevin Chang
 */
@Service
public class UmsRoleMenuRelationServiceImpl extends ServiceImpl<UmsRoleMenuRelationMapper, UmsRoleMenuRelation> implements UmsRoleMenuRelationService {
}
