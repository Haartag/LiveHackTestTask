package com.valerytimofeev.livehacktesttask.usecase

import android.telephony.PhoneNumberUtils
import android.util.Patterns
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Phone
import com.valerytimofeev.livehacktesttask.data.remote.responses.LifeHackDetailsResponse
import com.valerytimofeev.livehacktesttask.model.TextWithIcon


class MakeTextsWithIconUseCase {
    operator fun invoke(response: LifeHackDetailsResponse): List<TextWithIcon> {
        val phone = response[0].phone.fixPhone()
        val www = response[0].www

        val outputList = mutableListOf<TextWithIcon>()

        if (PhoneNumberUtils.isGlobalPhoneNumber(phone)) outputList.add(
            TextWithIcon(
                phone,
                Icons.Default.Phone,
                "Phone",
                "Phone icon"
            )
        )
        if (Patterns.WEB_URL.matcher(www).matches()) outputList.add(
            TextWithIcon(
                www,
                Icons.Default.Info,
                "URL",
                "Website icon"
            )
        )
        return outputList
    }

    /**
     * Fix a misspelling phone number
     */
    private fun String.fixPhone(): String {
        val phoneNumber = this.filter { it.isDigit() || it == '+' }
        if (phoneNumber.length == 10) return "+7$phoneNumber"
        if (phoneNumber.length == 11 && phoneNumber.first() == '8') {
            return "+7${phoneNumber.drop(1)}"
        }
        return phoneNumber
    }
}