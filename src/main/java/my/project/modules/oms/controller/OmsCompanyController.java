package my.project.modules.oms.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import my.project.common.api.CommonPage;
import my.project.common.api.CommonResult;
import my.project.modules.oms.model.OmsCompany;
import my.project.modules.oms.model.OmsOrganization;
import my.project.modules.oms.service.OmsCompanyService;
import my.project.modules.oms.service.OmsOrganizationService;
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
@RequestMapping("/company")
@Slf4j
@Tag(name = "OmsCompany Controller")
public class OmsCompanyController {

    private static final String SUPER_ADMIN_ACCOUNT = "root";
    @Autowired
    private OmsCompanyService companyService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 依據公司ID取得該筆公司資料
     *
     * @param bearer
     * @param userAgent
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    @Operation(summary = "This method is used to get the company by ID.")
    public CommonResult<OmsCompany> getItem(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id) {

        //只有root以及該公司的admin帳號可以操作

        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        String callerName = caller.getUsername();

        log.info("companyService.getById: {}", id);
        OmsCompany company = companyService.getById(id);
        return CommonResult.success(company);

    }

    /**
     * 列舉公司
     *
     * @param bearer
     * @param userAgent
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<OmsCompany>> listCompanyPage(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        //只有root以及該公司的admin帳號可以操作
        //root 可以顯示所有公司
        //admin 可以只可以顯示自己公司資料
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        String callerName = caller.getUsername();
        Long companyId = caller.getCompanyId();

        log.info("caller name:" + callerName);
        if (SUPER_ADMIN_ACCOUNT.equals(callerName)) {
            Page<OmsCompany> companyList = companyService.list(pageSize, pageNum);
            return CommonResult.success(CommonPage.restPage(companyList));
        } else {
            log.info("callerName:" + callerName);
            Page<OmsCompany> companyList = companyService.listMyCompany(companyId, pageSize, pageNum);
            return CommonResult.success(CommonPage.restPage(companyList));
        }
    }

    /**
     * 新增公司
     *
     * @param bearer
     * @param userAgent
     * @param company
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createCompany(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestBody OmsCompany company) {

        //只有root可以新增公司
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        String callerName = caller.getUsername();
        if (SUPER_ADMIN_ACCOUNT.equals(callerName)) {
            boolean success = companyService.create(company);
            if (success) {
                log.info("create Company success: {}", company);
                return CommonResult.success(null);
            }
        }
        return CommonResult.failed();
    }

    /**
     * 更新公司資料
     *
     * @param id
     * @param company
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateCompany(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id,
            @RequestBody OmsCompany company) {

        //只有root以及該公司的admin帳號可以操作
        //root 可以更改所有公司資料
        //admin 可以只可以更改自己公司資料
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        String callerName = caller.getUsername();
        Long companyId = caller.getCompanyId();
        //if("root".equals(callerName) || (callerName.contains("admin") && id.equals(companyId))) {
        company.setId(id);
        boolean success = companyService.updateById(company);
        return CommonResult.success(companyService.updateById(company));
        //}else {
        //   return CommonResult.failed(callerName+"無此權限");
        //}
    }

    /**
     * 刪除公司資料
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteCompany(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id) {

        //只有root可以刪除公司資料
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        String callerName = caller.getUsername();
        if (SUPER_ADMIN_ACCOUNT.equals(callerName)) {
            boolean success = companyService.delete(id);
            return CommonResult.success(success);
        } else {
            return CommonResult.failed(callerName + "無此權限");
        }
    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<OmsCompany>> listAllCompany(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent) {

        //只有root可以操作列舉所有公司（下拉選單）
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        String callerName = caller.getUsername();

        if (SUPER_ADMIN_ACCOUNT.equals(callerName)) {
            List<OmsCompany> companyList = companyService.list();
            log.info("listAllCompany count: {}", companyList.size());
            return CommonResult.success(companyList);
        } else {
            return CommonResult.failed(callerName + "無此權限");
        }
    }


    @RequestMapping(value = "/listD", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<OmsCompany>> listCompany(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam(value = "pageSize", defaultValue = "100") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        //只有root可以操作
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        String callerName = caller.getUsername();
        if (SUPER_ADMIN_ACCOUNT.equals(callerName)) {
            Page<OmsCompany> orgList = companyService.list(pageSize, pageNum);
            log.info("listOrganizationPage count: {}", CommonPage.restPage(orgList).getTotal());
            return CommonResult.success(CommonPage.restPage(orgList));
        } else {
            return CommonResult.failed(callerName + "無此權限");
        }
    }
}
