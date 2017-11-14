package com.example.task.datasources;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.MockitoAnnotations.initMocks;

import static com.example.task.utils.UuidUtils.generateUUID;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

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
    private static final String WRONG_ID = generateUUID();
    private static final String ID = generateUUID();

    @InjectMocks
    private SimpleDataSource simpleDataSource;

    @Before
    public void setUp() {
        initMocks(this);
        simpleDataSource.insertTask(new Task(ID, TASK_NAME, TASK_TEXT));
        assertThat(simpleDataSource.getAllTasks().size(), is(3));
    }

    @After
    public void tearDown() {
        simpleDataSource.deleteTask(ID);
        assertThat(simpleDataSource.getAllTasks().size(), is(2));
    }

    @Test
    public void When_GettingTask_Expect_CorrectTaskReturned() {
        Task task = simpleDataSource.getTask(ID);
        assertThat(task.getName(), is(TASK_NAME));
        assertThat(task.getText(), is(TASK_TEXT));
    }

    @Test
    public void When_GettingAllTasks_Expect_ListOfCorrectSize() {
        assertThat(simpleDataSource.getAllTasks().size(), is(3));
    }

    @Test
    public void When_UpdatingTask_Expect_TaskToBeUpdated() {
        assertThat(simpleDataSource.getTask(ID).getName(), is(TASK_NAME));
        assertThat(simpleDataSource.getTask(ID).getText(), is(TASK_TEXT));

        simpleDataSource.updateTask(ID, new Task(ID, TASK_NAME_UPDATED, TASK_TEXT_UPDATED));

        assertThat(simpleDataSource.getTask(ID).getName(), is(TASK_NAME_UPDATED));
        assertThat(simpleDataSource.getTask(ID).getText(), is(TASK_TEXT_UPDATED));
    }

    @Test(expected = NotFoundException.class)
    public void When_GetTaskNotFound_Expect_NotFoundException() {
        simpleDataSource.getTask(WRONG_ID);
    }

    @Test(expected = NotFoundException.class)
    public void When_InsertTaskNotFound_Expect_NotFoundException() {
        simpleDataSource.updateTask(WRONG_ID, new Task(ID, TASK_NAME, TASK_TEXT));
    }

    @Test(expected = NotFoundException.class)
    public void When_DeleteTaskNotFound_Expect_NotFoundException() {
        simpleDataSource.deleteTask(WRONG_ID);
    }

    @Test(expected = BadRequestException.class)
    public void When_InsertWithConflict_Expect_NotFoundException() {
        simpleDataSource.insertTask(new Task(ID, TASK_NAME, TASK_TEXT));
    }
}
