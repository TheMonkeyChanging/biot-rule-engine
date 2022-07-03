package org.biot.rule.engine.infrastructure.tsdb;

import org.biot.rule.engine.domain.rule.condition.point.TriggerPoint;

import java.util.List;

public interface RuleEngineTsdbClient {
    /**
     * 写入触发点
     *
     * @param point
     */
    void write(TriggerPoint point);

    /**
     * 读取触发点列表
     *
     * @param ruleId    规则ID
     * @param sourceId  触发源ID
     * @param fromTime  开始时间，Epoch MS
     * @param endTime   结束时间，Epoch MS
     * @return
     */
    List<TriggerPoint> read(String ruleId, String sourceId, long fromTime, long endTime);
}
