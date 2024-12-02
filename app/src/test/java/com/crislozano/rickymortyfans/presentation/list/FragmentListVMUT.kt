package com.crislozano.rickymortyfans.presentation.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.crislozano.rickymortyfans.domain.entities.Characters
import com.crislozano.rickymortyfans.domain.entities.Info
import com.crislozano.rickymortyfans.domain.entities.Result
import com.crislozano.rickymortyfans.domain.usecase.GetCharactersUC
import com.crislozano.rickymortyfans.presentation.commons.State
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Response

@ExperimentalCoroutinesApi
class ListFragmentVMTest {

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
        val mockCharacters = listOf(Result(1, "Rick", "Human", "", "", "", ""))
        val mockInfo = Info(1,2,"next_page", null)
        val mockResponse = mockk<Response<Characters>>()
        every { mockResponse.isSuccessful } returns true
        every { mockResponse.body() } returns Characters(mockInfo, mockCharacters)
        val getCharactersUC: GetCharactersUC = mockk()
        coEvery { getCharactersUC.invoke(1) } returns mockResponse
        val viewModel = ListFragmentVM(getCharactersUC)
        val observer = mockk<Observer<State>>(relaxed = true)
        viewModel.state.observeForever(observer)

        // When
        viewModel.loadData()
        advanceUntilIdle()

        // Verify
        verify { observer.onChanged(State.Loading) }
        verify { observer.onChanged(State.Success(mockCharacters)) }
        assertTrue(viewModel.page == 2)
        assertFalse(viewModel.isLastPage)
    }

    @Test
    fun `test loadData success last page`() = runTest {
        // Given
        val mockCharacters = listOf(Result(1, "Rick", "Human", "", "", "", ""))
        val mockInfo = Info(1,1,null, null)
        val mockResponse = mockk<Response<Characters>>()
        every { mockResponse.isSuccessful } returns true
        every { mockResponse.body() } returns Characters(mockInfo, mockCharacters)
        val getCharactersUC: GetCharactersUC = mockk()
        coEvery { getCharactersUC.invoke(1) } returns mockResponse
        val viewModel = ListFragmentVM(getCharactersUC)
        val observer = mockk<Observer<State>>(relaxed = true)
        viewModel.state.observeForever(observer)

        // When
        viewModel.loadData()
        advanceUntilIdle()

        // Verify
        verify { observer.onChanged(State.Loading) }
        verify { observer.onChanged(State.Success(mockCharacters)) }
        assertTrue(viewModel.page == 2)
        assertTrue(viewModel.isLastPage)
    }

    @Test
    fun `test loadData error`() = runTest {
        // When
        val mockResponse = mockk<Response<Characters>>()
        every { mockResponse.isSuccessful } returns false
        val getCharactersUC: GetCharactersUC = mockk()
        coEvery { getCharactersUC.invoke(1) } returns mockResponse
        val viewModel = ListFragmentVM(getCharactersUC)
        val observer = mockk<Observer<State>>(relaxed = true)
        viewModel.state.observeForever(observer)

        // When
        viewModel.loadData()
        advanceUntilIdle()

        // Verify
        verify { observer.onChanged(State.Loading) }
        verify { observer.onChanged(State.Error("Something went wrong")) }
    }
}