package org.biot.rule.engine.domain.aggregation;

/**
 * 聚合触发源仓库
 */
public interface AggregationRepository {
    /**
     * 根据ID获取聚合触发源
     *
     * @param id
     * @return
     */
    Aggregation of(AggregationId id);
}
