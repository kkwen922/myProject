package my.project.modules.dashboard.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import my.project.modules.dashboard.dto.SalesCount;
import my.project.modules.dashboard.dto.TimeAmount;
import my.project.modules.dashboard.mapper.SalesMapper;
import my.project.modules.dashboard.model.Sales;
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
public class SalesServiceImpl extends ServiceImpl<SalesMapper, Sales> implements SalesService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SalesServiceImpl.class);

    @Autowired
    private SalesMapper salesMapper;


    @Override
    public Long getTodayAmount(String fromDate, String toDate) {
        return salesMapper.getTodayAmount(fromDate, toDate);
    }

    @Override
    public List<SalesCount> getSalesProdCount() {
        return salesMapper.getSalesProdCount();
    }

    @Override
    public List<SalesCount> getHotMachineCount() {
        return salesMapper.getHotMachine();
    }

    @Override
    public List<TimeAmount> getSalesAmount() {
        return salesMapper.getSalesAmount();
    }
    public List<TimeAmount> getSalesAmountLastWeek() {
        return salesMapper.getSalesAmountLastWeek();
    }
    public List<TimeAmount> getSalesAmountLastMonth() {
        return salesMapper.getSalesAmountLastMonth();
    }

    public List<TimeAmount> getSalesCount() {
        return salesMapper.getSalesCount();
    }

    public Integer getTodaySalesCount() {
        return salesMapper.getTodaySalesCount();
    }


}
