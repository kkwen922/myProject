package my.project.modules.ums.service;


import com.baomidou.mybatisplus.extension.service.IService;
import my.project.modules.ums.model.UmsResourceCategory;

import java.util.List;

/**
 * 後台資源分類管理Service
 */
public interface UmsResourceCategoryService extends IService<UmsResourceCategory> {

    /**
     * 獲得所有資源分類
     */
    List<UmsResourceCategory> listAll();

    /**
     * 創建資源分類
     */
    boolean create(UmsResourceCategory umsResourceCategory);
}
