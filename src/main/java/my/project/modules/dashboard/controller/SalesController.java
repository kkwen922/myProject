package my.project.modules.dashboard.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import my.project.common.api.CommonResult;
import my.project.modules.dashboard.dto.SalesCount;
import my.project.modules.dashboard.dto.TimeAmount;
import my.project.modules.dashboard.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;


/**
 * @author kevinchang
 */
@Controller
@RequestMapping("/sales")
@Slf4j
@Tag(name = "Sales Controller")
public class SalesController {

    @Autowired
    private SalesService salesService;

    @RequestMapping(value = "/getSaleProdCount", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SalesCount>> getSaleProdCount(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {


        List<SalesCount> prodCountPage = salesService.getSalesProdCount();
        return CommonResult.success(prodCountPage);

    }

    @RequestMapping(value = "/getHotMachineCount", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<SalesCount>> getHotMachineCount(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {


        List<SalesCount> hotMachineList = salesService.getHotMachineCount();
        return CommonResult.success(hotMachineList);

    }

    @RequestMapping(value = "/getSalesAmount", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Map<String, Object>> getSalesAmount(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {


        List<TimeAmount> weekList = salesService.getSalesAmountLastWeek();
        List<TimeAmount> monthList = salesService.getSalesAmountLastMonth();

        //Last Week
        List<String> sumList_week = new ArrayList<>();
        List<String> timeList_week = new ArrayList<>();
        for (TimeAmount weekData : weekList) {
            sumList_week.add(weekData.getAmount().toString());
            timeList_week.add(weekData.getTime());
        }

        //Last Month
        List<String> sumList_month = new ArrayList<>();
        List<String> timeList_month= new  ArrayList<>();
        for (TimeAmount monthData : monthList) {
            sumList_month.add(monthData.getAmount().toString());
            timeList_month.add(monthData.getTime());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sumList_week", sumList_week);
        map.put("timeList_week", timeList_week);

        map.put("sumList_month",sumList_month);
        map.put("timeList_month", timeList_month);

        return CommonResult.success(map);

    }

    @RequestMapping(value = "/getSalesCount", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Map<String, Object>> a(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {


        List<TimeAmount> countList = salesService.getSalesCount();
        //Last Week
        List<String> sumList = new ArrayList<>();
        List<String> timeList = new ArrayList<>();
        for (TimeAmount data : countList) {
            sumList.add(data.getAmount().toString());
            timeList.add(data.getTime());
        }

        Map<String, Object> map = new HashMap<>();
        map.put("sumList", sumList);
        map.put("timeList", timeList);

        return CommonResult.success(map);

    }
}