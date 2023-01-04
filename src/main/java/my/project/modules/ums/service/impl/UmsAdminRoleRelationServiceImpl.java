package my.project.modules.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import my.project.modules.ums.mapper.UmsAdminRoleRelationMapper;
import my.project.modules.ums.model.UmsAdminRoleRelation;
import my.project.modules.ums.service.UmsAdminRoleRelationService;
import org.springframework.stereotype.Service;

/**
 * 管理員角色管理Service實現類
 *
 * @author : kevin Chang
 */
@Service
public class UmsAdminRoleRelationServiceImpl extends ServiceImpl<UmsAdminRoleRelationMapper, UmsAdminRoleRelation> implements UmsAdminRoleRelationService {
}
