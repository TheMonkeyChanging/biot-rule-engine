package org.biot.rule.engine.driving.controller;

import org.biot.BiotResult;
import org.biot.EntityId;
import org.biot.rule.engine.client.RuleClient;
import org.biot.rule.engine.domain.rule.Rule;
import org.biot.rule.engine.domain.rule.RuleId;
import org.biot.rule.engine.domain.service.RuleManageService;
import org.biot.rule.engine.dto.CreateRuleRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RuleController implements RuleClient {
    @Autowired
    private RuleManageService ruleManageService;

    /**
     * 创建规则，仅仅是规则，不包括规则的触发器、条件及动作
     *
     * @param tenantId
     * @param createRuleRequest
     * @return
     */
    @Override
    public BiotResult<String> create(String tenantId, CreateRuleRequest createRuleRequest) {
        Rule rule = buildRule(tenantId, createRuleRequest);
        return ruleManageService.create(rule);
    }

    /**
     * 构建Rule
     *
     * @param tenantId
     * @param createRuleRequest
     * @return
     */
    private Rule buildRule(String tenantId, CreateRuleRequest createRuleRequest) {
        Rule rule = new Rule();
        rule.setName(createRuleRequest.getName());
        rule.setDesc(createRuleRequest.getDesc());
        rule.setRuleId(new RuleId(tenantId, EntityId.uuid32String()));
        rule.setEnabled(false);
        return rule;
    }
}
