package com.example.android.architecture.blueprints.todoapp.tasks

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.android.architecture.blueprints.todoapp.Event
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.R
import com.example.android.architecture.blueprints.todoapp.data.Task
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTaskRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsEqual
import org.hamcrest.core.IsNot.not
import org.hamcrest.core.IsNull.nullValue
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class TasksViewModelTest {
    //To Test ViewModel Import AndroidX test and ReoboElectric libraries First
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var taskViewModel: TasksViewModel

    private lateinit var tasksRepository: FakeTaskRepository
    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2", true)
    private val task3 = Task("Title3", "Description3", true)
    private val tasks = listOf(task1, task2, task3)

    @Before
    fun setupViewModel() {
        tasksRepository = FakeTaskRepository()
        tasksRepository.addTasks(task1, task2, task3)

        taskViewModel = TasksViewModel(tasksRepository)


    }


    @Test
    fun addNewTask_setsNewTaskEvent() {
        //Given - Arrange
//        val observer = Observer<Event<Unit>> {}
//        try {
//            taskViewModel.newTaskEvent.observeForever(observer)
//            taskViewModel.addNewTask()
//            val value = taskViewModel.newTaskEvent.value
//            assertThat(value?.getContentIfNotHandled(), (not(nullValue())))
//        } finally {
//            taskViewModel.newTaskEvent.removeObserver(observer)
//        }
        taskViewModel.addNewTask()
        val v = taskViewModel.newTaskEvent.getOrAwaitValue()
        assertThat(v.getContentIfNotHandled(), not((nullValue())))
        //When - Act
        //Then - Assert
    }

    @Test
    fun setFilterAllTasks_tasksAddViewVisible() {

        // Given a fresh ViewModel

        // When the filter type is ALL_TASKS
        taskViewModel.setFiltering(TasksFilterType.ALL_TASKS)

        // Then the "Add task" action is visible
        val v = taskViewModel.tasksAddViewVisible.getOrAwaitValue()
        assertThat(v, (`is`(true)))
    }

    @Test
    fun getTasks_forceUpdate() = runBlockingTest {
        taskViewModel.loadTasks(true)
        val v = taskViewModel.items.getOrAwaitValue()
        assertThat(v, IsEqual(tasks))
    }

    @Test
    fun completeTask_dataAndSnackBarUpdated(){

        //arrange, given
        val task = Task(title = "t1",description = "d1")
        tasksRepository.addTasks(task)
        //act, when
        taskViewModel.completeTask(task,true)

        //assert, then
        assertThat(tasksRepository.tasksServiceData[task.id]?.isCompleted,`is`(true))
        val snackbar: Event<Int> = taskViewModel.snackbarText.getOrAwaitValue()
        assertThat(snackbar.getContentIfNotHandled(),`is`(R.string.task_marked_complete))


    }

}