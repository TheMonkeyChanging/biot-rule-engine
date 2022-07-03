package org.biot.rule.engine.infrastructure.mq.rabbit.consumer;

import com.alibaba.fastjson.JSON;
import org.biot.BiotResult;
import org.biot.rule.engine.driving.msg.RuleMsgProcessor;
import org.biot.things.core.message.ThingsCoreMessage;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 与规则引擎相关的消息消费
 */
@Component
public class RuleMessageConsumer {
    @Autowired
    private RuleMsgProcessor ruleMsgProcessor;

    @RabbitListener(queues = "ruleEngineQueue")
    @RabbitHandler
    public void process(ThingsCoreMessage message) {
        System.out.println(System.currentTimeMillis() + ": 消费者接受到的消息是：" + JSON.toJSONString(message));
        ruleMsgProcessor.processReceivedMessage(message);
//        BiotResult rs = msgProcessor.processReceivedMessage(message);
//        System.out.println(JSON.toJSONString(rs));
    }
}
