package org.biot.rule.engine.domain.rule.model.condition;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.biot.rule.engine.domain.rule.RuleId;

/**
 * 触发器被触发时，增强判断条件，是否执行动作。
 * 举例1：trigger--气温高于33摄氏度；condition--持续时长超过10分钟；action--打开空调 （上报周期必须低于10分钟，才有意义）
 * 举例2：trigger--气温高于33摄氏度；condition--10分钟内，次数大于2；action--打开空调 （上报周期必须低于10分钟，才有意义）
 * 注意上述两者的区别：前者强调持续性，过程中有不满足条件的输入，需清零；后者则是累积
 */
@Getter
@Setter
@SuperBuilder
public abstract class Condition {
    /**
     * 所属规则ID
     */
    private RuleId ruleId;

    /**
     * 业务主键
     */
    private String uuid;

    /**
     * 执行条件类别
     */
    private ConditionType type;

    /**
     * 观察窗口时长，单位秒
     */
    private int period;

    public abstract boolean isContinuous();
}
