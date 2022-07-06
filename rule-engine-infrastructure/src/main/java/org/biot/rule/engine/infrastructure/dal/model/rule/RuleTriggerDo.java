package org.biot.rule.engine.infrastructure.dal.model.rule;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RuleTriggerDo {
    /**
     * 自增ID
     */
    private Long id;

    /**
     * 租户ID
     */
    private String tenantId;

    /**
     * 业务主键
     */
    private String uuid;

    /**
     * 所属规则ID
     */
    private String ruleId;

    /**
     * 触发源类型
     * @see org.biot.rule.engine.domain.rule.model.trigger.SourceType
     */
    private String sourceType;

    /**
     * 触发源定义，json格式
     */
    private String source;

    /**
     * 触发规则定义，json格式
     */
    private String triggerRule;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 修改时间
     */
    private Date gmtModified;
}
