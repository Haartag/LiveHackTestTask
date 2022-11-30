package com.valerytimofeev.livehacktesttask.repository

import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponse
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponseItem
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
    private val fakeCompanyDetails = arrayListOf(
        LifeHackDetailsResponseItem(
            "Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                    "when an unknown printer took a galley of type and scrambled it to make a type " +
                    "specimen book. It has survived not only five centuries, but also the leap " +
                    "into electronic typesetting, remaining essentially unchanged.",
            "1",
            "Url",
            42.42,
            75.75,
            "Company name",
            "phone number",
            "website"
        ),
        LifeHackDetailsResponseItem(
            "Some long text",
            "4",
            "fourth img",
            0.0,
            0.0,
            "fourth name",
            "",
            ""
        )
    )
    private val fakeCompanyListResponse = LifeHackResponse()
    private val fakeCompanyDetailsResponse = LifeHackDetailsResponse()

    init {
        fakeCompanyListResponse.addAll(fakeCompanyList)
    }

    private var returnError = false
    fun shouldReturnError(value: Boolean) {
        returnError = value
    }

    private fun putDetailsIntoResponse(id: Int) {
        when (id) {
            1 -> fakeCompanyDetailsResponse.add(fakeCompanyDetails[0])
            else -> fakeCompanyDetailsResponse.add(fakeCompanyDetails[1])
        }
    }

    override suspend fun getCompanyList(): Resource<LifeHackResponse> {
        return if (returnError) {
            Resource.error("Error", null)
        } else {
            Resource.success(fakeCompanyListResponse)
        }
    }

    override suspend fun getCompanyDetails(id: Int): Resource<LifeHackDetailsResponse> {
        return if (returnError) {
            Resource.error("Error", null)
        } else {
            putDetailsIntoResponse(id)
            Resource.success(fakeCompanyDetailsResponse)
        }
    }

}
