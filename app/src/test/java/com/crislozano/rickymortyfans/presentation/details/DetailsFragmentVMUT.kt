package com.crislozano.rickymortyfans.presentation.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.crislozano.rickymortyfans.domain.entities.Location
import com.crislozano.rickymortyfans.domain.entities.Origin
import com.crislozano.rickymortyfans.domain.entities.SingleCharacter
import com.crislozano.rickymortyfans.domain.usecase.GetSingleCharacterUC
import com.crislozano.rickymortyfans.presentation.commons.State
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class DetailsFragmentVMTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `test loadData success`() = runTest {
        // Given
        val mockCharacter = SingleCharacter(1, "Rick", "Human", "", "", "", "", "",  listOf(), "", Origin("", ""), Location("", ""))
        val mockResponse = mockk<Response<SingleCharacter>>()
        every { mockResponse.isSuccessful } returns true
        every { mockResponse.body() } returns mockCharacter
        val getSingleCharacterUC: GetSingleCharacterUC = mockk()
        coEvery { getSingleCharacterUC.invoke(1) } returns mockResponse
        val viewModel = DetailsFragmentVM(getSingleCharacterUC)
        val observer = mockk<Observer<State>>(relaxed = true)
        viewModel.state.observeForever(observer)

        // When
        viewModel.loadData(1)
        advanceUntilIdle()

        // Verify
        verify { observer.onChanged(State.Loading) }
        verify { observer.onChanged(State.Success(mockCharacter)) }
    }

    @Test
    fun `test loadData error`() = runTest {
        // Given
        val mockResponse = mockk<Response<SingleCharacter>>()
        every { mockResponse.isSuccessful } returns false
        val getSingleCharacterUC: GetSingleCharacterUC = mockk()
        coEvery { getSingleCharacterUC.invoke(1) } returns mockResponse
        val viewModel = DetailsFragmentVM(getSingleCharacterUC)
        val observer = mockk<Observer<State>>(relaxed = true)
        viewModel.state.observeForever(observer)

        // When
        viewModel.loadData(1)
        advanceUntilIdle()

        // Verify
        verify { observer.onChanged(State.Loading) }
        verify { observer.onChanged(State.Error("Something went wrong")) }
    }
}