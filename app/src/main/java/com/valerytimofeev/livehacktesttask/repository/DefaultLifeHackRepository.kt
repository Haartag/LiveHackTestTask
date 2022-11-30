package com.valerytimofeev.livehacktesttask.repository

import com.valerytimofeev.livehacktesttask.data.remote.LifeHackApi
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponse
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackResponse
import com.valerytimofeev.livehacktesttask.utility.Resource
import javax.inject.Inject

class DefaultLifeHackRepository @Inject constructor(
    private val api: LifeHackApi
): LifeHackRepository {

    override suspend fun getCompanyList(): Resource<LifeHackResponse> {
        val response = try {
            api.getCompanyList()
        } catch (e: Exception) {
            return Resource.error("Try to check your internet connection \n\n $e", null)
        }
        return Resource.success(response)
    }

    override suspend fun getCompanyDetails(id: Int): Resource<LifeHackDetailsResponse> {
        val response = try {
            api.getCompanyDetails(id = id)
        } catch (e: Exception) {
            return Resource.error("Try to check your internet connection \n\n $e", null)
        }
        return Resource.success(response)
    }

}