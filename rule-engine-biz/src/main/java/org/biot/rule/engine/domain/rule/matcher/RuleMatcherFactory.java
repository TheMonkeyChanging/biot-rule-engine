package org.biot.rule.engine.domain.rule.matcher;

import org.biot.rule.engine.domain.aggregation.AggregationRepository;
import org.biot.rule.engine.domain.rule.model.RuleModel;
import org.biot.rule.engine.domain.rule.model.RuleModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RuleMatcherFactory {
    @Autowired
    private RuleModelRepository modelRepository;

    @Autowired
    private AggregationRepository aggregationRepository;

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
                    // todo put into map
                }
            }
        }
        return matcher;
    }

    /**
     *
     *
     * @param tenantId
     * @return
     */
    private RuleMatcher create(String tenantId) {
        List<RuleModel> ruleModels =  modelRepository.enabledModelsOfTenant(tenantId);
        RuleMatcher matcher = new RuleMatcher();
        matcher.build(ruleModels, aggregationRepository);
        return matcher;
    }
}
