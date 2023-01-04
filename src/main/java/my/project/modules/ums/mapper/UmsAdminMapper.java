package my.project.modules.ums.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import my.project.modules.ums.model.UmsAdmin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 後台用户 Mapper 接口
 *
 * @author : kevin Chang
 */
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {

    /**
     * Fetch Account List By resourceId
     * @param resourceId
     * @return
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);

    /**
     * Fetch Accmount Data by CompanyId
     * @param page
     * @param username
     * @param companyId
     * @return
     */
    Page<UmsAdmin> getAdminByUsernameAndCompanyId(Page<UmsAdmin> page, @Param("username") String username, @Param("companyId") Long companyId);

    /**
     * Fetch Data By CompanyID
     * @param page
     * @param companyId
     * @return
     */
    Page<UmsAdmin> getAdminByCompanyId(Page<UmsAdmin> page, @Param("companyId") Long companyId);

    /**
     * Get Account By Username
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(@Param("username") String username);

}
