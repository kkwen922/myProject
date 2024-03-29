package my.project.modules.ams.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import my.project.modules.ams.model.AmsCity;
import my.project.modules.dms.model.DmsDeviceCategory;


import java.util.List;

/**
 * @author kevinchang
 */
@SuppressWarnings("AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc")
public interface AmsCityService extends IService<AmsCity> {

    /**
     * Fetch City Data By CityId
     * @param id
     * @return
     */
    AmsCity getCityById(Long id);

    /**
     * Fetch All Citty Data
     * @return
     */
    List<AmsCity> listAll();

    /**
     * Fetch Area Data By Keyword
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    Page<AmsCity> list(String keyword, Integer pageSize, Integer pageNum);


}
