package org.nds.dhp.bus.service;

import com.microsoft.azure.servicebus.IMessage;
import org.nds.dhp.bus.model.DhpMessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class ApplicationService {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    private static String FHIR_MESSAGE_TYPE = "FhirMessageType";
    private static String FHIR_MESSAGE_BLOB_REF = "FhirMessageBlobRef";

    @Autowired
    private MessageBusService messageBusService;

    public void testMessageBus() throws Exception {
        logger.info("*****Test Message Bus*****");
        logger.info("");

        messageBusService.clear();

        testMessageBus(DhpMessageType.DVA_NOTIF_RESPONSE);
        testMessageBus(DhpMessageType.COPD_REPORT);
        testMessageBus(DhpMessageType.MCO_PROM_OVRN_CNCR);
        testMessageBus(DhpMessageType.TRAUMA_INCIDENT_REPORT);

        messageBusService.close();

        logger.info("Closed Message Bus");
    }

    private void testMessageBus(DhpMessageType dhpMessageType) throws Exception {
        logger.info("Test {}", dhpMessageType.getValue());
        logger.info("");

        logger.info("Cleared Message Bus");

        String blobReference = BlobService.generateBlobReference(dhpMessageType);
        messageBusService.send(messageBusService.createMessage(dhpMessageType, blobReference), messageBusService.createQueueClient());
        logger.info("Sent Message '{}'", blobReference);

        IMessage message = messageBusService.receive(Duration.ofMillis(500));

        logger.info("Received Message Type: {}",message.getProperties().get(FHIR_MESSAGE_TYPE));
        logger.info("Received Message Content: {}", message.getProperties().get(FHIR_MESSAGE_BLOB_REF));

        logger.info("");
    }
}
