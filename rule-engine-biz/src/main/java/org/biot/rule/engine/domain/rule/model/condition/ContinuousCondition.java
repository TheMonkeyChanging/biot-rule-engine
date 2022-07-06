package org.biot.rule.engine.domain.rule.model.condition;

import lombok.experimental.SuperBuilder;

/**
 * 状态持续一段时间
 */
@SuperBuilder
public class ContinuousCondition extends Condition {
    @Override
    public boolean isContinuous() {
        return true;
    }
}
