package org.biot.rule.engine.infrastructure.dal.mapper.rule;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.biot.rule.engine.infrastructure.dal.model.rule.ConditionDo;
import org.biot.rule.engine.infrastructure.dal.model.rule.RuleTriggerDo;

import java.util.List;

@Mapper
public interface RuleModelMapper {
    /**
     * 根据规则ID查询触发器
     *
     * @param ruleId
     * @return
     */
    RuleTriggerDo selectTriggerByRuleId(@Param("ruleId") String ruleId);

    /**
     * 查询租户下的所有触发器，用于构建租户下的所有触发模型
     *
     * @param tenantId
     * @return
     */
    List<RuleTriggerDo> selectTriggersOfTenant(@Param("tenantId") String tenantId);

    /**
     * 根据规则ID查询执行条件
     *
     * @param ruleId
     * @return
     */
    ConditionDo selectConditionByRuleId(@Param("ruleId") String ruleId);

    /**
     * 查询租户下的所有执行条件，用于构建租户下的所有触发模型
     *
     * @param tenantId
     * @return
     */
    List<ConditionDo> selectConditionsOfTenant(@Param("tenantId") String tenantId);
}
