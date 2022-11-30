package com.valerytimofeev.livehacktesttask.usecase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackResponseItem
import com.valerytimofeev.livehacktesttask.repository.FakeLifeHackRepository
import com.valerytimofeev.livehacktesttask.utility.Resource
import com.valerytimofeev.livehacktesttask.utility.Status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class LoadDataUseCaseTest {

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var loadDataUseCase: LoadDataUseCase
    private lateinit var fakeRepository: FakeLifeHackRepository

    @Before
    fun setup() {
        fakeRepository = FakeLifeHackRepository()
        loadDataUseCase = LoadDataUseCase(fakeRepository)
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun `load company list, returns loading status as first`() = runTest {
        val result = loadDataUseCase.loadCompanyList().first()

        assertThat(result).isEqualTo(Resource(Status.LOADING, null, null))
    }

    @Test
    fun `load company list, returns success`() = runTest {
        val flow = loadDataUseCase.loadCompanyList()
        val result = flow.first { it.status != Status.LOADING }

        assertThat(result.status).isEqualTo(Status.SUCCESS)
        assertThat(result.data?.get(0)).isEqualTo(LifeHackResponseItem("1", "first name", "first img"))
    }

    @Test
    fun `load company list, returns error`() = runTest {
        fakeRepository.shouldReturnError(true)

        val flow = loadDataUseCase.loadCompanyList()
        val result = flow.first { it.status != Status.LOADING }

        assertThat(result.status).isEqualTo(Status.ERROR)
        assertThat(result.message).isEqualTo("Error")
    }

    @Test
    fun `load details, returns loading status as first`() = runTest {
        val result = loadDataUseCase.loadCompanyDetails(1).first()

        assertThat(result).isEqualTo(Resource(Status.LOADING, null, null))
    }

    @Test
    fun `load details, returns success`() = runTest {
        val flow = loadDataUseCase.loadCompanyDetails(1)
        val result = flow.first { it.status != Status.LOADING }

        assertThat(result.status).isEqualTo(Status.SUCCESS)
        assertThat(result.data?.get(0)?.name).isEqualTo("Company name")
    }

    @Test
    fun `load details, returns error`() = runTest {
        fakeRepository.shouldReturnError(true)

        val flow = loadDataUseCase.loadCompanyDetails(1)
        val result = flow.first { it.status != Status.LOADING }

        assertThat(result.status).isEqualTo(Status.ERROR)
        assertThat(result.message).isEqualTo("Error")
    }
}
