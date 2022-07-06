package org.biot.rule.engine.domain.rule.matcher;

import org.biot.rule.engine.domain.aggregation.Aggregation;
import org.biot.rule.engine.domain.aggregation.AggregationId;
import org.biot.rule.engine.domain.aggregation.AggregationRepository;
import org.biot.rule.engine.domain.rule.model.RuleModel;
import org.biot.rule.engine.domain.rule.model.trigger.DeviceTrigger;
import org.biot.rule.engine.domain.rule.model.trigger.Trigger;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

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
     * 读写锁
     */
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 查找匹配的规则模型
     *
     * @param productId
     * @param deviceId
     * @return
     */
    public Set<RuleModel> findRule(String productId, String deviceId) {
        Set<RuleModel> rs = new HashSet<>();

        try {
            lock.readLock().lock();
            Set<RuleModel> fromProduct = productMap.get(productId);
            if (!CollectionUtils.isEmpty(fromProduct)) {
                rs.addAll(fromProduct);
            }
            Set<RuleModel> fromDevice = deviceMap.get(deviceId);
            if (!CollectionUtils.isEmpty(fromDevice)) {
                rs.addAll(fromDevice);
            }
        } finally {
            lock.readLock().unlock();
        }

        return rs;
    }

    /**
     * 构建匹配表
     *
     * @param models
     * @param repository
     */
    void build(List<RuleModel> models, AggregationRepository repository) {
        Map<String, Set<RuleModel>> pMap = new HashMap<>();
        Map<String, Set<RuleModel>> dMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(models)) {
            for (RuleModel model : models) {
                if (model.isAggregationTriggerSource()) {
                    parseAggregation(dMap, model, repository);
                } else {
                    parseMatcher(pMap, dMap, model);
                }
            }
        }

        lock.writeLock().lock();
        productMap = pMap;
        deviceMap = dMap;
        lock.writeLock().unlock();
    }

    /**
     * 聚合触发源构建
     *
     * @param dMap
     * @param model
     * @param repository
     */
    private void parseAggregation(Map<String, Set<RuleModel>> dMap, RuleModel model, AggregationRepository repository) {
        String aggregationId = ""; // todo
        Aggregation aggregation = repository.of(new AggregationId(model.getRuleId().getTenantId(), aggregationId));
        if (aggregation != null && !CollectionUtils.isEmpty(aggregation.getDevices())) {
            aggregation.getDevices().forEach(d -> putIntoMap(dMap, d.getDeviceId(), model));
        }
    }

    /**
     * 单源构建
     *
     * @param pMap
     * @param dMap
     * @param model
     */
    private void parseMatcher(Map<String, Set<RuleModel>> pMap, Map<String, Set<RuleModel>> dMap, RuleModel model) {
        Trigger trigger = model.getTrigger();
        if (trigger instanceof DeviceTrigger) {
            DeviceTrigger deviceTrigger = (DeviceTrigger) trigger;
            if (deviceTrigger.isAnyDevice()) {
                putIntoMap(pMap, deviceTrigger.getProductId(), model);
            } else {
                putIntoMap(dMap, deviceTrigger.getDeviceId(), model);
            }
        }
    }

    /**
     * 放入内存map
     *
     * @param map
     * @param key
     * @param model
     */
    private void putIntoMap(Map<String, Set<RuleModel>> map, String key, RuleModel model) {
        Set<RuleModel> modelSet = map.get(key);
        if (modelSet == null) {
            modelSet = new HashSet<>();
            map.put(key, modelSet);
        }
        modelSet.add(model);
    }
}
