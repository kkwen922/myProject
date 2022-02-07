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
 */
public interface UmsRoleService extends IService<UmsRole> {
    /**
     * 添加角色
     */
    boolean create(UmsRole role);

    /**
     * 批量删除角色
     */
    boolean delete(List<Long> ids);

    /**
     * 分頁獲得角色列表
     */
    Page<UmsRole> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根據管理員ID獲得对應菜單
     */
    List<UmsMenu> getMenuList(Long adminId);

    /**
     * 獲得角色相關菜單
     */
    List<UmsMenu> listMenu(Long roleId);

    /**
     * 獲得角色相關資源
     */
    List<UmsResource> listResource(Long roleId);

    /**
     * 給角色分配菜單
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 給角色分配資源
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);
}
