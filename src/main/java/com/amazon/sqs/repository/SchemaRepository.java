package com.amazon.sqs.repository;

import com.amazon.sqs.model.SQSMetadata;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

@Repository
public class SchemaRepository {

    public void createSchema(SQSMetadata metadata) {

        String SQL = "INSERT INTO SCHEMA_VERSION(ID, VERSION, DETAIL_TYPE, SOURCE, ACCOUNT, CREATE_TIME, REGION, DETAIL) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {

            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

            System.out.println("success in database connection");

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            preparedStatement.setString(1, metadata.getId());
            preparedStatement.setString(2, metadata.getVersion());
            preparedStatement.setString(3, metadata.getDetailType());
            preparedStatement.setString(4, metadata.getSource());
            preparedStatement.setString(5, metadata.getAccount());
            preparedStatement.setString(6, metadata.getTime());
            preparedStatement.setString(7, metadata.getRegion());
            preparedStatement.setString(8, metadata.getDetail().toString());
            preparedStatement.execute();

            System.out.println("success in insert sqs metadata");

            connection.close();

        } catch (Exception e) {

            System.out.println("fail in database connection");

        }

    }

    public List<SQSMetadata> getAll() {

        String SQL = "SELECT * FROM SCHEMA_VERSION";

        try {

            Connection connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "sa");

            System.out.println("success in database connection");

            PreparedStatement preparedStatement = connection.prepareStatement(SQL);

            ResultSet resultSet = preparedStatement.executeQuery();

            List<SQSMetadata> schemas = new ArrayList<>();

            while (resultSet.next()) {

                String version = resultSet.getString("VERSION");
                String id = resultSet.getString("ID");
                String detailType = resultSet.getString("DETAIL_TYPE");
                String source = resultSet.getString("SOURCE");
                String account = resultSet.getString("ACCOUNT");
                String time = resultSet.getString("CREATE_TIME");
                String region = resultSet.getString("REGION");
                Object detail = resultSet.getString("DETAIL");

                SQSMetadata metadata = new SQSMetadata(version, id, detailType, source, account, time, region, detail);

                schemas.add(metadata);

            }

            System.out.println("success in select * car");

            connection.close();

            return schemas;

        } catch (Exception e) {

            System.out.println("fail in database connection");

            return Collections.emptyList();

        }

    }

}
