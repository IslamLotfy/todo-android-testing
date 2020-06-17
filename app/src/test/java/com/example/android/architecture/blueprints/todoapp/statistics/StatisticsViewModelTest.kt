package com.example.android.architecture.blueprints.todoapp.statistics

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.android.architecture.blueprints.todoapp.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.data.source.FakeTaskRepository
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.hamcrest.core.Is.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StatisticsViewModelTest{
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Subject under test
    private lateinit var statisticsViewModel: StatisticsViewModel

    // Use a fake repository to be injected into the view model.
    private lateinit var tasksRepository: FakeTaskRepository

    @Before
    fun setupStatisticsViewModel() {
        // Initialise the repository with no tasks.
        tasksRepository = FakeTaskRepository()

        statisticsViewModel = StatisticsViewModel(tasksRepository)
    }

    @Test
    fun loadTasks_loading() {

        // Pause dispatcher so you can verify initial values.
        mainCoroutineRule.pauseDispatcher()
        // Load the task in the view model.
        statisticsViewModel.refresh()

        // Then progress indicator is shown.
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(true))

        // Execute pending coroutines actions.
        mainCoroutineRule.resumeDispatcher()
        // Then progress indicator is hidden.
        assertThat(statisticsViewModel.dataLoading.getOrAwaitValue(), `is`(false))
    }

    @Test
    fun loadStatisticsWhenTasksNotAvailable_callErrorAndDisplay(){
        tasksRepository.setReturnError(true)

        statisticsViewModel.refresh()

        assertThat(statisticsViewModel.error.getOrAwaitValue(),`is`(true))
        assertThat(statisticsViewModel.empty.getOrAwaitValue(),`is`(true))
    }
}