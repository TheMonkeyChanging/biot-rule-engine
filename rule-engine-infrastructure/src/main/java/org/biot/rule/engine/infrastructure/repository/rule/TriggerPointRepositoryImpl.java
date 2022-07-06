package org.biot.rule.engine.infrastructure.repository.rule;

import com.alibaba.fastjson.JSON;
import lombok.NonNull;
import org.biot.rule.engine.domain.rule.RuleId;
import org.biot.rule.engine.domain.rule.model.condition.point.TriggerPoint;
import org.biot.rule.engine.domain.rule.model.condition.point.TriggerPointRepository;
import org.biot.rule.engine.infrastructure.tsdb.RuleEngineTsdbClient;
import org.biot.rule.engine.infrastructure.tsdb.TsdbClientFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 触发点资源库实现
 */
@Repository
public class TriggerPointRepositoryImpl implements TriggerPointRepository {
    @Autowired
    private TsdbClientFactory tsdbClientFactory;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 保存出发点
     *
     * @param point
     */
    @Override
    public void save(@NonNull TriggerPoint point) {
        String tenantId = point.getRuleId().getTenantId();
        RuleEngineTsdbClient client = tsdbClientFactory.of(tenantId);
        client.write(point);
    }

    /**
     * 查找触发点
     *
     * @param ruleId
     * @param sourceId
     * @param fromTime
     * @param endTime
     * @return
     */
    @Override
    public List<TriggerPoint> query(RuleId ruleId, String sourceId, long fromTime, long endTime) {
        String tenantId = ruleId.getTenantId();
        RuleEngineTsdbClient client = tsdbClientFactory.of(tenantId);
        return client.read(ruleId.getUuid(), sourceId, fromTime, endTime);
    }

    /**
     * 具备状态持续类型执行条件的规则，最近一次属性上报，是否被触发
     * 备注：被触发不同于被执行，仅有满足执行条件的触发才会导致执行action
     *
     * @param ruleId
     * @param sourceId
     * @return
     */
    @Override
    public boolean isLastTriggered(String ruleId, String sourceId) {
        String key = ruleId + "#" + sourceId;
        String value = redisTemplate.opsForValue().get(key);
        if (value != null) {
            return JSON.parseObject(value, Boolean.class);
        } else {
            return false;
        }
    }

    /**
     * 标注最新被触发的状态
     *
     * @param ruleId
     * @param sourceId
     * @param triggered
     * @param expireSeconds
     */
    @Override
    public void markTriggeredState(String ruleId, String sourceId, boolean triggered, int expireSeconds) {
        String key = ruleId + "#" + sourceId;
        redisTemplate.opsForValue().set(key, String.valueOf(triggered), expireSeconds, TimeUnit.SECONDS);
    }
}
