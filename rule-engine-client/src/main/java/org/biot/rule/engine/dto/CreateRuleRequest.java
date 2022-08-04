package org.biot.rule.engine.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 创建规则的请求参数
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRuleRequest implements Serializable {
    @NotBlank(message = "名称不能为空")
    @Size(max = 64, message = "名称过长")
    private String name;

    @Size(max = 128, message = "名称过长")
    private String desc;
}
