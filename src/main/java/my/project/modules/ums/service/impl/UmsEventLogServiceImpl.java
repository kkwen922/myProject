package my.project.modules.ums.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import my.project.modules.ums.mapper.UmsAdminMapper;
import my.project.modules.ums.mapper.UmsEventLogMapper;
import my.project.modules.ums.model.UmsAdmin;
import my.project.modules.ums.model.UmsEventLog;
import my.project.modules.ums.model.UmsMenu;
import my.project.modules.ums.model.UmsResource;
import my.project.modules.ums.service.UmsAdminService;
import my.project.modules.ums.service.UmsEventLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author kevinchang
 */
@Service
@Slf4j
public class UmsEventLogServiceImpl extends ServiceImpl<UmsEventLogMapper, UmsEventLog> implements UmsEventLogService {

    @Autowired
    UmsEventLogMapper umsEventLogMapper;

    @Override
    public Page<UmsEventLog> list(Long userId, String keyword, Integer pageSize, Integer pageNum) {

        Page<UmsEventLog> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsEventLog> wrapper = new QueryWrapper<>();
        LambdaQueryWrapper<UmsEventLog> lambda = wrapper.lambda();

        wrapper.lambda().eq(UmsEventLog::getUserId, userId)
                .orderByDesc(UmsEventLog::getCreateTime);

        if (StrUtil.isNotEmpty(keyword)) {
            lambda.like(UmsEventLog::getEvent, keyword).or()
                    .eq(UmsEventLog::getResult, keyword);
        }

        return page(page, wrapper);

    }
}
