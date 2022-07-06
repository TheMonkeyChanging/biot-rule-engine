package org.biot.rule.engine.infrastructure.dal;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "org.biot.rule.engine.infrastructure.dal.mapper")
public class DataSourceConfig {
}
