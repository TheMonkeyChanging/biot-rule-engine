package org.biot.rule.engine.domain.rule.condition.point;

import lombok.NonNull;
import org.biot.rule.engine.domain.rule.RuleId;

import java.util.List;

/**
 * 触发的仓库
 */
public interface TriggerPointRepository {
    /**
     * 保存出发点
     *
     * @param point
     */
    void save(@NonNull TriggerPoint point);

    /**
     * 查找触发点
     *
     * @param ruleId    规则ID
     * @param sourceId  触发源ID
     * @param fromTime  开始时间，Epoch MS
     * @param endTime   结束时间，Epoch MS
     * @return
     */
    List<TriggerPoint> query(RuleId ruleId, String sourceId, long fromTime, long endTime);

    /**
     * 具备状态持续类型执行条件的规则，最近一次属性上报，是否被触发
     * 备注：被触发不同于被执行，仅有满足执行条件的触发才会导致执行action
     *
     * @param ruleId
     * @param sourceId
     * @return
     */
    boolean isLastTriggered(String ruleId, String sourceId);

    /**
     * 标注最新被触发的状态
     *
     * @param ruleId
     * @param sourceId
     * @param triggered
     */
    void markTriggeredState(String ruleId, String sourceId, boolean triggered);
}
