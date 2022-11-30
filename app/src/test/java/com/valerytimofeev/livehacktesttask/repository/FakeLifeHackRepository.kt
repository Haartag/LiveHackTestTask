package com.valerytimofeev.livehacktesttask.repository

import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponse
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackResponse
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackResponseItem
import com.valerytimofeev.livehacktesttask.utility.Resource


class FakeLifeHackRepository() : LifeHackRepository {

    private val fakeCompanyList = arrayListOf(
        LifeHackResponseItem("1", "first name", "first img"),
        LifeHackResponseItem("2", "second name", "second img"),
        LifeHackResponseItem("3", "third name", "third img"),
        LifeHackResponseItem("4", "fourth name", "fourth img")
    )
    private val fakeCompanyListResponse = LifeHackResponse()
    init {
        fakeCompanyListResponse.addAll(fakeCompanyList)
    }


    private var returnError = false
    fun shouldReturnError(value: Boolean) {
        returnError = value
    }

    override suspend fun getCompanyList(): Resource<LifeHackResponse> {
        return if (returnError) {
            Resource.error("Error", null)
        } else {
            Resource.success(fakeCompanyListResponse)
        }
    }

    override suspend fun getCompanyDetails(id: Int): Resource<LifeHackDetailsResponse> {
        TODO("Not yet implemented")
    }

}
