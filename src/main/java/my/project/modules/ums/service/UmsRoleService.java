package my.project.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import my.project.modules.ums.model.UmsMenu;
import my.project.modules.ums.model.UmsResource;
import my.project.modules.ums.model.UmsRole;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 後台角色管理Service
 *
 * @author : kevin Chang
 */
public interface UmsRoleService extends IService<UmsRole> {

    /**
     * 添加角色
     *
     * @param role
     * @return
     */
    boolean create(UmsRole role);

    /**
     * 批量删除角色
     *
     * @param ids
     * @return
     */
    boolean delete(List<Long> ids);

    /**
     * 分頁獲得角色列表
     *
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    Page<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * Fetch data By companyId
     * @param companyId
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    Page<UmsRole> listByCompanyId(Long companyId, String keyword, Integer pageSize, Integer pageNum);

    /**
     * Fetch Data By companyId
     * @param companyId
     * @return
     */
    List<UmsRole> listRoleByCompanyId(Long companyId);

    /**
     * 根據管理員ID獲得对應菜單
     *
     * @param adminId
     * @return
     */
    List<UmsMenu> getMenuList(Long adminId);

    /**
     * 獲得角色相關菜單
     *
     * @param roleId
     * @return
     */
    List<UmsMenu> listMenu(Long roleId);

    /**
     * 獲得角色相關資源
     *
     * @param roleId
     * @return
     */
    List<UmsResource> listResource(Long roleId);


    /**
     * 給角色分配菜單
     *
     * @param roleId
     * @param menuIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    int allocMenu(Long roleId, List<Long> menuIds);


    /**
     * 給角色分配資源
     *
     * @param roleId
     * @param resourceIds
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    int allocResource(Long roleId, List<Long> resourceIds);
}
