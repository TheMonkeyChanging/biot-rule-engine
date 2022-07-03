package org.biot.rule.engine.domain.rule.matcher;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RuleMatcherFactory {
    /**
     * 规则匹配器map，key为tenantId
     */
    private Map<String, RuleMatcher> ruleMap = new ConcurrentHashMap<>();

    /**
     * 获取租户规则匹配器
     *
     * @param tenantId
     * @return
     */
    public RuleMatcher of(String tenantId) {
        RuleMatcher matcher = ruleMap.get(tenantId);
        if (matcher == null) {
            synchronized (this) {
                matcher = ruleMap.get(tenantId);
                if (matcher == null) {
                    matcher = create(tenantId);
                }
            }
        }
        return matcher;
    }

    private RuleMatcher create(String tenantId) {
        return null;
    }
}
