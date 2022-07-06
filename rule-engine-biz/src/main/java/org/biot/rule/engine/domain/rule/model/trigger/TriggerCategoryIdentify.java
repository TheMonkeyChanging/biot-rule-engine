package org.biot.rule.engine.domain.rule.model.trigger;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TriggerCategoryIdentify<T> {
    /**
     * 触发源类型
     */
    private TriggerCategory type;

    /**
     * 触发源标识
     */
    private T identify;
}
