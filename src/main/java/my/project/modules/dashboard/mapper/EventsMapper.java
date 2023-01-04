package my.project.modules.dashboard.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.project.modules.dashboard.dto.EventsCount;
import my.project.modules.dashboard.dto.EventsCount3;
import my.project.modules.dashboard.dto.SalesCount;
import my.project.modules.dashboard.model.Events;
import my.project.modules.dashboard.model.Sales;

import java.util.List;

/**
 * @author kevinchang
 */
public interface EventsMapper extends BaseMapper<Events> {

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
