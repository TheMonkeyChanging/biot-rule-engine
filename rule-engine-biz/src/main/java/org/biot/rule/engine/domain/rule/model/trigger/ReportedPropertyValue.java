package org.biot.rule.engine.domain.rule.model.trigger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportedPropertyValue {
    private String tenantId;
    private String deviceId;
    private String productId;
    private long eventTime;

    /**
     * 属性标识
     */
    private String propertyId;

    /**
     * 属性值
     */
    private Object propertyValue;
}
