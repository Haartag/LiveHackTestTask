package com.valerytimofeev.livehacktesttask.repository

import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponse
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackResponse
import com.valerytimofeev.livehacktesttask.utility.Resource

interface LifeHackRepository {

    suspend fun getCompanyList(): Resource<LifeHackResponse>

    suspend fun getCompanyDetails(id: Int): Resource<LifeHackDetailsResponse>

}