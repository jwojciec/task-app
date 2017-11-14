package com.example.task.datasources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;

import static com.example.task.utils.UuidUtils.generateUUID;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;

import com.example.task.model.Task;

public class SimpleDataSourceTest {
    private static final String TASK_NAME = "test_task";
    private static final String TASK_TEXT = "test_text";
    private static final String TASK_NAME_UPDATED = "updated_task";
    private static final String TASK_TEXT_UPDATED = "updated_text";

    @InjectMocks
    private SimpleDataSource simpleDataSource;

    private String id;

    @Before
    public void setUp() throws Exception {
        initMocks(this);
        id = simpleDataSource.insertTask(new Task(generateUUID(), TASK_NAME, TASK_TEXT)).getId();
        assertThat(simpleDataSource.getAllTasks().size(), is(3));
    }

    @After
    public void tearDown() {
        simpleDataSource.deleteTask(id);
        assertThat(simpleDataSource.getAllTasks().size(), is(2));
    }

    @Test
    public void getTask() throws Exception {
        Task task = simpleDataSource.getTask(id);
        assertThat(task.getName(), is(TASK_NAME));
        assertThat(task.getText(), is(TASK_TEXT));
    }

    @Test
    public void getAllTasks() throws Exception {
        assertThat(simpleDataSource.getAllTasks().size(), is(3));
    }

    @Test
    public void updateTask() throws Exception {
        assertThat(simpleDataSource.getTask(id).getName(), is(TASK_NAME));
        assertThat(simpleDataSource.getTask(id).getText(), is(TASK_TEXT));

        simpleDataSource.updateTask(id, new Task(id, TASK_NAME_UPDATED, TASK_TEXT_UPDATED));

        assertThat(simpleDataSource.getTask(id).getName(), is(TASK_NAME_UPDATED));
        assertThat(simpleDataSource.getTask(id).getText(), is(TASK_TEXT_UPDATED));
    }
}
