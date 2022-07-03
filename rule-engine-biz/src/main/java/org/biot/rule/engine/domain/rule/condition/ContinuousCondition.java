package org.biot.rule.engine.domain.rule.condition;

/**
 * 状态持续一段时间
 */
public class ContinuousCondition extends Condition {
    @Override
    public boolean isContinuous() {
        return true;
    }
}
