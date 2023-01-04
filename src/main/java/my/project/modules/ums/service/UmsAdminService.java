package my.project.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import my.project.domain.AdminUserDetails;
import my.project.modules.ums.dto.UmsAdminParam;
import my.project.modules.ums.dto.UpdateAdminPasswordParam;
import my.project.modules.ums.model.UmsAdmin;
import my.project.modules.ums.model.UmsResource;
import my.project.modules.ums.model.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * @author kevinchang
 */
public interface UmsAdminService extends IService<UmsAdmin> {

//    UmsAdmin getAdminByUsernameAndInvoiceNumber(String username,Long invoiceNumber);

    /**
     * 根據用戶名稱獲得後台管理員
     *
     * @param username
     * @return
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     * 新增帳號
     *
     * @param umsAdminParam
     * @return
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * 登入功能
     *
     * @param username 用戶名稱
     * @param password 密碼
     * @return 生成的JWT的token
     */
    String login(String username, String password);


    /**
     * 刷新token的功能
     *
     * @param oldToken
     * @return
     */
    String refreshToken(String oldToken);


    /**
     * 根據用戶名稱或暱稱分頁查詢用户
     *
     * @param companyId
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    Page<UmsAdmin> list(Long companyId, String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改指定用户信息
     *
     * @param id
     * @param admin
     * @return
     */
    boolean update(Long id, UmsAdmin admin);

    /**
     * 删除指定用户
     *
     * @param id
     * @return
     */
    boolean delete(Long id);

    /**
     * 修改用户角色關聯
     *
     * @param adminId
     * @param roleIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    int updateRole(Long adminId, List<Long> roleIds);


    /**
     * 獲得用户對應角色
     *
     * @param adminId
     * @return
     */
    List<UmsRole> getRoleList(Long adminId);


    /**
     * 獲得指定用户的可訪問資源
     *
     * @param adminId
     * @return
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 修改密碼
     *
     * @param updatePasswordParam
     * @return
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 獲得用户信息
     *
     * @param username
     * @return
     */
    UserDetails loadUserByUsername(String username);


}
