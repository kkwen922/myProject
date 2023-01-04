package my.project.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import my.project.modules.ums.model.UmsAdmin;
import my.project.modules.ums.model.UmsRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 後台用户角色 Mapper 接口
 *
 * @author : kevin Chang
 */
public interface UmsRoleMapper extends BaseMapper<UmsRole> {

    /**
     * Fetch Data By userId
     * @param adminId
     * @return
     */
    List<UmsRole> getRoleList(@Param("adminId") Long adminId);

    /**
     * Fetch Data By companyId
     * @param companyId
     * @return
     */
    List<UmsRole> getRoleListByCompanyId(@Param("companyId") Long companyId);

    /**
     * Fetch Data By companyId
     * @param page
     * @param companyId
     * @return
     */
    Page<UmsRole> getRoleListByCompanyId(Page<UmsRole> page, @Param("companyId") Long companyId);

    /**
     * Fetch Data By companyId
     * @param page
     * @param name
     * @param companyId
     * @return
     */
    Page<UmsRole> getRoleListByRoleNameAndCompanyId(Page<UmsRole> page, @Param("name") String name, @Param("companyId") Long companyId);
}
