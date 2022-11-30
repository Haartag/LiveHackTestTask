package com.valerytimofeev.livehacktesttask.usecase

import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponse
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackResponse
import com.valerytimofeev.livehacktesttask.repository.LifeHackRepository
import com.valerytimofeev.livehacktesttask.utility.Resource
import com.valerytimofeev.livehacktesttask.utility.Status
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadDataUseCase @Inject constructor(
    private val repository: LifeHackRepository
) {
    /**
     * Make flow of [Resource] with api response [LifeHackResponse]
     */
    fun loadCompanyList(): Flow<Resource<LifeHackResponse>> = flow {
        emit(Resource.loading())
        val result = repository.getCompanyList()
        when (result.status) {
            Status.SUCCESS -> {
                emit(Resource.success(result.data))
            }
            Status.ERROR -> {
                emit(Resource.error(result.message ?: "An unknown error occurred", null))
            }
            Status.LOADING -> {
                emit(Resource.error(result.message ?: "An unknown error occurred", null))
            }
        }
    }

    /**
     * Make flow of [Resource] with api response [LifeHackDetailsResponse]
     */
    fun loadCompanyDetails(id: Int): Flow<Resource<LifeHackDetailsResponse>> = flow {
        emit(Resource.loading())
        val result = repository.getCompanyDetails(id)
        when (result.status) {
            Status.SUCCESS -> {
                emit(Resource.success(result.data))
            }
            Status.ERROR -> {
                emit(Resource.error(result.message ?: "An unknown error occurred", null))
            }
            Status.LOADING -> {
                emit(Resource.error(result.message ?: "An unknown error occurred", null))
            }
        }
    }
}