package my.project.modules.oms.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 組織資訊
 *
 * @author kevinchang
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_organization")
public class OmsOrganization implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "公司代碼")
    private Long companyId;

    @Schema(description = "上層組織代碼")
    private Long parentId;

    @Schema(description = "組織編號")
    private String nameSn;

    @Schema(description = "組織名稱")
    private String name;

    @Schema(description = "層級")
    private Integer level;

    @Schema(description = "狀態")
    private Integer status;

    @Schema(description = "創建時間")
    private Date createTime;

    @Schema(description = "排序")
    private Integer sort;


}
