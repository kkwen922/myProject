package my.project.modules.ums.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 後台角色資源角色關聯
 * </p>

 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("ums_role_resource_relation")
@ApiModel(value="UmsRoleResourceRelation對象", description="後台角色資源關聯")
public class UmsRoleResourceRelation implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色ID")
    private Long roleId;

    @ApiModelProperty(value = "資源ID")
    private Long resourceId;


}
