package my.project.modules.ums.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import my.project.modules.ums.model.UmsAdmin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 後台用户 Mapper 接口
 * </p>
 */
public interface UmsAdminMapper extends BaseMapper<UmsAdmin> {

    /**
     * 獲得資源相關用户ID列表
     */
    List<Long> getAdminIdList(@Param("resourceId") Long resourceId);

}
