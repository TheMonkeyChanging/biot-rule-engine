package org.biot.rule.engine.domain.rule.trigger;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportedPropertyValue {
    private String tenantId;
    private String deviceId;
    private String productId;
    private long eventTime;

    private String propertyId;
    private Object propertyValue;

}
