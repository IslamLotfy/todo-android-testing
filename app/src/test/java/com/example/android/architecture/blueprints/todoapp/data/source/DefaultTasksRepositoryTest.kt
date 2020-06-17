package com.example.android.architecture.blueprints.todoapp.data.source

import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.Result
import com.example.android.architecture.blueprints.todoapp.data.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.core.IsEqual
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class DefaultTasksRepositoryTest {
    private val task1 = Task("Title1", "Description1")
    private val task2 = Task("Title2", "Description2")
    private val task3 = Task("Title3", "Description3")
    private val remoteTasks = listOf(task1, task2).sortedBy { it.id }
    private val localTasks = listOf(task3).sortedBy { it.id }
    private val newTasks = listOf(task3).sortedBy { it.id }

    private lateinit var taskRemoteDataSource: FakeDataSource
    private lateinit var taskLocalDataSource: FakeDataSource

    private lateinit var tasksRepository: DefaultTasksRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    @Before
    fun createRepository() {
        taskLocalDataSource = FakeDataSource(localTasks.toMutableList())
        taskRemoteDataSource = FakeDataSource(remoteTasks.toMutableList())

        tasksRepository = DefaultTasksRepository(taskRemoteDataSource, taskLocalDataSource, Dispatchers.Main)

    }

    @Test
    fun getTasks_requestAllTasksFromRemoteDataSource() = mainCoroutineRule.runBlockingTest {
        val result = tasksRepository.getTasks(forceUpdate = true) as Result.Success
        assertThat(result.data, IsEqual(remoteTasks))
    }
}