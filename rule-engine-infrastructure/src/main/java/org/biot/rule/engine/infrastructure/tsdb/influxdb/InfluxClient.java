package org.biot.rule.engine.infrastructure.tsdb.influxdb;

import com.influxdb.client.InfluxDBClient;
import com.influxdb.client.InfluxDBClientFactory;
import com.influxdb.client.WriteApiBlocking;
import com.influxdb.client.domain.WritePrecision;
import org.biot.rule.engine.domain.rule.condition.point.TriggerPoint;
import org.biot.rule.engine.infrastructure.tsdb.RuleEngineTsdbClient;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class InfluxClient implements RuleEngineTsdbClient {
    private static final char[] token = "GvWxmeCG1ymm8hNUtdosAgsefwfFnIT0xNOwSN_4-QOCIxsBzmE_aYmD3iSKIVLsQp5OM6A_tfttOxqS2vG8yg==".toCharArray();
    private static final String org = "abama";
    private static final String bucket = "rule_trigger_trace";

    /**
     * 东八区与SYSTEM UTC相差的毫秒数
     */
    private static final long MILLS_OFFSET_E8 = TimeUnit.HOURS.toMillis(8);

    // 内部有连接池机制
    private final InfluxDBClient influxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token, org, bucket);

    @Override
    public void write(TriggerPoint point) {
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
        String start = Instant.ofEpochMilli(fromTime).plusMillis(MILLS_OFFSET_E8).toString();
        String stop = Instant.ofEpochMilli(endTime).plusMillis(MILLS_OFFSET_E8).toString();

        String flux = "from(bucket: \"" + bucket + "\")\n"
                + " |> range(start: " + start + ", stop: " + stop + ")\n"
                + " |> filter(fn: (r) => r._measurement ==\"" + ruleId + "\")\n"
                + " |>  filter(fn: (r) => r[\"sourceId\"] ==\"" + sourceId + "\")";
        return flux;
    }
}
