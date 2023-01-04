package my.project.modules.ums.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import my.project.modules.ums.mapper.UmsRoleResourceRelationMapper;
import my.project.modules.ums.model.UmsRoleResourceRelation;
import my.project.modules.ums.service.UmsRoleResourceRelationService;
import org.springframework.stereotype.Service;

/**
 * 角色資源關聯管理Service實現類
 *
 * @author : kevin Chang
 */
@Service
public class UmsRoleResourceRelationServiceImpl extends ServiceImpl<UmsRoleResourceRelationMapper, UmsRoleResourceRelation> implements UmsRoleResourceRelationService {
}
