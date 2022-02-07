package my.project.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.project.modules.ums.model.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 後台用户角色 Mapper 接口
 * </p
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
     * 獲取用户所有角色
     */
    List<UmsRole> getRoleList(@Param("adminId") Long adminId);

}
