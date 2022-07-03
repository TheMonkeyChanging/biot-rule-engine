package org.biot.rule.engine.domain.rule;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.biot.EntityId;

@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RuleId extends EntityId<String> {
    public RuleId(String tenantId, String uuid) {
        super(tenantId, uuid);
    }
}
