package org.biot.rule.engine.domain.rule.condition;

import lombok.Getter;
import lombok.Setter;

/**
 * 特定时间内触发次数达到阈值
 */
@Getter
@Setter
public class CumulativeCondition extends Condition {
    /**
     * 阈值
     */
    private int times;

    @Override
    public boolean isContinuous() {
        return false;
    }
}
