package org.biot.rule.engine.domain.service;

import org.biot.BiotResult;
import org.biot.rule.engine.domain.rule.Rule;
import org.biot.rule.engine.domain.rule.RuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RuleManageService {
    @Autowired
    private RuleRepository ruleRepository;

    /**
     * 创建规则，返回规则ID
     *
     * @param rule
     * @return
     */
    public BiotResult<String> create(Rule rule) {
        /**
         * 1. 逻辑判断：如重名
         * 2. 创建
         */
        ruleRepository.create(rule);
        return BiotResult.successResult(rule.getRuleId().getUuid());
    }

}
