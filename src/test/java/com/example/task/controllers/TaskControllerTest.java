package com.example.task.controllers;

import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.example.task.utils.JsonUtils.jsonToTask;
import static com.example.task.utils.JsonUtils.readJsonFile;
import static com.example.task.utils.UuidUtils.generateUUID;

import java.io.IOException;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.task.datasources.SimpleDataSource;

public class TaskControllerTest {
    private static final String ALL_TASKS_PATH = "src/test/resources/task/getAllTasks.json";
    private static final String SINGLE_TASK_PATH = "src/test/resources/task/getSingleTask.json";
    private static final String NEW_TASK_PATH = "src/test/resources/task/newTask.json";
    private static final String TASK_ID = "cd502cc3-99f8-4921-a66f-61f424937ebd";
    private static final String NEW_TASK_ID = "ad602cc1-99f8-4922-a66f-61f424937ebc";

    private MockMvc mockMvc;

    @InjectMocks
    private TaskController taskController;

    @Spy
    private SimpleDataSource simpleDataSource;

    @Before
    public void init() throws IOException, ParseException {
        initMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(taskController)
            .build();
    }

    @Test
    public void When_GettingAllTasks_Expect_CorrectResponse() throws Exception {
        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(content().json(readJsonFile(ALL_TASKS_PATH)));
    }

    @Test
    public void When_GettingTaskById_Expect_CorrectResponse() throws Exception {
        mockMvc.perform(get("/tasks/" + TASK_ID))
            .andExpect(status().isOk())
            .andExpect(content().json(readJsonFile(SINGLE_TASK_PATH)));
    }

    @Test
    public void When_PostingTask_Expect_CorrectResponse() throws Exception {
        mockMvc.perform(post("/tasks/")
            .content(readJsonFile(NEW_TASK_PATH))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(readJsonFile(NEW_TASK_PATH)));

        simpleDataSource.deleteTask(NEW_TASK_ID);
    }

    @Test
    public void When_PostingExistingTask_Expect_BadRequest() throws Exception {
        mockMvc.perform(post("/tasks/")
            .content(readJsonFile(SINGLE_TASK_PATH))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());
    }

    @Test
    public void When_UpdatingTask_Expect_CorrectResponse() throws Exception {
        mockMvc.perform(put("/tasks/" + TASK_ID)
            .content(readJsonFile(SINGLE_TASK_PATH))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(content().json(readJsonFile(SINGLE_TASK_PATH)));
    }

    @Test
    public void When_UpdatingByWrongTaskId_Expect_StatusNotFound() throws Exception {
        mockMvc.perform(put("/tasks/" + generateUUID())
            .content(readJsonFile(SINGLE_TASK_PATH))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());
    }

    @Test
    public void When_DeletingTask_Expect_CorrectResponse() throws Exception {
        mockMvc.perform(delete("/tasks/" + TASK_ID))
            .andExpect(status().isGone());

        simpleDataSource.insertTask(jsonToTask(SINGLE_TASK_PATH));
    }

    @Test
    public void When_DeletingByWrongTaskId_Expect_StatusNotFound() throws Exception {
        mockMvc.perform(delete("/tasks/" + generateUUID()))
            .andExpect(status().isNotFound());
    }
}
