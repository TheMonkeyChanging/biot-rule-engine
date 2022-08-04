package org.biot.rule.engine.infrastructure.repository.rule.convert;

import org.biot.rule.engine.domain.rule.Rule;
import org.biot.rule.engine.infrastructure.dal.model.rule.RuleDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface RuleAssembleMapper {
    RuleAssembleMapper INSTANCE = Mappers.getMapper(RuleAssembleMapper.class);

    @Mapping(source = "tenantId", target = "ruleId.tenantId")
    @Mapping(source = "uuid", target = "ruleId.uuid")
    Rule toRule(RuleDo ruleDo);

    @Mapping(source = "ruleId.tenantId", target = "tenantId")
    @Mapping(source = "ruleId.uuid", target = "uuid")
    RuleDo toRuleDo(Rule rule);
}
