package org.example.satdwn.controller;

import org.example.satdwn.model.Response;
import org.example.satdwn.model.SatClass;
import org.example.satdwn.service.SatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@ResponseBody
@RequestMapping("/")
public class SatController {

    @Autowired
    private SatService satService;

    public SatController() {
    }

    @PostMapping(value = "sat",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ArrayList<String>> sat(@RequestBody SatClass userSat) throws ParseException, IOException {
        ArrayList<String> list = satService.requestSat(userSat);
         if (list != null) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(list, HttpStatus.NO_CONTENT);
        }
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
