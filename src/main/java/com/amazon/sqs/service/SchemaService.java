package com.amazon.sqs.service;

import com.amazon.sqs.model.SQSMetadata;
import com.amazon.sqs.model.SchemaVersion;
import com.amazon.sqs.repository.SchemaRepository;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.schemas.SchemasClient;
import software.amazon.awssdk.services.schemas.model.DescribeSchemaRequest;
import software.amazon.awssdk.services.schemas.model.DescribeSchemaResponse;

import java.util.List;

@Service
public class SchemaService {

    private final SchemasClient schemasClient;
    private final SchemaRepository schemaRepository;

    public SchemaService(SchemaRepository schemaRepository) {
        this.schemasClient = SchemasClient.builder().region(Region.US_EAST_1).build();
        this.schemaRepository = schemaRepository;
    }

    public List<SQSMetadata> getAllSchemaChanges() {

        return schemaRepository.getAll();

    }

    public SchemaVersion getVersionsInSchemaRegistry(int schemaVersion) {

        DescribeSchemaResponse executeNewSchema = execute(schemaVersion);
        String newSchemaBody = executeNewSchema.content();
        String newSchemaVersion = executeNewSchema.schemaVersion();

        DescribeSchemaResponse executeOldSchema = execute(schemaVersion - 1);
        String oldSchemaBody = executeOldSchema.content();
        String oldSchemaVersion = executeOldSchema.schemaVersion();

        return new SchemaVersion(newSchemaBody, newSchemaVersion, oldSchemaBody, oldSchemaVersion);

    }

    private DescribeSchemaResponse execute(int version) {

        String schemaVersion = String.valueOf(version);

        DescribeSchemaRequest describeSchemaRequest;

        if (version == 0) {

            describeSchemaRequest = DescribeSchemaRequest.builder()
                    .schemaName("com.enoque@Test")
                    .registryName("discovered-schemas")
                    .build();

        } else {

            describeSchemaRequest = DescribeSchemaRequest.builder()
                    .schemaName("com.enoque@Test")
                    .registryName("discovered-schemas")
                    .schemaVersion(schemaVersion)
                    .build();
        }

        return schemasClient.describeSchema(describeSchemaRequest);

    }

}
