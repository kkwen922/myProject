package my.project.modules.ums.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import my.project.modules.ums.model.UmsEventLog;
import my.project.modules.ums.model.UmsMenu;

/**
 * @author kevinchang
 */
public interface UmsEventLogService extends IService<UmsEventLog> {

    /**
     * Fetch Data By userId or keyword
     * @param userId
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    Page<UmsEventLog> list(Long userId, String keyword, Integer pageSize, Integer pageNum);

}
