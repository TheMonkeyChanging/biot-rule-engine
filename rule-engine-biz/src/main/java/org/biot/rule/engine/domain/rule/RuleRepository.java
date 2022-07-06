package org.biot.rule.engine.domain.rule;

import java.util.List;

public interface RuleRepository {
    /**
     * 获取租户下的所有规则
     *
     * @param tenantId
     * @return
     */
    List<Rule> ofTenant(String tenantId);

    /**
     * 获取租户下的所有启用的规则
     *
     * @param tenantId
     * @return
     */
    List<Rule> enabledRulesOfTenant(String tenantId);
}
