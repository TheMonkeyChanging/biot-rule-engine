package org.biot.rule.engine.domain.service;

import org.biot.message.BiotMessageType;
import org.biot.rule.engine.domain.rule.matcher.RuleMatcher;
import org.biot.rule.engine.domain.rule.matcher.RuleMatcherFactory;
import org.biot.rule.engine.domain.rule.model.RuleModel;
import org.biot.rule.engine.domain.rule.trigger.ReportedPropertyValue;
import org.biot.rule.engine.domain.rule.trigger.Trigger;
import org.biot.rule.engine.domain.rule.trigger.TriggerSourceIdentify;
import org.biot.things.core.dto.msg.PropertiesUpdateDto;
import org.biot.things.core.message.ThingsCoreMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;

import static org.biot.rule.engine.domain.rule.trigger.TriggerSourceType.DEVICE_PROPERTY;

/**
 * 规则引擎执行服务，处理设备相关消息
 */
@Service
public class RuleExecuteService {
    private static final Logger log = LoggerFactory.getLogger(RuleExecuteService.class);

    @Autowired
    private RuleMatcherFactory matcherFactory;

    @Autowired
    private Judge judge;

    public void processMessage(ThingsCoreMessage message) {
        if (message.getType() == BiotMessageType.DEVICE_PROPERTY) {
            PropertiesUpdateDto updateDto = (PropertiesUpdateDto) message.getBody();
            processPropertyMessage(updateDto);
        }

    }

    private void processPropertyMessage(PropertiesUpdateDto updateDto) {
        // 根据设备信息，查找可能匹配的规则
        RuleMatcher matcher = matcherFactory.of(updateDto.getTenantId());
        Set<RuleModel> ruleModelSet = matcher.findRule(updateDto.getProductId(), updateDto.getDeviceId());
        if (CollectionUtils.isEmpty(ruleModelSet)) {
            log.debug("No rule found for {}--{}", updateDto.getProductId(), updateDto.getDeviceId());
            return;
        }

        // 多个规则、多个属性值，遍历执行
        for (RuleModel model : ruleModelSet) {
            for (Map.Entry<String, Object> entry : updateDto.getValues().entrySet()) {
                TriggerSourceIdentify<String> sourceIdentify = new TriggerSourceIdentify(DEVICE_PROPERTY, entry.getKey());
                // 仅当匹配规则所指定的属性时，执行相关操作流程
                if (model.isMatch(sourceIdentify)) {
                    ReportedPropertyValue pv = convert(updateDto, entry);
                    execute(model, pv);
                }
            }
        }
    }

    private void execute(RuleModel model, ReportedPropertyValue pv) {
        // todo 区分聚合触发源与单设备源的规则模型
        Trigger trigger = model.getTrigger();
        boolean triggered = trigger.isTriggered(pv);
        if (triggered) {
            if (model.hasCondition()) {
                // 执行条件检查通过，执行action
                if (judge.checkCondition(model, pv)) {
                    // do action
                } else {
                    log.info("Triggered but not passed: rule({}), source({}), property({})",
                            model.getRuleId().getUuid(), pv.getDeviceId(), pv.getPropertyId());
                }
            } else {
                // do action
            }
        } else {
            if (model.hasContinuousCondition()) {
                // do reset
                judge.resetContinuousState(model, pv);
            } else {
                log.info("Rule {} not triggered, {} is {}.", model.getRuleId().getUuid(),
                        pv.getPropertyId(), pv.getPropertyValue());
            }
        }
    }

    /**
     * @param updateDto
     * @param entry
     * @return
     */
    private ReportedPropertyValue convert(PropertiesUpdateDto updateDto, Map.Entry<String, Object> entry) {
        ReportedPropertyValue propertyValue = new ReportedPropertyValue();
        BeanUtils.copyProperties(updateDto, propertyValue);
        propertyValue.setPropertyId(entry.getKey());
        propertyValue.setPropertyValue(entry.getValue());
        return propertyValue;
    }
}
