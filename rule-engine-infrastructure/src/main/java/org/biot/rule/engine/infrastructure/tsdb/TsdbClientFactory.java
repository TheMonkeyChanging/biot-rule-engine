package org.biot.rule.engine.infrastructure.tsdb;

import org.biot.rule.engine.infrastructure.tsdb.influxdb.InfluxClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TsdbClientFactory {
    @Autowired
    private InfluxClient influxClient;

    public RuleEngineTsdbClient of(String tenantId) {
        return influxClient;
    }
}
