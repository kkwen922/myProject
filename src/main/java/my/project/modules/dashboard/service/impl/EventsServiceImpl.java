package my.project.modules.dashboard.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import my.project.modules.dashboard.dto.EventsCount;
import my.project.modules.dashboard.dto.EventsCount3;
import my.project.modules.dashboard.dto.SalesCount;
import my.project.modules.dashboard.dto.TimeAmount;
import my.project.modules.dashboard.mapper.EventsMapper;
import my.project.modules.dashboard.mapper.SalesMapper;
import my.project.modules.dashboard.model.Events;
import my.project.modules.dashboard.model.Sales;
import my.project.modules.dashboard.service.EventsService;
import my.project.modules.dashboard.service.SalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author kevinchang
 */
@Service
@Slf4j
public class EventsServiceImpl extends ServiceImpl<EventsMapper, Events> implements EventsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventsServiceImpl.class);

    @Autowired
    private EventsMapper eventsMapper;

    @Override
    public List<EventsCount> getEventFail(String eventType) {
        return eventsMapper.getEventFail(eventType);
    }

    @Override
    public List<EventsCount> getDeliverEventFail(String eventType) {
        return eventsMapper.getDeliverEventFail(eventType);
    }

    @Override
    public List<EventsCount3> getEventCount() {
        return eventsMapper.getEventCount();
    }
}
