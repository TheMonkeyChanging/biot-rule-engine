package org.biot.rule.engine.domain.rule.model;

import lombok.*;
import org.biot.rule.engine.domain.rule.RuleId;
import org.biot.rule.engine.domain.rule.model.action.Action;
import org.biot.rule.engine.domain.rule.model.condition.Condition;
import org.biot.rule.engine.domain.rule.model.trigger.Trigger;
import org.biot.rule.engine.domain.rule.model.trigger.TriggerCategoryIdentify;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Getter
@Setter
public class RuleModel {
    /**
     * 规则ID
     */
    @EqualsAndHashCode.Include
    private final RuleId ruleId;

    /**
     * 每个规则，仅有一条触发器，不支持多触发器（多触发器貌似是不必要的复杂）
     */
    private Trigger trigger;

    /**
     * 触发器被触发时，是否执行action的条件：可空，为空时，触发器被触发即执行action
     * 每个规则，最多仅允许一个执行条件（暂不考虑扩展）
     */
    private Condition condition;

    /**
     * 执行动作
     * 每个规则，有且仅有一个动作（暂不考虑扩展）
     */
    private Action action;

    /**
     * 是否为聚合触发源：聚合触发源，不支持Continuous Condition
     */
    private boolean aggregationTriggerSource;

    /**
     *
     * @param ruleId
     */
    public RuleModel(@NonNull RuleId ruleId) {
        this.ruleId = ruleId;
    }

    /**
     *
     *
     * @param identify
     * @return
     */
    public boolean isMatch(TriggerCategoryIdentify identify) {
        return trigger.isMatch(identify);
    }

    /**
     * 是否有执行条件
     *
     * @return
     */
    public boolean hasCondition() {
        return condition != null;
    }

    /**
     * 是否有持续维持某个状态的执行条件
     *
     * @return
     */
    public boolean hasContinuousCondition() {
        return condition != null ? condition.isContinuous() : false;
    }
}
