package org.biot.rule.engine.domain.rule.model.condition;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * 特定时间内触发次数达到阈值
 */
@Getter
@Setter
@SuperBuilder
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
