package org.biot.rule.engine.domain.rule.model;

import org.biot.rule.engine.domain.rule.RuleId;

import java.util.List;

/**
 * 规则模型资源库
 */
public interface RuleModelRepository {
    /**
     * 根据ID获取规则模型
     *
     * @param ruleId
     * @return
     */
    RuleModel modelOfRuleId(RuleId ruleId);

    /**
     * 获取租户下的规则引擎列表
     *
     * @param tenantId
     * @return
     */
    List<RuleModel> enabledModelsOfTenant(String tenantId);
}
