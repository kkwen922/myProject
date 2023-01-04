package my.project.modules.ums.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import my.project.common.api.CommonPage;
import my.project.common.api.CommonResult;
import my.project.modules.ums.model.UmsAdmin;
import my.project.modules.ums.model.UmsEventLog;
import my.project.modules.ums.service.UmsEventLogService;
import my.project.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author kevinchang
 */
@Controller
@RequestMapping("/operate")
@Slf4j
@Tag(name = "UmsEventLog Controller")
public class UmsEventLogController {

    @Autowired
    UmsEventLogService umsEventLogService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<UmsEventLog>> list(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        //取得呼叫者的資訊
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        log.info("userId:" + caller.getId());


        Page<UmsEventLog> eventList = umsEventLogService.list(caller.getId(), keyword, pageSize, pageNum);

        return CommonResult.success(CommonPage.restPage(eventList));
    }
}
