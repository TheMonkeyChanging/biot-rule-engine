package org.biot.rule.engine.domain.rule.model.trigger;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.biot.rule.engine.domain.rule.RuleId;

/**
 * 触发器基类
 */
@Getter
@Setter
@SuperBuilder
public abstract class Trigger<T> {
    /**
     * 所属规则ID
     */
    private RuleId ruleId;

    /**
     * 触发器ID
     */
    private String uuid;

    /**
     * 触发源类型
     */
    private SourceType sourceType;

    /**
     * 是否被触发
     *
     * @param param
     * @return
     */
    public abstract boolean isTriggered(T param);

    /**
     * 是否匹配该触发器，仅有在匹配的情况下，才进一步判断是否能够被触发
     *
     * @param identify
     * @return
     */
    public abstract boolean isMatch(TriggerCategoryIdentify identify);
}
