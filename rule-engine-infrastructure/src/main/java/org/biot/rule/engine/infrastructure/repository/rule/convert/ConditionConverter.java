package org.biot.rule.engine.infrastructure.repository.rule.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.NonNull;
import org.biot.rule.engine.domain.rule.RuleId;
import org.biot.rule.engine.domain.rule.model.condition.Condition;
import org.biot.rule.engine.domain.rule.model.condition.ConditionType;
import org.biot.rule.engine.domain.rule.model.condition.ContinuousCondition;
import org.biot.rule.engine.domain.rule.model.condition.CumulativeCondition;
import org.biot.rule.engine.infrastructure.dal.model.rule.ConditionDo;

/**
 * condition领域模型与数据模型转换器
 */
public class ConditionConverter {
    private static final String TIMES_STR = "times";

    /**
     * @param conditionDo
     * @return
     */
    public static Condition convert(@NonNull ConditionDo conditionDo) {
        String type = conditionDo.getType();
        RuleId ruleId = new RuleId(conditionDo.getTenantId(), conditionDo.getRuleId());
        if (ConditionType.CONTINUOUS.toString().equals(type)) {
            return ContinuousCondition.builder().uuid(conditionDo.getUuid()).ruleId(ruleId).type(ConditionType.CONTINUOUS)
                    .period(conditionDo.getPeriod()).build();
        } else if (ConditionType.CUMULATIVE.toString().equals(type)) {
            JSONObject conditionRule = JSON.parseObject(conditionDo.getConditionRule());
            int times = Integer.parseInt(conditionRule.getString(TIMES_STR));
            return CumulativeCondition.builder().uuid(conditionDo.getUuid()).ruleId(ruleId).type(ConditionType.CUMULATIVE)
                    .period(conditionDo.getPeriod()).times(times).build();
        }

        return null;
    }
}
