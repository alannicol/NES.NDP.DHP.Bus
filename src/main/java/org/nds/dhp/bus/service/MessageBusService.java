package org.nds.dhp.bus.service;

import com.microsoft.azure.servicebus.*;
import com.microsoft.azure.servicebus.primitives.ConnectionStringBuilder;
import com.microsoft.azure.servicebus.primitives.ServiceBusException;
import org.nds.dhp.bus.config.AzureProperties;
import org.nds.dhp.bus.model.DhpMessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
public class MessageBusService implements AutoCloseable {

    private static final String DHP_SERVICE_BUS = "dhp-service-bus-queue-test";

    private static String FHIR_MESSAGE_TYPE = "FhirMessageType";
    private static String FHIR_MESSAGE_BLOB_REF = "FhirMessageBlobRef";

    private final String connectionString;
    private final IMessageReceiver messageReceiver;

    @Autowired
    public MessageBusService(AzureProperties azureProperties) throws ServiceBusException, InterruptedException {
        this.connectionString = azureProperties.getServiceBus().getConnectionString();

        this.messageReceiver = ClientFactory
                .createMessageReceiverFromConnectionStringBuilder(
                        new ConnectionStringBuilder(connectionString, DHP_SERVICE_BUS),
                        ReceiveMode.RECEIVEANDDELETE);
    }

    public void send(Message message, QueueClient queueClient) {

        queueClient.sendAsync(message)
                .thenRunAsync(queueClient::closeAsync).handle((result, exception) -> {

            return result;
        });
    }

    public IMessage receive(Duration waitTime) throws ServiceBusException, InterruptedException {
        return messageReceiver.receive(waitTime);
    }

    public void clear() throws ServiceBusException, InterruptedException {
        IMessage message;
        do {
            message = messageReceiver.receive(Duration.ofMillis(500));
        } while (message != null);
    }

    public QueueClient createQueueClient() {
        QueueClient queueClient = null;

        try {

            queueClient = new QueueClient(new ConnectionStringBuilder(connectionString, DHP_SERVICE_BUS), ReceiveMode.PEEKLOCK);
        } catch (Exception exception) {
            System.out.println("Unable to create queue client");
        }

        return queueClient;
    }

    public Message createMessage(DhpMessageType dhpMessageType, String reference) {
        Message message;

        message = new Message();
        message.getProperties().put(FHIR_MESSAGE_TYPE, dhpMessageType.getValue());
        message.getProperties().put(FHIR_MESSAGE_BLOB_REF, reference);

        return message;
    }

    public void close() throws ServiceBusException {
        messageReceiver.close();
    }
}
