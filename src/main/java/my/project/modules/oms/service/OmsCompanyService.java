package my.project.modules.oms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import my.project.modules.oms.model.OmsCompany;


import java.util.List;

/**
 * @author kevinchang
 */

public interface OmsCompanyService extends IService<OmsCompany> {

    /**
     * 創建公司資訊
     *
     * @param omsCompany
     * @return
     */
    boolean create(OmsCompany omsCompany);

    /**
     * 批量删除該公司下的組織
     *
     * @param ids
     * @return
     */
    boolean delete(List<Long> ids);

    /**
     * 刪除公司
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 分頁獲得公司列表
     *
     * @param pageSize
     * @param pageNum
     * @return
     */
    Page<OmsCompany> list(Integer pageSize, Integer pageNum);

    /**
     * 列舉公司（單筆）
     *
     * @param companyId
     * @param pageSize
     * @param pageNum
     * @return
     */
    Page<OmsCompany> listMyCompany(Long companyId, Integer pageSize, Integer pageNum);

    /**
     * 修改公司狀態
     *
     * @param id
     * @param status
     * @return
     */
    boolean updateStatus(Long id, Integer status);

    /**
     * 修改指定公司信息
     *
     * @param id
     * @param omsCompany
     * @return
     */
    boolean update(Long id, OmsCompany omsCompany);


}
