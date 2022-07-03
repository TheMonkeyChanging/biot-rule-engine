package org.biot.rule.engine.domain.rule.trigger;

/**
 * 触发器基类
 */
public abstract class Trigger<T> {
    private TriggerId id;

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
    public abstract boolean isMatch(TriggerSourceIdentify identify);
}
