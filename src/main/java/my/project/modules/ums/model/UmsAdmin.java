package my.project.modules.ums.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 後台用戶
 * </p>
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_admin")
@ApiModel(value="UmsAdmin對象", description="後台用户")
public class UmsAdmin implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String username;

    private String password;

    @ApiModelProperty(value = "照片")
    private String icon;

    @ApiModelProperty(value = "電郵")
    private String email;

    @ApiModelProperty(value = "暱稱")
    private String nickName;

    @ApiModelProperty(value = "備註")
    private String note;

    @ApiModelProperty(value = "創建時間")
    private Date createTime;

    @ApiModelProperty(value = "最後登入時間")
    private Date loginTime;

    @ApiModelProperty(value = "帳號狀態：0->禁用；1->啟用")
    private Integer status;


}
