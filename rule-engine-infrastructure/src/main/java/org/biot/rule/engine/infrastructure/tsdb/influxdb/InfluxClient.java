package org.biot.rule.engine.infrastructure.tsdb.influxdb;

import com.alibaba.fastjson.JSON;
import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import org.biot.rule.engine.domain.rule.model.condition.point.TriggerPoint;
import org.biot.rule.engine.infrastructure.tsdb.RuleEngineTsdbClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class InfluxClient implements RuleEngineTsdbClient {
    private static final Logger log = LoggerFactory.getLogger(InfluxClient.class);

    @Value("${biot.tsdb.influx.host}")
    private String host;

    @Value("${biot.tsdb.influx.token}")
    private String token;

    @Value("${biot.tsdb.influx.org}")
    private String org;

    @Value("${biot.tsdb.influx.trigger_bucket}")
    private String bucket;

    // 内部有连接池机制
    private InfluxDBClient influxDBClient;

    @PostConstruct
    private void init() {
        influxDBClient = InfluxDBClientFactory.create(host, token.toCharArray(),org, bucket);
        log.info("influxDBClient: {}", influxDBClient);
    }

    @Override
    public void write(TriggerPoint point) {
        log.info("Write point to trace line: {}", JSON.toJSONString(point));

        WriteApiBlocking writeApi = influxDBClient.getWriteApiBlocking();
//        String measure = point.getRuleId().getUuid();
//        String source = point.getSourceId();
//        Point p = Point.measurement(measure)
//                .addTag("source", source)
//                .addFields(point.toFieldMap())
//                .time(point.getEventTime(), WritePrecision.S);
//        writeApi.writePoint(p);

        TriggerPointDo pointDo = AssembleMapper.INSTANCE.toTriggerPointDo(point);
        writeApi.writeMeasurement(WritePrecision.MS, pointDo);
    }

    @Override
    public List<TriggerPoint> read(String ruleId, String sourceId, long fromTime, long endTime) {
        String flux = buildFlux(ruleId, sourceId, fromTime, endTime);
        List<TriggerPointDo> pointDoList = influxDBClient.getQueryApi().query(flux, TriggerPointDo.class);
        List<TriggerPoint> rs = new ArrayList<>();
        if (pointDoList != null) {
            pointDoList.forEach(p -> rs.add(AssembleMapper.INSTANCE.toTriggerPoint(p)));
        }
        return rs;
    }

    /**
     * 构建flux语句
     *
     * @param ruleId
     * @param sourceId
     * @param fromTime
     * @param endTime
     * @return
     */
    private String buildFlux(String ruleId, String sourceId, long fromTime, long endTime) {
        String start = Instant.ofEpochMilli(fromTime).toString();
        String stop = Instant.ofEpochMilli(endTime).toString();

        String flux = "from(bucket: \"" + bucket + "\")\n"
                + " |> range(start: " + start + ", stop: " + stop + ")\n"
                + " |> filter(fn: (r) => r._measurement ==\"" + ruleId + "\")\n"
                + " |> filter(fn: (r) => r[\"sourceId\"] ==\"" + sourceId + "\")"
                + " |> pivot(rowKey:[\"_time\"], columnKey: [\"_field\"], valueColumn: \"_value\")";
        return flux;
    }
}
