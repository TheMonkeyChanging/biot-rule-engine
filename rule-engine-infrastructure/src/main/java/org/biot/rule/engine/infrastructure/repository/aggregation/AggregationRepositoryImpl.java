package org.biot.rule.engine.infrastructure.repository.aggregation;

import org.biot.rule.engine.domain.aggregation.Aggregation;
import org.biot.rule.engine.domain.aggregation.AggregationId;
import org.biot.rule.engine.domain.aggregation.AggregationRepository;
import org.springframework.stereotype.Repository;

/**
 * 聚合触发源资源库实现
 */
@Repository
public class AggregationRepositoryImpl implements AggregationRepository {
    /**
     * 根据ID获取聚合触发源
     *
     * @param id
     * @return
     */
    @Override
    public Aggregation of(AggregationId id) {
        return null;
    }
}
