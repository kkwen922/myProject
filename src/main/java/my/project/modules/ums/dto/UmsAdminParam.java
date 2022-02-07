package my.project.modules.ums.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * 用户登入參數
 */
@Getter
@Setter
public class UmsAdminParam {
    @NotEmpty
    @ApiModelProperty(value = "用户名稱", required = true)
    private String username;
    @NotEmpty
    @ApiModelProperty(value = "密碼", required = true)
    private String password;
    @ApiModelProperty(value = "用戶照片")
    private String icon;
    @Email
    @ApiModelProperty(value = "電郵")
    private String email;
    @ApiModelProperty(value = "用戶暱稱")
    private String nickName;
    @ApiModelProperty(value = "備註")
    private String note;
}
