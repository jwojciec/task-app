package com.example.task.controllers;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.example.task.utils.JsonUtils.jsonToTask;
import static com.example.task.utils.JsonUtils.readJsonFile;

import java.io.IOException;

import jersey.repackaged.com.google.common.collect.ImmutableList;

import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.task.model.Task;
import com.example.task.repositories.TaskRepository;

public class TaskControllerTest {
    private static final String ALL_TASKS_PATH = "src/test/resources/task/getAllTasks.json";
    private static final String SINGLE_TASK_PATH = "src/test/resources/task/getSingleTask.json";
    private static final String NEW_TASK_PATH = "src/test/resources/task/newTask.json";
    private static final Long TASK_ID_1 = 1L;
    private static final Long TASK_ID_2 = 2L;
    private static final Long UNAVAILABLE_ID = 10L;
    private static final Task TASK_1 = new Task(TASK_ID_1, "zmywanie");
    private static final Task TASK_2 = new Task(TASK_ID_2, "pranie");

    private MockMvc mockMvc;

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskRepository taskRepository;

    @Before
    public void init() throws IOException, ParseException {
        initMocks(this);
        mockMvc = MockMvcBuilders
            .standaloneSetup(taskController)
            .build();

        when(taskRepository.findAll()).thenReturn(ImmutableList.of(TASK_1, TASK_2));
        when(taskRepository.findOne(TASK_ID_1)).thenReturn(TASK_1);
        doThrow(new EmptyResultDataAccessException(1)).when(taskRepository).delete(UNAVAILABLE_ID);
    }

    @Test
    public void When_GettingAllTasks_Expect_CorrectResponse() throws Exception {
        mockMvc.perform(get("/tasks"))
            .andExpect(status().isOk())
            .andExpect(content().json(readJsonFile(ALL_TASKS_PATH)));
    }

    @Test
    public void When_GettingTaskById_Expect_CorrectResponse() throws Exception {
        mockMvc.perform(get("/tasks/" + TASK_ID_1))
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
    }

    @Test
    public void When_DeletingTask_Expect_CorrectResponse() throws Exception {
        mockMvc.perform(delete("/tasks/" + TASK_ID_1))
            .andExpect(status().isGone());

        taskRepository.save(jsonToTask(SINGLE_TASK_PATH));
    }

    @Test
    public void When_DeletingByWrongTaskId_Expect_StatusNotFound() throws Exception {
        mockMvc.perform(delete("/tasks/" + UNAVAILABLE_ID))
            .andExpect(status().isNotFound());
    }
}
