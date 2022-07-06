package org.biot.rule.engine.domain.rule.model.trigger;

/**
 * 触发源类别
 */
public enum SourceType {
    /**
     * 单源（设备）触发
     */
    SINGLE,
    /**
     * 多源（设备）触发
     */
    AGGREGATION;
}
