package org.biot.rule.engine.infrastructure.tsdb.influxdb;

import org.biot.rule.engine.domain.rule.model.condition.point.TriggerPoint;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.Instant;

@Mapper
interface AssembleMapper {
    AssembleMapper INSTANCE = Mappers.getMapper(AssembleMapper.class);


    @Mapping(source = "rule", target = "ruleId.uuid")
    @Mapping(source = "time", target = "eventTime")
    TriggerPoint toTriggerPoint(TriggerPointDo triggerPointDo);

    @Mapping(source = "ruleId.uuid", target = "rule")
    @Mapping(source = "eventTime", target = "time")
    TriggerPointDo toTriggerPointDo(TriggerPoint triggerPoint);

    /**
     * Instant转换为Epoch毫秒计数
     *
     * @param value
     * @return
     */
    default long map(Instant value) {
        return value.toEpochMilli();
    }

    /**
     * EpochMilli转换为Instant
     *
     * @param value
     * @return
     */
    default Instant map(long value) {
        return Instant.ofEpochMilli(value);
    }
}
