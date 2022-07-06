package org.biot.rule.engine.domain.aggregation;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 聚合触发源
 */
@Getter
@Setter
public class Aggregation {
    private AggregationId aggregationId;

    private List<SingleDevice> devices;
}
