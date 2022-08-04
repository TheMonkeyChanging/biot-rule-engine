package org.biot.rule.engine.infrastructure.repository.rule;

import org.biot.rule.engine.domain.rule.Rule;
import org.biot.rule.engine.domain.rule.RuleRepository;
import org.biot.rule.engine.infrastructure.dal.mapper.rule.RuleMapper;
import org.biot.rule.engine.infrastructure.dal.model.rule.RuleDo;
import org.biot.rule.engine.infrastructure.repository.rule.convert.RuleAssembleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * 仓库实现类
 */
@Repository
public class RuleRepositoryImpl implements RuleRepository {
    @Autowired
    private RuleMapper ruleMapper;

    /**
     * 创建规则
     *
     * @param rule
     */
    @Override
    public void create(Rule rule) {
        ruleMapper.insert(RuleAssembleMapper.INSTANCE.toRuleDo(rule));
    }

    /**
     * 获取租户下的所有规则
     *
     * @param tenantId
     * @return
     */
    @Override
    public List<Rule> ofTenant(String tenantId) {
        List<Rule> rules = new ArrayList<>();
        List<RuleDo> ruleDoList = ruleMapper.selectAllRulesOfTenant(tenantId);
        ruleDoList.forEach(r -> rules.add(RuleAssembleMapper.INSTANCE.toRule(r)));
        return rules;
    }

    /**
     * 获取租户下的所有启用的规则
     *
     * @param tenantId
     * @return
     */
    @Override
    public List<Rule> enabledRulesOfTenant(String tenantId) {
        List<Rule> rules = new ArrayList<>();
        List<RuleDo> ruleDoList = ruleMapper.selectEnabledRulesOfTenant(tenantId);
        ruleDoList.forEach(r -> rules.add(RuleAssembleMapper.INSTANCE.toRule(r)));
        return rules;
    }
}
