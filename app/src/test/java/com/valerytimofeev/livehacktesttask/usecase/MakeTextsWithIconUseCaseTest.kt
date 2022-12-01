package com.valerytimofeev.livehacktesttask.usecase

import android.telephony.PhoneNumberUtils
import androidx.core.util.PatternsCompat
import com.google.common.truth.Truth.assertThat
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponse
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponseItem
import io.mockk.every
import io.mockk.mockkObject
import io.mockk.mockkStatic
import org.junit.Before
import org.junit.Test


class MakeTextsWithIconUseCaseTest {

    private lateinit var makeTextWithIcon: MakeTextsWithIconUseCase
    private lateinit var lifeHackDetailsResponse: LifeHackDetailsResponse

    @Before
    fun setup() {
        makeTextWithIcon = MakeTextsWithIconUseCase()
        lifeHackDetailsResponse = LifeHackDetailsResponse()
    }

    @Test
    fun `makeTextWithIcon, valid phone and url, returns 2 items`() {
        val response = LifeHackDetailsResponseItem(
            "Some long text",
            "4",
            "fourth img",
            0.0,
            0.0,
            "fourth name",
            "+79998887766",
            "www.phone.eu"
        )
        lifeHackDetailsResponse.add(response)

        mockkStatic(PhoneNumberUtils::class)
        mockkObject(PatternsCompat.WEB_URL)
        every { PhoneNumberUtils.isGlobalPhoneNumber(any()) } returns true
        every { PatternsCompat.WEB_URL.matcher(any()).matches() } returns true

        val result = makeTextWithIcon(lifeHackDetailsResponse)

        assertThat(result.size).isEqualTo(2)
        assertThat(result.map { it.text }).isEqualTo(
            listOf(
            "+79998887766", "www.phone.eu"
        )
        )
    }

    @Test
    fun `makeTextWithIcon, only phone, returns 1 item`() {
        val response = LifeHackDetailsResponseItem(
            "Some long text",
            "4",
            "fourth img",
            0.0,
            0.0,
            "fourth name",
            "+79998887766",
            ""
        )
        lifeHackDetailsResponse.add(response)

        mockkStatic(PhoneNumberUtils::class)
        mockkObject(PatternsCompat.WEB_URL)
        every { PhoneNumberUtils.isGlobalPhoneNumber(any()) } returns true
        every { PatternsCompat.WEB_URL.matcher(any()).matches() } returns false

        val result = makeTextWithIcon(lifeHackDetailsResponse)

        assertThat(result.size).isEqualTo(1)
        assertThat(result.map { it.text }).isEqualTo(
            listOf("+79998887766")
        )
    }

    @Test
    fun `makeTextWithIcon, empty phone and URL, returns empty list`() {
        val response = LifeHackDetailsResponseItem(
            "Some long text",
            "4",
            "fourth img",
            0.0,
            0.0,
            "fourth name",
            "",
            ""
        )
        lifeHackDetailsResponse.add(response)

        mockkStatic(PhoneNumberUtils::class)
        mockkObject(PatternsCompat.WEB_URL)
        every { PhoneNumberUtils.isGlobalPhoneNumber(any()) } returns false
        every { PatternsCompat.WEB_URL.matcher(any()).matches() } returns false

        val result = makeTextWithIcon(lifeHackDetailsResponse)

        assertThat(result.size).isEqualTo(0)
    }

    @Test
    fun `makeTextWithIcon, broken phone number, returns valid phone`() {
        val response = LifeHackDetailsResponseItem(
            "Some long text",
            "4",
            "fourth img",
            0.0,
            0.0,
            "fourth name",
            "8 (999) 999-88-77",
            ""
        )
        lifeHackDetailsResponse.add(response)

        mockkStatic(PhoneNumberUtils::class)
        mockkObject(PatternsCompat.WEB_URL)
        every { PhoneNumberUtils.isGlobalPhoneNumber(any()) } returns true
        every { PatternsCompat.WEB_URL.matcher(any()).matches() } returns false

        val result = makeTextWithIcon(lifeHackDetailsResponse)

        assertThat(result[0].text).isEqualTo("+79999998877")
    }

}