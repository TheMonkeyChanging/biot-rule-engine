package org.biot.rule.engine.domain.rule.trigger;

import com.googlecode.aviator.AviatorEvaluator;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

import static org.biot.rule.engine.domain.rule.trigger.TriggerSourceType.DEVICE_PROPERTY;

/**
 * 属性值上报触发器，通常为属性值与特定值的比较关系
 */
@Getter
@Setter
public class PropertyValueTrigger extends DeviceTrigger<ReportedPropertyValue> {
    /**
     * 属性标识
     */
    private String propertyId;

    /**
     *
     */
    private String expression;

    /**
     * 判断是否满足触发的必要条件
     *
     * @return
     */
    @Override
    public boolean isTriggered(@NonNull ReportedPropertyValue value) {
        if (propertyId.equals(value.getPropertyId())) {
            Map map = new HashMap<>();
            map.put(propertyId, value.getPropertyValue());
            return (Boolean) AviatorEvaluator.execute(expression, map);
        }

        return false;
    }

    /**
     * 是否匹配该触发器，仅有在匹配的情况下，才进一步判断是否能够触发
     *
     * @param identify
     * @return
     */
    @Override
    public boolean isMatch(@NonNull TriggerSourceIdentify identify) {
        return DEVICE_PROPERTY==identify.getType() ? propertyId.equals(identify.getIdentify()) : false;
    }
}
