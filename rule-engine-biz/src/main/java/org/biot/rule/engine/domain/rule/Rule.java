package org.biot.rule.engine.domain.rule;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 规则引擎实体定义
 */
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Rule {
    @EqualsAndHashCode.Include
    private RuleId ruleId;
    private String name;
    private String desc;
    private Boolean enabled;
}
