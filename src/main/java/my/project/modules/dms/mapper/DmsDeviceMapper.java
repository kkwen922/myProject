package my.project.modules.dms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import my.project.modules.dms.model.DmsDevice;
import my.project.modules.oms.model.OmsCompany;
import my.project.modules.ums.model.UmsAdmin;
import org.apache.ibatis.annotations.Param;


/**
 * @author kevinchang
 */
public interface DmsDeviceMapper extends BaseMapper<DmsDevice> {

    /**
     * Fetch Device Info By CompanyID
     * @param page
     * @param companyId
     * @return
     */
    Page<DmsDevice> getDeviceListByCompanyId(Page<DmsDevice> page, @Param("companyId") Long companyId);

}
