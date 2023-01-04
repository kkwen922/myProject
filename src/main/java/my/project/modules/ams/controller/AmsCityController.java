package my.project.modules.ams.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import my.project.common.api.CommonPage;
import my.project.common.api.CommonResult;
import my.project.modules.ams.model.AmsCity;
import my.project.modules.ams.service.AmsCityService;
import my.project.modules.dms.model.DmsDevice;
import my.project.modules.dms.model.DmsDeviceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author kevinchang
 */
@Controller
@RequestMapping("/city")
public class AmsCityController {

    @Autowired
    private AmsCityService cityService;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<AmsCity>> listAll() {
        List<AmsCity> cityList = cityService.listAll();
        return CommonResult.success(cityList);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<AmsCity>> list(@RequestParam(value = "keyword", required = false) String keyword,
                                                  @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
                                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        Page<AmsCity> cityList = cityService.list(keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(cityList));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<AmsCity> getItem(@PathVariable Long id) {

        AmsCity city = cityService.getById(id);
        return CommonResult.success(city);
    }

}
