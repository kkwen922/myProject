package my.project.modules.ums.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import my.project.common.api.CommonPage;
import my.project.common.api.CommonResult;
import my.project.modules.ums.mapper.UmsEventLogMapper;
import my.project.modules.ums.model.*;
import my.project.modules.ums.service.UmsRoleService;
import my.project.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 後台用户角色管理
 * @author : kevin Chang
 */
@Controller
@RequestMapping("/role")
@Slf4j
@Tag(name = "UmsRole Controller")
public class UmsRoleController {
    @Autowired
    private UmsRoleService roleService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UmsEventLogMapper umsEventLogMapper;

    /**
     * 新增角色
     *
     * @param bearer
     * @param userAgent
     * @param role
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestBody UmsRole role) {

        //取得呼叫者的資訊
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);
        role.setCompanyId(caller.getCompanyId());

        boolean success = roleService.create(role);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id,
            @RequestBody UmsRole role) {

        //取得呼叫者的資訊
        UmsAdmin caller = jwtTokenUtil.getCallerInfoFromToken(bearer);

        role.setId(id);
        boolean success = roleService.updateById(role);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam("ids") List<Long> ids) {

        //取得呼叫者的資訊
        UmsAdmin admin = jwtTokenUtil.getCallerInfoFromToken(bearer);

        boolean success = roleService.delete(ids);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }


    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsRole>> listAll(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent) {

        //取得呼叫者的資訊
        UmsAdmin admin = jwtTokenUtil.getCallerInfoFromToken(bearer);

        List<UmsRole> roleList = roleService.listRoleByCompanyId(admin.getCompanyId());
        return CommonResult.success(roleList);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<UmsRole>> list(
            @RequestHeader(value = "Authorization") String bearer,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        //取得呼叫者的資訊
        UmsAdmin admin = jwtTokenUtil.getCallerInfoFromToken(bearer);

        Page<UmsRole> roleList = roleService.listByCompanyId(admin.getCompanyId(), keyword, pageSize, pageNum);
        return CommonResult.success(CommonPage.restPage(roleList));
    }

    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(@PathVariable Long id, @RequestParam(value = "status") Integer status) {
        UmsRole umsRole = new UmsRole();
        umsRole.setId(id);
        umsRole.setStatus(status);
        boolean success = roleService.updateById(umsRole);
        if (success) {
            return CommonResult.success(null);
        }
        return CommonResult.failed();
    }

    @RequestMapping(value = "/listMenu/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsMenu>> listMenu(@PathVariable Long roleId) {
        List<UmsMenu> roleList = roleService.listMenu(roleId);
        return CommonResult.success(roleList);
    }

    @RequestMapping(value = "/listResource/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<UmsResource>> listResource(@PathVariable Long roleId) {
        List<UmsResource> roleList = roleService.listResource(roleId);
        return CommonResult.success(roleList);
    }

    @RequestMapping(value = "/allocMenu", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult allocMenu(@RequestParam Long roleId, @RequestParam List<Long> menuIds) {
        int count = roleService.allocMenu(roleId, menuIds);
        return CommonResult.success(count);
    }

    @RequestMapping(value = "/allocResource", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult allocResource(@RequestParam Long roleId, @RequestParam List<Long> resourceIds) {
        int count = roleService.allocResource(roleId, resourceIds);
        return CommonResult.success(count);
    }

}
