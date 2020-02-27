package org.nds.dhp.bus;

import org.nds.dhp.bus.model.DhpMessageType;
import org.nds.dhp.bus.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@ConfigurationPropertiesScan("org.nds.dhp.bus.config")
public class Application {

	@Autowired
	private ApplicationService applicationService;

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void applicationReady() throws Exception {
		applicationService.testMessageBus();
	}
}
