package my.project.modules.ums.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import my.project.common.api.CommonPage;
import my.project.common.api.CommonResult;
import my.project.modules.oms.model.OmsCompany;
import my.project.modules.oms.model.OmsOrganization;
import my.project.modules.oms.service.OmsOrganizationService;
import my.project.modules.ums.dto.UmsAdminLoginParam;
import my.project.modules.ums.dto.UmsAdminParam;
import my.project.modules.ums.dto.UpdateAdminPasswordParam;
import my.project.modules.ums.mapper.UmsEventLogMapper;
import my.project.modules.ums.model.UmsAdmin;
import my.project.modules.ums.model.UmsEventLog;
import my.project.modules.ums.model.UmsRole;
import my.project.modules.ums.service.UmsAdminService;
import my.project.modules.ums.service.UmsRoleService;


import my.project.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.executable.ValidateOnExecution;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 後台帳號管理
 *
 * @author : kevin Chang
 */
@Controller
@RequestMapping("/admin")
@Slf4j
@Tag(name = "UmsAdmin Controllerr")
public class UmsAdminController {

    private static String COMPANY_ADMIN_ACCOUNT = "admin";
    private static int FAILED_PARAMETER_CHECK = -1;
    private static int NO_SUCH_ACCOUNT = -2;
    private static int OLD_PASSWORD_ERROR = -3;
    private static int INSUFFICIENT_PASSWORD_STRENGTH = -4;

    @Autowired
    private UmsEventLogMapper umsEventLogMapper;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Autowired
    private UmsAdminService adminService;

    @Autowired
    private UmsRoleService roleService;

    @Autowired
    private OmsOrganizationService omsOrganizationService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    /**
     * 新增一筆帳號
     *
     * @param bearer
     * @param userAgent
     * @param umsAdminParam
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @ValidateOnExecution
    public CommonResult<UmsAdmin> register(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @Validated @RequestBody UmsAdminParam umsAdminParam) {

        //取得呼叫者的資訊
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);

        //判斷是否帳號重複
        UmsAdmin checkAdmin = adminService.getAdminByUsername(umsAdminParam.getUsername());

        if (checkAdmin == null && !umsAdminParam.getUsername().contains(COMPANY_ADMIN_ACCOUNT)) {

            umsAdminParam.setCreator(caller.getUsername());
            umsAdminParam.setCompanyId(caller.getCompanyId());
            UmsAdmin umsAdmin = adminService.register(umsAdminParam);
            if (umsAdmin == null) {
                insertEventLog(caller.getId(), caller.getUsername(), userAgent, "新增帳號", 0, "失敗", "failed:無法新增" + umsAdminParam.getUsername() + "，請檢查欄位:");
                return CommonResult.failed("無法新增，請檢查欄位");
            }
            //新增事件到Table(ums_event_log)
            insertEventLog(caller.getId(), caller.getUsername(), userAgent, "新增帳號 " + umsAdmin.getUsername(), 1, "成功", null);

            //回傳結果
            return CommonResult.success(umsAdmin);
        } else {
            return CommonResult.failed(umsAdminParam.getUsername() + "帳號已存在或為系統保留字，無法新增");
        }
    }

    /**
     * 登入
     *
     * @param userAgent
     * @param umsAdminLoginParam
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(
            @RequestHeader(value = "User-Agent") String userAgent,
            @Validated @RequestBody UmsAdminLoginParam umsAdminLoginParam) {

        String username = umsAdminLoginParam.getUsername();
        String password = umsAdminLoginParam.getPassword();

        try {
            //透過帳號密碼，驗證成功後取得Token
            String token = adminService.login(username, password);

            if (token == null) {
                insertEventLog(999L, username, userAgent, "登入", 0, "失敗", "failed:" + username + "無法登入->" + userAgent);
                return CommonResult.validateFailed("無法登入");
            }

            Map<String, String> tokenMap = new HashMap<>(10);
            tokenMap.put("token", token);
            tokenMap.put("tokenHead", tokenHead);
            //tokenMap.put("FunctionList","[{\"ParentId\":\"\",\"FunctionId\":1200,\"FunctionName\":\"DASHBOARD\",\"Type\":\"fold\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"\"},{\"ParentId\":1200,\"FunctionId\":1201,\"FunctionName\":\"Dashboard\",\"Type\":\"url\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"/Dashboard\"},{\"ParentId\":1200,\"FunctionId\":1202,\"FunctionName\":\"電子地圖\",\"Type\":\"url\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"/Map\"},{\"ParentId\":\"\",\"FunctionId\":1400,\"FunctionName\":\"店鋪管理\",\"Type\":\"fold\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"\"},{\"ParentId\":\"\",\"FunctionId\":1500,\"FunctionName\":\"訂單管理\",\"Type\":\"fold\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"\"},{\"ParentId\":\"\",\"FunctionId\":1900,\"FunctionName\":\"設備管理\",\"Type\":\"fold\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"\"},{\"ParentId\":1900,\"FunctionId\":1901,\"FunctionName\":\"智販機資料維護\",\"Type\":\"url\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"/SVM\"},{\"ParentId\":\"\",\"FunctionId\":2000,\"FunctionName\":\"後台功能管理\",\"Type\":\"fold\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"\"},{\"ParentId\":2000,\"FunctionId\":2001,\"FunctionName\":\"帳號維護\",\"Type\":\"url\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"/Account\"},{\"ParentId\":2000,\"FunctionId\":2002,\"FunctionName\":\"角色管理\",\"Type\":\"url\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"/Role\"},{\"ParentId\":2000,\"FunctionId\":2004,\"FunctionName\":\"店鋪資料維護\",\"Type\":\"url\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"/Store\"},{\"ParentId\":1400,\"FunctionId\":1401,\"FunctionName\":\"智販機庫存查詢\",\"Type\":\"url\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"/StoreInv\"},{\"ParentId\":1500,\"FunctionId\":1501,\"FunctionName\":\"智販機銷售紀錄\",\"Type\":\"url\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"/StoreSale\"},{\"ParentId\":1900,\"FunctionId\":1902,\"FunctionName\":\"智販機事件查詢\",\"Type\":\"url\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"/StoreEvent\"},{\"ParentId\":1900,\"FunctionId\":1903,\"FunctionName\":\"智販機歷史溫度查詢\",\"Type\":\"url\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"/MachineTemperature\"},{\"ParentId\":\"\",\"FunctionId\":1600,\"FunctionName\":\"統計分析\",\"Type\":\"fold\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"\"},{\"ParentId\":1600,\"FunctionId\":1601,\"FunctionName\":\"銷售金額\",\"Type\":\"url\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"/SaleAnalysis\"},{\"ParentId\":1600,\"FunctionId\":1602,\"FunctionName\":\"銷售商品\",\"Type\":\"url\",\"IconPath\":\"\",\"Button\":{},\"Edit\":1,\"Operate\":1,\"Url\":\"/ProductAnalysis\"}]");

            //更新登入時間
            UmsAdmin umsAdmin = adminService.getAdminByUsername(username);
            umsAdmin.setLoginTime(new Date());
            adminService.update(umsAdmin.getId(), umsAdmin);

            //新增事件到Table(ums_event_log)
            insertEventLog(umsAdmin.getId(), umsAdmin.getUsername(), userAgent, "登入", 1, "成功", null);

            return CommonResult.success(tokenMap);

        } catch (Exception ex) {
            UmsAdmin badUser = adminService.getAdminByUsername(username);
            if (badUser == null) {
                insertEventLog(999L, username, userAgent, "登入", 0, "失敗", ex.toString());
            } else {
                insertEventLog(badUser.getId(), username, userAgent, "登入", 0, "失敗", ex.toString());
            }
            return CommonResult.failed();
        }
    }

    /**
     * 更新Token
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/refreshToken", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult refreshToken(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            HttpServletRequest request) {

        String token = request.getHeader(tokenHeader);

        //取得呼叫者的資訊 caller
        String token2 = bearer;
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);

        String refreshToken = adminService.refreshToken(token);

        if (refreshToken == null) {
            //新增事件到Table(ums_event_log)
            insertEventLog(caller.getId(), caller.getUsername(), userAgent, "/admin/refreshToken", 0, "失敗", "failed:Token已經過期");
            return CommonResult.failed("Token已經過期！");
        }

        Map<String, String> tokenMap = new HashMap<>(10);
        tokenMap.put("token", refreshToken);
        tokenMap.put("tokenHead", tokenHead);

        //新增事件到Table(ums_event_log)
        insertEventLog(caller.getId(), caller.getUsername(), userAgent, "/admin/refreshToken", 1, "成功", null);
        return CommonResult.success(tokenMap);
    }


    /**
     * 取得帳號資訊
     *
     * @param bearer
     * @param userAgent
     * @param principal
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getAdminInfo(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal) {

        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);

        if (principal == null) {
            return CommonResult.unauthorized(null);
        }

        String username = principal.getName();

        UmsAdmin umsAdmin = adminService.getAdminByUsername(username);
        Map<String, Object> data = new HashMap<>(10);
        data.put("username", umsAdmin.getUsername());
        data.put("menus", roleService.getMenuList(umsAdmin.getId()));

        List<UmsRole> roleList = adminService.getRoleList(umsAdmin.getId());
        if (CollUtil.isNotEmpty(roleList)) {
            List<String> roles = roleList.stream().map(UmsRole::getName).collect(Collectors.toList());
            data.put("roles", roles);
        }

        //新增事件到Table(ums_event_log)
        return CommonResult.success(data);
    }


    /**
     * 登出
     *
     * @param bearer
     * @param userAgent
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent) {

        //取得呼叫者的資訊
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);

        //新增事件到Table(ums_event_log)
        insertEventLog(caller.getId(), caller.getUsername(), userAgent, "登出", 1, "成功", null);

        return CommonResult.success("ok");
    }

    /**
     * 列舉該公司下的所有帳號
     *
     * @param bearer
     * @param keyword
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<UmsAdmin>> list(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        //取得呼叫者的資訊
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);

        Page<UmsAdmin> adminList = adminService.list(caller.getCompanyId(), keyword, pageSize, pageNum);

        return CommonResult.success(CommonPage.restPage(adminList));
    }

    @RequestMapping(value = "/listMyAccount", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<UmsAdmin>> listMyAccount(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        //取得呼叫者的資訊
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);

        Page<UmsAdmin> adminList = adminService.list(caller.getCompanyId(), caller.getUsername(), pageSize, pageNum);

        return CommonResult.success(CommonPage.restPage(adminList));
    }

    /**
     * 依據帳號ID，取得該帳號的資訊
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<UmsAdmin> getItem(@PathVariable Long id) {
        UmsAdmin admin = adminService.getById(id);
        return CommonResult.success(admin);
    }

    /**
     * 依據帳號ID，更新該帳號的資訊
     *
     * @param bearer
     * @param id
     * @param admin
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id, @RequestBody UmsAdmin admin) {

        //取得呼叫者的資訊
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);

        UmsAdmin user = adminService.getById(id);
        //取得組織代碼
        log.info("umsAdminParam :{}", admin.getOrgId());
        OmsOrganization omsOrganization = new OmsOrganization();
        omsOrganization = omsOrganizationService.getById(admin.getOrgId());

        boolean success = adminService.update(id, admin);
        if (success) {
            //新增事件到Table(ums_event_log)
            insertEventLog(caller.getId(), caller.getUsername(), userAgent, "更新帳號資訊", 1, "成功", user.toString());
            return CommonResult.success(null);
        }

        //新增事件到Table(ums_event_log)
        insertEventLog(caller.getId(), caller.getUsername(), userAgent, "更新帳號資訊", 0, "失敗", user.toString());
        return CommonResult.failed();
    }


    /**
     * 更新該用戶的密碼
     * 未更新公司類別
     *
     * @param updatePasswordParam
     * @return
     */
    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updatePassword(
            @Validated @RequestBody UpdateAdminPasswordParam updatePasswordParam) {

        int status = adminService.updatePassword(updatePasswordParam);
        /**
         *     private static int FAILED_PARAMETER_CHECK  = -1;
         *     private static int NO_SUCH_ACCOUNT = -2;
         *     private static int OLD_PASSWORD_ERROR=-3;
         *     private static int INSUFFICIENT_PASSWORD_STRENGTH = -4;
         */
        if (status > 0) {
            return CommonResult.success(null);
        } else if (status == FAILED_PARAMETER_CHECK) {
            return CommonResult.failed("未通過參數檢查");
        } else if (status == NO_SUCH_ACCOUNT) {
            return CommonResult.failed("無該用戶帳號");
        } else if (status == OLD_PASSWORD_ERROR) {
            return CommonResult.failed("舊密碼錯誤");
        } else if (status == INSUFFICIENT_PASSWORD_STRENGTH) {
            return CommonResult.passwordError("密碼強度不足");
        } else {
            return CommonResult.failed();
        }
    }

    /**
     * 依據帳號ID，刪除該帳號的資訊
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id) {

        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        UmsAdmin user = adminService.getById(id);
        if (user.getUsername().contains(COMPANY_ADMIN_ACCOUNT)) {
            return CommonResult.failed("系統帳號不可刪除");
        } else {
            boolean success = adminService.delete(id);

            if (success) {
                insertEventLog(caller.getId(), caller.getUsername(), userAgent, "/admin/delete/" + id + ",username:" + user.getUsername(), 1, "成功", null);
                return CommonResult.success(null);
            }
            insertEventLog(caller.getId(), caller.getUsername(), userAgent, "/admin/delete/" + id + ",username:" + user.getUsername(), 0, "失敗", null);
        }
        return CommonResult.failed();
    }

    /**
     * 更新帳號的起停狀態
     *
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id,
            @RequestParam(value = "status") Integer status) {

        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        UmsAdmin user = adminService.getById(id);

        String statusMessage = "";
        if (status > 0) {
            statusMessage = "帳號 Enabled";
        } else {
            statusMessage = "帳號 Disabled";
        }
        UmsAdmin umsAdmin = new UmsAdmin();
        umsAdmin.setStatus(status);
        boolean success = adminService.update(id, umsAdmin);
        if (success) {
            insertEventLog(caller.getId(), caller.getUsername(), userAgent, "/admin/updateStatus/" + id + ",username:" + user.getUsername(), 1, "成功:", "success:" + statusMessage);
            return CommonResult.success(null);
        } else {
            insertEventLog(caller.getId(), caller.getUsername(), userAgent, "/admin/updateStatus/" + id + ",username:" + user.getUsername(), 0, "失敗", "failed:" + statusMessage);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRole(@RequestParam("adminId") Long adminId,
                                   @RequestParam("roleIds") List<Long> roleIds) {
        int count = adminService.updateRole(adminId, roleIds);
        if (count >= 0) {
            return CommonResult.success(count);
        }
        return CommonResult.failed();
    }


    /**
     * 增加公司別判斷
     *
     * @param adminId
     * @return
     */
    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsRole>> getRoleList(@PathVariable Long adminId) {
        List<UmsRole> roleList = adminService.getRoleList(adminId);
        return CommonResult.success(roleList);
    }

    /**
     * 紀錄事件
     *
     * @param username
     * @param userAgent
     * @param event
     * @param result
     * @return
     */
    private int insertEventLog(Long userId, String username, String userAgent, String event, Integer status, String result, String memo) {
        //EventLog的初始化
        try {
            UmsEventLog eventLog = new UmsEventLog();
            eventLog.setUserId(userId);
            eventLog.setUsername(username);
            eventLog.setCreateTime(new Date());
            eventLog.setMemo(userAgent);
            eventLog.setEvent(event);
            eventLog.setStatus(status);
            eventLog.setResult(result);
            eventLog.setMemo(memo);
            return umsEventLogMapper.insert(eventLog);
        } catch (Exception ex) {
            log.info(String.valueOf(ex));
            return -1;
        }
    }
}
