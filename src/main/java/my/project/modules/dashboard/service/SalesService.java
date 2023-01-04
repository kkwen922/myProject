package my.project.modules.dashboard.service;


import com.baomidou.mybatisplus.extension.service.IService;
import my.project.modules.dashboard.dto.SalesCount;
import my.project.modules.dashboard.dto.TimeAmount;
import my.project.modules.dashboard.model.Sales;


import java.util.List;

/**
 * 銷售管理Service
 *
 * @author Kevin Chang
 */
public interface SalesService extends IService<Sales> {

    /**
     * Get Today Amount
     * @param fromDate
     * @param toDate
     * @return
     */
    Long getTodayAmount(String fromDate, String toDate);

    /**
     * Get Amount By Product
     * @return
     */
    List<SalesCount> getSalesProdCount();

    /**
     * Get Amount By Machine
     * @return
     */
    List<SalesCount> getHotMachineCount();

    /**
     * Get Amount
     * @return
     */
    List<TimeAmount> getSalesAmount();
    List<TimeAmount> getSalesAmountLastWeek();
    List<TimeAmount> getSalesAmountLastMonth();

    /**
     * Get Amount
     * @return
     */
    List<TimeAmount> getSalesCount();
}
