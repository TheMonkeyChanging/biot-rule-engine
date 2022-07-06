package org.biot.rule.engine.infrastructure.repository.rule;

import org.biot.rule.engine.domain.rule.RuleId;
import org.biot.rule.engine.domain.rule.model.RuleModel;
import org.biot.rule.engine.domain.rule.model.RuleModelRepository;
import org.biot.rule.engine.domain.rule.model.condition.Condition;
import org.biot.rule.engine.domain.rule.model.trigger.Trigger;
import org.biot.rule.engine.infrastructure.dal.mapper.rule.RuleMapper;
import org.biot.rule.engine.infrastructure.dal.mapper.rule.RuleModelMapper;
import org.biot.rule.engine.infrastructure.dal.model.rule.ConditionDo;
import org.biot.rule.engine.infrastructure.dal.model.rule.RuleDo;
import org.biot.rule.engine.infrastructure.dal.model.rule.RuleTriggerDo;
import org.biot.rule.engine.infrastructure.repository.rule.convert.ConditionConverter;
import org.biot.rule.engine.infrastructure.repository.rule.convert.TriggerConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 规则模型资源库实现
 */
@Repository
public class RuleModelRepositoryImpl implements RuleModelRepository {
    @Autowired
    private RuleMapper ruleMapper;

    @Autowired
    private RuleModelMapper modelMapper;

    /**
     * 根据ID获取规则模型
     *
     * @param ruleId
     * @return
     */
    @Override
    public RuleModel modelOfRuleId(RuleId ruleId) {
        return null;
    }

    /**
     * 获取租户下的规则引擎列表
     *
     * @param tenantId
     * @return
     */
    @Override
    public List<RuleModel> enabledModelsOfTenant(String tenantId) {
        Map<String, RuleDo> enabledRules = enabledRulesOfTenant(tenantId);
        Map<String, RuleModel> modelMap = new HashMap<>();
        enabledRules.forEach((k, v) -> modelMap.put(k, new RuleModel(new RuleId(tenantId, k))));

        readAndSetTriggers(tenantId, modelMap);
        readAndSetConditions(tenantId, modelMap);


        return new ArrayList<>(modelMap.values());
    }

    /**
     * 获取租户下所有启用的规则，返回结果为map
     *
     * @param tenantId
     * @return
     */
    private Map<String, RuleDo> enabledRulesOfTenant(String tenantId) {
        Map<String, RuleDo> rs = new HashMap<>();
        List<RuleDo> ruleDos = ruleMapper.selectEnabledRulesOfTenant(tenantId);
        if (!CollectionUtils.isEmpty(ruleDos)) {
            ruleDos.forEach(r -> rs.put(r.getUuid(), r));
        }
        return rs;
    }

    /**
     * 读取并设置规则的触发器
     *
     * @param tenantId
     * @param modelMap
     */
    private void readAndSetTriggers(String tenantId, Map<String, RuleModel> modelMap) {
        List<RuleTriggerDo> triggerDos = modelMapper.selectTriggersOfTenant(tenantId);
        for (RuleTriggerDo triggerDo : triggerDos) {
            RuleModel model = modelMap.get(triggerDo.getRuleId());
            if (model != null) {
                Trigger trigger = TriggerConverter.convert(triggerDo);
                model.setTrigger(trigger);
            }
        }
    }

    /**
     * 读取并设置规则的执行条件
     *
     * @param tenantId
     * @param modelMap
     */
    private void readAndSetConditions(String tenantId, Map<String, RuleModel> modelMap) {
        List<ConditionDo> conditionDos = modelMapper.selectConditionsOfTenant(tenantId);
        for (ConditionDo conditionDo : conditionDos) {
            RuleModel model = modelMap.get(conditionDo.getRuleId());
            if (model != null) {
                Condition condition = ConditionConverter.convert(conditionDo);
                model.setCondition(condition);
            }
        }
    }

}
