package com.amazon.sqs.controller;

import com.amazon.sqs.model.SQSMetadata;
import com.amazon.sqs.service.SchemaService;
import com.amazon.sqs.service.ReceiveMessageService;
import com.amazon.sqs.model.SchemaVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MyViewController {

    private final ReceiveMessageService receiveMessageService;
    private final SchemaService schemaService;

    @Autowired
    public MyViewController(SchemaService schemaService, ReceiveMessageService receiveMessageService) {

        this.receiveMessageService = receiveMessageService;
        this.schemaService = schemaService;

    }

    @GetMapping("/")
    public String schemas(Model model) {

        List<SQSMetadata> schemas = schemaService.getAllSchemaChanges();

        model.addAttribute("schemas", schemas);

        return ("schemas");

    }
    @GetMapping("/schema-detail")
    public String index(@RequestParam int version, Model model) {

        schemaService.getAllSchemaChanges();

        SchemaVersion schemaVersion = schemaService.getVersionsInSchemaRegistry(version);

        model.addAttribute("schemaVersion", schemaVersion);

        return ("jquery");

    }

    @GetMapping("/jquery")
    public String jquery(Model model) {

        return ("jquery");

    }

}
