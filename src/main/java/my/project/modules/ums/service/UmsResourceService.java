package my.project.modules.ums.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import my.project.modules.ums.model.UmsResource;

/**
 * 後台資源管理Service
 */
public interface UmsResourceService extends IService<UmsResource> {
    /**
     * 添加資源
     */
    boolean create(UmsResource umsResource);

    /**
     * 修改資源
     */
    boolean update(Long id, UmsResource umsResource);

    /**
     * 删除資源
     */
    boolean delete(Long id);

    /**
     * 分頁查詢資源
     */
    Page<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum);
}
