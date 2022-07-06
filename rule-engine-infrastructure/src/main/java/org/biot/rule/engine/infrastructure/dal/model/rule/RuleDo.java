package org.biot.rule.engine.infrastructure.dal.model.rule;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RuleDo {
    private Long id;
    private String tenantId;
    private String uuid;
    private String name;
    private String desc;
    private Boolean enabled;
    private Date gmtCreate;
    private Date gmtModified;
}
