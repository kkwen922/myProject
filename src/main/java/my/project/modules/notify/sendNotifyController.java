package my.project.modules.notify;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import my.project.common.api.CommonResult;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author kevinchang
 */
@Controller
@RequestMapping("/notify")
@Slf4j
@Tag(name = "sendNotify Controller")
public class sendNotifyController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createCompany(
            @RequestBody SendMessageParam sendMessageParam) {

        rabbitTemplate.convertAndSend("teamplus.queue", sendMessageParam);

        return CommonResult.success(sendMessageParam);
    }
}
