package org.biot.rule.engine.domain.rule.trigger;

/**
 * （单）设备触发
 */
public abstract class DeviceTrigger<T> extends Trigger<T> {
    /**
     * 所有设备标识，即针对特定产品的所有设备进行触发监控
     */
    public static final String ALL_DEVICES = "ALL_DEVICES__";

    /**
     * 设备所属产品
     */
    private String productId;

    /**
     * 设备ID，触发设备为全部（任一）时，取值ALL_DEVICES
     */
    private String deviceId;
}
