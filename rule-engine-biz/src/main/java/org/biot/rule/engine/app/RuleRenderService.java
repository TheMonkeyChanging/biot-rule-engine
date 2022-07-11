package org.biot.rule.engine.app;

import org.biot.things.core.client.device.DeviceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RuleRenderService {
    @Autowired
    private DeviceClient deviceClient;

    @PostConstruct
    void confirm() {
        System.out.println("DeviceClient: " + deviceClient);
    }
}
