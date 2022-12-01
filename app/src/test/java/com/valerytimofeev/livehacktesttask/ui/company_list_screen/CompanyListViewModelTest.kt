package com.valerytimofeev.livehacktesttask.ui.company_list_screen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.valerytimofeev.livehacktesttask.usecase.LoadDataUseCase
import com.valerytimofeev.livehacktesttask.utility.Constants
import com.valerytimofeev.livehacktesttask.utility.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CompanyListViewModelTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val loadDataUseCase: LoadDataUseCase = mockk {
        coEvery { loadCompanyList() } returns flow {
            emit(Resource.loading())
        }
    }
    private lateinit var viewModel: CompanyListViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(mainThreadSurrogate)
        viewModel = CompanyListViewModel(loadDataUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `makeUrlForImg test`() = runBlocking {
        viewModel.loadCompanyList()
        assertThat(viewModel.makeUrlForImg("1")).isEqualTo("${Constants.IMG_URL_TEMPLATE}1.jpg")
    }
}