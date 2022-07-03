package org.biot.rule.engine.domain.rule.condition.point;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.biot.rule.engine.domain.rule.RuleId;

import java.util.HashMap;
import java.util.Map;

/**
 * 触发执行点
 */
@Getter
@Setter
@Builder
public class TriggerPoint {
    public static final String TRIGGERED_KEY = "triggered";
    public static final String DESC_KEY = "desc";

    /**
     * 规则id
     * 默认使用时序数据库保存，对应measure
     */
    private RuleId ruleId;

    /**
     * 触发源ID：deviceId，聚合触发源ID
     * 默认使用时序数据库保存，对应tag
     */
    private String sourceId;

    /**
     * 事件时间
     * 默认使用时序数据库保存，对应时间戳
     */
    private long eventTime;

    /**
     * 是否被触发
     */
    private boolean triggered;

    /**
     * 描述
     */
    private String desc;

    /**
     * 属性值转化为map：trigger point（默认）使用时序数据库保存，该map对应fields
     *
     * @return
     */
    public Map<String, Object> toFieldMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(TRIGGERED_KEY, triggered);
        map.put(DESC_KEY, desc);
        return map;
    }
}
