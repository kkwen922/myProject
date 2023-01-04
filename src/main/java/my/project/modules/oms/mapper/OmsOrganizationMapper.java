package my.project.modules.oms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.project.modules.oms.model.OmsOrganization;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author kevinchang
 */
public interface OmsOrganizationMapper extends BaseMapper<OmsOrganization> {

    /**
     * 獲取對應的公司下層組織
     *
     * @param parentId
     * @return
     */
    List<OmsOrganization> getOrgListByParentId(@Param("parentId") Long parentId);

    /**
     * Get Org Data By orgName
     * @param name
     * @return
     */
    OmsOrganization getOrgByName(@Param("name") String name);

    /**
     * Get Max Org Id Number
     * @return
     */
    Long getOrgMax();

    /**
     * Fetch Data By CompanyId
     * @param companyId
     * @return
     */
    List<OmsOrganization> getOrgListByComapnyId(@Param("companyId") Long companyId);
}
