package com.example.android.architecture.blueprints.todoapp.statistics

import com.example.android.architecture.blueprints.todoapp.data.Task
import org.junit.Assert.assertEquals
import org.junit.Test

class StatisticsUtilsTest {
    @Test
    fun getActiveAndCompleteStats_noCompleted_returnsHundredZero() {
        //Text Concept
        //Note that the" Arrange, Act, Assert" (AAA) testing mnemonic is a similar concept.

        //Naming of Test
        //subjectUnderTest_actionOrInput_resultState

        // Create an active task
        //Given
        val tasks = listOf<Task>(
                Task(title = "TestTask 1", description = "this is the fist test task .", isCompleted = false),
                Task(title = "TestTask 2", description = "this is the fist test task .", isCompleted = false),
                Task(title = "TestTask 3", description = "this is the fist test task .", isCompleted = false),
                Task(title = "TestTask 4", description = "this is the fist test task .", isCompleted = false),
                Task(title = "TestTask 5", description = "this is the fist test task .", isCompleted = false),
                Task(title = "TestTask 6", description = "this is the fist test task .", isCompleted = false),
                Task(title = "TestTask 7", description = "this is the fist test task .", isCompleted = false),
                Task(title = "TestTask 8", description = "this is the fist test task .", isCompleted = false),
                Task(title = "TestTask 9", description = "this is the fist test task .", isCompleted = false),
                Task(title = "TestTask 10", description = "this is the fist test task .", isCompleted = true)
        )

        // Call your function
        //When
        val result = getActiveAndCompletedStats(tasks)

        // Check the result
        //Then
        assertEquals(result.completedTasksPercent, 10f)
        assertEquals(result.activeTasksPercent, 90f)
    }

    @Test
    fun getActiveAndCompleteState_emptyOrnull_returnZeroZero() {
        //Given - Arrange
        val tasks1 = listOf<Task>()
        val tasks2  = null
        //When - Act
        val resultOfEmptyList = getActiveAndCompletedStats(tasks1)
        val resultOfNullList = getActiveAndCompletedStats(tasks2)
        //Then - Assert
        assertEquals(resultOfEmptyList.completedTasksPercent, 0f)
        assertEquals(resultOfEmptyList.activeTasksPercent, 0f)

        assertEquals(resultOfNullList.completedTasksPercent, 0f)
        assertEquals(resultOfNullList.activeTasksPercent, 0f)
    }
}