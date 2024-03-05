package org.example.satdwn.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.satdwn.model.Response;
import org.example.satdwn.model.SatClass;
import org.example.satdwn.service.SatService;
import org.example.satdwn.util.UploadFileToS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@ResponseBody
@RequestMapping("/")
public class SatController {

    @Autowired
    private SatService satService;

    Logger LOGGER = LogManager.getLogger(SatController.class);

    public SatController() {
    }

    @PostMapping(value = "sat",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> sat(@RequestBody SatClass userSat) throws IOException {
        satService.requestSat(userSat);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @PostMapping(value = "upload",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> upload(@RequestBody String fileName) {
        UploadFileToS3.upload(fileName);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @GetMapping(value = "getFiles/{fileName}")
    public ResponseEntity<Map<String, List<Response>>> getFile(@PathVariable(name = "fileName") String fileName) {
        Map<String, List<Response>> responses = satService.getFiles(fileName);
        if (!responses.isEmpty()) {
            return new ResponseEntity<>(responses, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
