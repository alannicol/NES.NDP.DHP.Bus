package org.nds.dhp.bus;

import com.microsoft.azure.servicebus.IMessage;
import org.nds.dhp.bus.model.DhpMessageType;
import org.nds.dhp.bus.service.MessageBusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.event.EventListener;

import java.time.Duration;

@SpringBootApplication
@ConfigurationPropertiesScan("org.nds.dhp.bus.config")
public class Application {

	private static String FHIR_MESSAGE_TYPE = "FhirMessageType";
	private static String FHIR_MESSAGE_BLOB_REF = "FhirMessageBlobRef";

	@Autowired
	private MessageBusService messageBusService;

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);

	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() throws Exception {

		messageBusService.clear();
		System.out.println("Cleared Message Bus");

		messageBusService.send(messageBusService.createMessage(DhpMessageType.DVA_NOTIF, "Test Blob Reference"), messageBusService.createQueueClient());
		System.out.println(String.format("Sent message %s", "Test Blob Reference"));

		IMessage message = messageBusService.receive(Duration.ofMillis(500));

		System.out.println(String.format("Received Message Type: %s",message.getProperties().get(FHIR_MESSAGE_TYPE)));
		System.out.println(String.format("Received Message Content: %s", message.getProperties().get(FHIR_MESSAGE_BLOB_REF)));
	}
}
