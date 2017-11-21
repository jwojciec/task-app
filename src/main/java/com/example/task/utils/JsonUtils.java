package com.example.task.utils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.example.task.model.Task;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    private static final JSONParser parser = new JSONParser();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String readJsonFile(String path) throws IOException, ParseException {
        return parser.parse(new FileReader(path)).toString();
    }

    public static Task jsonToTask(String path) throws IOException {
        return mapper.readValue(new File(path), Task.class);
    }
}
