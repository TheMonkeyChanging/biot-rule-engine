package org.biot.rule.engine.infrastructure.dal.mapper.rule;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.biot.rule.engine.infrastructure.dal.model.rule.RuleDo;

import java.util.List;

@Mapper
public interface RuleMapper {
    /**
     * 插入规则
     *
     * @param ruleDo
     */
    void insert(RuleDo ruleDo);

    /**
     * 查询租户下的所有规则
     *
     * @param tenantId
     * @return
     */
    List<RuleDo> selectAllRulesOfTenant(@Param("tenantId") String tenantId);

    /**
     * 查询租户下所有启用的规则
     *
     * @param tenantId
     * @return
     */
    List<RuleDo> selectEnabledRulesOfTenant(@Param("tenantId") String tenantId);
}
