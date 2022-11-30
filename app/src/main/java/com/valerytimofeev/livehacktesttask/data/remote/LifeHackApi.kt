package com.valerytimofeev.livehacktesttask.data.remote

import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponse
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackResponse
import com.valerytimofeev.livehacktesttask.utility.Constants.URL_POSTFIX
import retrofit2.http.GET
import retrofit2.http.Query

interface LifeHackApi {
    @GET(URL_POSTFIX)
    suspend fun getCompanyList(): LifeHackResponse

    @GET(URL_POSTFIX)
    suspend fun getCompanyDetails(
        @Query("id") id: Int,
    ): LifeHackDetailsResponse
}