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
 * @author kevinchang
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("oms_company")
public class OmsCompany implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Schema(description = "公司名稱")
    private String companyName;

    @Schema(description = "狀態")
    private Integer status;

    @Schema(description = "創建時間")
    private Date createTime;

    @Schema(description = "統一編號")
    private Long invoiceNumber;
}
