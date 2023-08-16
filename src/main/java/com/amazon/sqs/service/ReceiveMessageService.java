package com.amazon.sqs.service;

import com.amazon.sqs.model.SQSMetadata;
import com.amazon.sqs.repository.SchemaRepository;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.schemas.SchemasClient;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;

import java.util.List;

@Service
public class ReceiveMessageService {

    private SchemaRepository schemaRepository;

    private static final SqsClient SQS_CLIENT = SqsClient.builder().region(Region.US_EAST_1).build();

    private static final SchemasClient schemasClient = SchemasClient.builder().region(Region.US_EAST_1).build();
    private static final Gson gson = new Gson();

    public ReceiveMessageService(SchemaRepository schemaRepository) {
        this.schemaRepository = schemaRepository;
    }

    //    @Scheduled(fixedRate = 50000)
    private void receive() {

        ReceiveMessageRequest receiveMessageRequest = ReceiveMessageRequest.builder()
                .queueUrl("https://sqs.us-east-1.amazonaws.com/781561213133/test-sqs")
                .build();

        List<Message> receivedMessages = SQS_CLIENT.receiveMessage(receiveMessageRequest).messages();

        for (Message receivedMessage : receivedMessages) {

            SQSMetadata metadata = gson.fromJson(receivedMessage.body(), SQSMetadata.class);

            schemaRepository.createSchema(metadata);

        }

    }

}
