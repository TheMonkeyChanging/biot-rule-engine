package org.biot.rule.engine.domain.aggregation;

import org.biot.EntityId;

/**
 * 聚合触发源ID
 */
public class AggregationId extends EntityId<String> {
    public AggregationId(String tenantId, String uuid) {
        super(tenantId, uuid);
    }
}
