package org.biot.rule.engine.domain.rule.trigger;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TriggerSourceIdentify<T> {
    /**
     * 触发源类型
     */
    private TriggerSourceType type;

    /**
     * 触发源标识
     */
    private T identify;
}
