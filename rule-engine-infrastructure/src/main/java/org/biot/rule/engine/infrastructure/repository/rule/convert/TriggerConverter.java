package org.biot.rule.engine.infrastructure.repository.rule.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.biot.rule.engine.domain.rule.RuleId;
import org.biot.rule.engine.domain.rule.model.trigger.*;
import org.biot.rule.engine.infrastructure.dal.model.rule.RuleTriggerDo;

/**
 * trigger领域模型与数据模型转换器
 */
public class TriggerConverter {
    private static final String PRODUCT_ID_STR = "productId";
    private static final String DEVICE_ID_STR = "deviceId";
    private static final String CATEGORY_STR = "category";
    private static final String PROPERTY_ID_STR = "propertyId";
    private static final String EXPRESSION_STR = "expression";

    public static Trigger convert(RuleTriggerDo triggerDo) {
        String sourceType = triggerDo.getSourceType();
        if (sourceType.equalsIgnoreCase(SourceType.SINGLE.toString())) {
            JSONObject source = JSON.parseObject(triggerDo.getSource());
            String productId = source.getString(PRODUCT_ID_STR);
            String deviceId = source.getString(DEVICE_ID_STR);

            JSONObject triggerRule = JSON.parseObject(triggerDo.getTriggerRule());
            String category = triggerRule.getString(CATEGORY_STR);
            if (category.equalsIgnoreCase(TriggerCategory.DEVICE_PROPERTY.toString())) {
                String propertyId = triggerRule.getString(PROPERTY_ID_STR);
                String expression = triggerRule.getString(EXPRESSION_STR);
                return PropertyValueTrigger.builder().productId(productId).deviceId(deviceId).propertyId(propertyId)
                        .expression(expression).ruleId(new RuleId(triggerDo.getTenantId(), triggerDo.getRuleId()))
                        .sourceType(SourceType.SINGLE).uuid(triggerDo.getUuid()).build();
            }
        } else {
            // todo
        }

        return null;
    }
}
