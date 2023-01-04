package my.project.modules.dashboard.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import my.project.common.api.CommonResult;
import my.project.modules.dashboard.dto.EventsCount;
import my.project.modules.dashboard.dto.EventsCount3;
import my.project.modules.dashboard.dto.SalesCount;
import my.project.modules.dashboard.dto.TimeAmount;
import my.project.modules.dashboard.service.EventsService;
import my.project.modules.dashboard.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author kevinchang
 */
@Controller
@RequestMapping("/events")
@Slf4j
@Tag(name = "Events Controller")
public class EventsController {

    @Autowired
    private EventsService eventsService;

    @RequestMapping(value = "/getEventFail", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<EventsCount>> getEventFail(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam(value = "eventType") String eventType,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        //圓餅圖專用
        List<EventsCount> eventPage = eventsService.getEventFail(eventType);
        return CommonResult.success(eventPage);

    }

    @RequestMapping(value = "/getDeliverEventFail", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<EventsCount>> getDeliverEventFail(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam(value = "eventType") String eventType) {

        //圓餅圖專用
        List<EventsCount> eventPage = eventsService.getDeliverEventFail(eventType);
        return CommonResult.success(eventPage);
    }

    @RequestMapping(value = "/getEventCount", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Map<String, Object>> getEventCount(
            @RequestHeader(value = "Authorization") String bearer) {

        List<EventsCount3> timeAndSumList = eventsService.getEventCount();

        List<String> deliveryList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        List<String> offlineList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>(timeAndSumList.size());
        for (EventsCount3 data : timeAndSumList) {
            deliveryList.add(data.getDeliverEvent().toString());
            tempList.add(data.getTempEvent().toString());
            offlineList.add(data.getOfflineEvent().toString());
            timeList.add(data.getName());
        }
        map.put("deliveryList", deliveryList);
        map.put("tempList", tempList);
        map.put("offlineList", offlineList);
        map.put("timeList", timeList);

        return CommonResult.success(map);
    }
}