package org.biot.rule.engine.domain.rule.matcher;

import org.biot.rule.engine.domain.rule.model.RuleModel;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 规则匹配器，每个租户一个
 */
public class RuleMatcher {
    /**
     * product对应的规则
     */
    private volatile Map<String, Set<RuleModel>> productMap = new HashMap<>();

    /**
     * device对应的规则
     */
    private volatile Map<String, Set<RuleModel>> deviceMap = new HashMap<>();

    /**
     * 查找匹配的规则模型
     *
     * @param productId
     * @param deviceId
     * @return
     */
    public Set<RuleModel> findRule(String productId, String deviceId) {
        Set<RuleModel> rs = new HashSet<>();
        Set<RuleModel> fromProduct = productMap.get(productId);
        if (!CollectionUtils.isEmpty(fromProduct)) {
            rs.addAll(fromProduct);
        }
        Set<RuleModel> fromDevice = deviceMap.get(deviceId);
        if (!CollectionUtils.isEmpty(fromDevice)) {
            rs.addAll(fromDevice);
        }
        return rs;
    }
}
