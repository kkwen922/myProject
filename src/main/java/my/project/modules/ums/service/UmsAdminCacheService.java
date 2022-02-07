package my.project.modules.ums.service;

import my.project.modules.ums.model.UmsAdmin;
import my.project.modules.ums.model.UmsResource;

import java.util.List;

/**
 * 後台用户暫存管理Service(Redis)
 */
public interface UmsAdminCacheService {
    /**
     * 删除後台用户暫存
     */
    void delAdmin(Long adminId);

    /**
     * 删除後台用户資源列表暫存
     */
    void delResourceList(Long adminId);

    /**
     * 當角色相關資源資料改变時，删除相關後台用户暫存
     */
    void delResourceListByRole(Long roleId);

    /**
     * 當角色相關資源資料改变時，删除相關後台用户暫存
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 當資源信息改變時，删除資源項目後台用户暫存
     */
    void delResourceListByResource(Long resourceId);

    /**
     * 獲得暫存後台用户信息
     */
    UmsAdmin getAdmin(String username);

    /**
     * 設置暫存後台用户信息
     */
    void setAdmin(UmsAdmin admin);

    /**
     * 獲得暫存後台用户資源列表
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 設置後台用户資源列表
     */
    void setResourceList(Long adminId, List<UmsResource> resourceList);
}
