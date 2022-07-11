package org.biot.rule.engine.driving.msg;

import org.biot.rule.engine.domain.service.RuleExecuteService;
import org.biot.things.core.message.ThingsCoreMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 与规则引擎相关的消息处理器
 */
@Component
public class RuleMsgProcessor {
    private static final Logger log = LoggerFactory.getLogger(RuleMsgProcessor.class);

    @Autowired
    private RuleExecuteService ruleExecuteService;

    /**
     * 处理接收到的消息
     *
     * @param message
     * @return
     */
    public void processReceivedMessage(ThingsCoreMessage message) {
        try {
            ruleExecuteService.processMessage(message);
        } catch (Exception e) {
            // todo 业务侧重试
            log.warn("Process msg exception: ", e);
        }
    }
}
