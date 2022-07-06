package org.biot.rule.engine.infrastructure.tsdb.influxdb;

import com.influxdb.annotations.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class TriggerPointDo {
    @Column(measurement = true)
    private String rule;

    @Column(tag = true)
    private String sourceId;

    @Column(timestamp = true)
    private Instant time;

    @Column
    private Boolean triggered;

    @Column
    private String desc;
}
