package com.valerytimofeev.livehacktesttask.data.remote

import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponse
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface LifeHackApi {
    @GET("test_task/test.php")
    suspend fun getCompanyList(): LifeHackResponse

    @GET("test_task/{id}")
    suspend fun getCompanyDetails(
        @Path("id") id: Int
    ): LifeHackDetailsResponse
}