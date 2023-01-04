package my.project.modules.oms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import my.project.common.api.CommonPage;
import my.project.common.api.CommonResult;

import my.project.modules.oms.model.OmsOrganization;
import my.project.modules.oms.service.OmsOrganizationService;

import my.project.modules.ums.mapper.UmsEventLogMapper;
import my.project.modules.ums.model.UmsAdmin;
import my.project.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;


/**
 * 單位組織架構管理
 *
 * @author kevinchang
 */
@Controller
@RequestMapping("/org")
@Slf4j
@Tag(name = "OmsOrganization Controller")
public class OmsOrganizationController {

    @Autowired
    private OmsOrganizationService organizationService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsEventLogMapper umsEventLogMapper;

    /**
     * 根據ID取得單筆組織資料
     *
     * @param bearer
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<OmsOrganization> getItem(
            @RequestHeader(value = "Authorization") String bearer,
            @PathVariable Long id) {

        log.info("organizationService.getById: {}", id);
        OmsOrganization organization = organizationService.getById(id);
        return CommonResult.success(organization);
    }

    /**
     * 列出該層級ID之下的所有組織
     *
     * @param parentId
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<OmsOrganization>> listOrganizationPage(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long parentId,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        Long companyId = caller.getCompanyId();

        Page<OmsOrganization> orgList = organizationService.list(companyId, parentId, pageSize, pageNum);

        return CommonResult.success(CommonPage.restPage(orgList));
    }

    @RequestMapping(value = "/listLevel", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<OmsOrganization>> listLevel(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        Long companyId = caller.getCompanyId();

        Page<OmsOrganization> orgList = organizationService.listLevel(companyId, pageSize, pageNum);

        return CommonResult.success(CommonPage.restPage(orgList));
    }

    /**
     * 建立組織
     *
     * @param organization
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createOrganization(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestBody OmsOrganization organization) {

        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        String callerName = caller.getUsername();
        Long companyId = caller.getCompanyId();

        //準備name_sn

        String orgName = organization.getName();
        Integer orgLevel = organization.getLevel();
        Long orgParnet = organization.getParentId();

        log.debug("orgName---->" + orgName + "," + orgLevel + "," + orgParnet);

        //用上層單位，計算出name_sn
        OmsOrganization upperOrg = organizationService.getById(orgParnet);
        String upperSn = upperOrg.getNameSn();
        //找出最大id
        Long currId = organizationService.getMaxId();
        String nextId = String.format("%03d", currId + 1);
        String orgSn = upperSn + nextId;

        //org_sn 已經準備好了，可以新增
        organization.setCompanyId(companyId);
        organization.setNameSn(orgSn);
        organization.setCreateTime(new Date());
        boolean success = organizationService.create(organization);
        if (success) {
            return CommonResult.success(null);
        }
        log.info("createOrganization failed: {}", organization);
        return CommonResult.failed();
    }


    /**
     * 更新組織
     *
     * @param id
     * @param organization
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateOrganization(
            @PathVariable Long id,
            @RequestBody OmsOrganization organization) {

        organization.setId(id);
        boolean success = organizationService.updateById(organization);
        if (success) {
            log.info("updateOrganization success:{}", organization);
            return CommonResult.success(null);
        }
        log.info("updateOrganization failed:{}", organization);
        return CommonResult.failed();
    }

    /**
     * 刪除組織
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteOrganization(@PathVariable Long id) {

        log.info("deleteOrganization: {} ", id);

        List<OmsOrganization> lowList = organizationService.organizationLowList(id);
        log.info("lowList.size(): {} ", lowList.size());

        //無下層組織，可刪除
        if (lowList.size() == 0) {
            boolean success = organizationService.delete(id);
            if (success) {
                log.info("deleteOrganization failed:{}", id);
                return CommonResult.success(null);
            }
        } else {
            return CommonResult.failed("存在下級單位，無法刪除！");
        }
        log.info("deleteOrganization failed:{}", id);
        return CommonResult.failed();
    }

    /**
     * 列舉該公司下的所有組織
     *
     * @return
     */
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<OmsOrganization>> listAllOrganization(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent) {

        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        String callerName = caller.getUsername();
        Long companyId = caller.getCompanyId();

        List<OmsOrganization> organizationList = organizationService.listOrgByComapnyId(companyId);
        log.debug("listAllOrganization count: {}", organizationList.size());
        return CommonResult.success(organizationList);
    }

    /**
     * 更新組織狀態
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateOrganizationStatus(
            @PathVariable Long id,
            @RequestParam(value = "status") Integer status) {

        OmsOrganization organization = new OmsOrganization();
        organization.setStatus(status);
        boolean success = organizationService.update(id, organization);
        if (success) {
            log.info("updateOrganizationStatus success: {},{}", id, organization);
            return CommonResult.success(null);
        }
        log.info("updateOrganizationStatus failed: {},{}", id, organization);
        return CommonResult.failed();
    }

    /**
     * 帳號選擇組織的下拉清單listCombox
     *
     * @param bearer
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/listD", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<OmsOrganization>> listOrganizationPage(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        //取得呼叫者的資訊，由組織iD取得企業iD
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);

        Page<OmsOrganization> orgList = organizationService.listCombox(caller.getCompanyId(), pageSize, pageNum);
        log.info("listOrganizationPage count: {}", CommonPage.restPage(orgList).getTotal());
        return CommonResult.success(CommonPage.restPage(orgList));
    }

//    private int insertEventLog(Long userId, String username, String userAgent, String event, Integer result,String memo) {
//        //EventLog的初始化
//        try {
//            UmsEventLog eventLog = new UmsEventLog();
//            eventLog.setUserId(userId);
//            eventLog.setUsername(username);
//            eventLog.setCreateTime(new Date());
//            eventLog.setMemo(userAgent);
//            eventLog.setEvent(event);
//            eventLog.setResult(result);
//            eventLog.setMemo(memo);
//            return umsEventLogMapper.insert(eventLog);
//        } catch (Exception ex) {
//            log.info(String.valueOf(ex));
//            return -1;
//        }
//    }
}
