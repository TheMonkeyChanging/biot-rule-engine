package org.biot.rule.engine.client;

import org.biot.BiotResult;
import org.biot.rule.engine.dto.CreateRuleRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/rule")
@FeignClient(name = "rule-engine", path = "/rule", contextId = "RuleClient")
public interface RuleClient {
    /**
     * 创建规则，仅仅是规则，不包括规则的触发器、条件及动作
     *
     * @param tenantId
     * @param createRuleRequest
     * @return
     */
    @PostMapping(path = "/create")
    BiotResult<String> create(@RequestParam("tenantId") String tenantId,
                              @RequestBody @Validated CreateRuleRequest createRuleRequest);
}
