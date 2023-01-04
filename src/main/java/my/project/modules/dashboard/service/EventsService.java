package my.project.modules.dashboard.service;

import com.baomidou.mybatisplus.extension.service.IService;
import my.project.modules.dashboard.dto.EventsCount;
import my.project.modules.dashboard.dto.EventsCount3;
import my.project.modules.dashboard.dto.SalesCount;
import my.project.modules.dashboard.model.Events;
import my.project.modules.dashboard.model.Sales;

import java.util.List;

/**
 * @author kevinchang
 */
public interface EventsService extends IService<Events> {

    /**
     * Fetch Data By eventType
     * @param eventType
     * @return
     */
    List<EventsCount> getEventFail(String eventType);

    /**
     * Fetch Data By eventType
     * @param eventType
     * @return
     */
    List<EventsCount> getDeliverEventFail(String eventType);

    /**
     * Fetch All Data
     * @return
     */
    List<EventsCount3> getEventCount();
}
