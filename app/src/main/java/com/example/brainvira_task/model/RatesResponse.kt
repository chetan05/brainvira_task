package com.example.brainvira_task.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class RatesResponse(
    @SerializedName("rates")
    var rates:MutableMap<String,data>? = null
): BaseErrorResponse() , Serializable {
     data class data(
         @SerializedName("CAD")
         var CAD:Double = 0.0,
         @SerializedName("HKD")
         var HKD:Double = 0.0,
         @SerializedName("ISK")
         var ISK:Double = 0.0,
         @SerializedName("PHP")
         var PHP:Double = 0.0,
         @SerializedName("DKK")
         var DKK:Double = 0.0,
         @SerializedName("HUF")
         var HUF:Double = 0.0,
         @SerializedName("CZK")
         var CZK:Double = 0.0,
         @SerializedName("GBP")
         var GBP:Double = 0.0,
         @SerializedName("RON")
         var RON:Double = 0.0,
         @SerializedName("SEK")
         var SEK:Double = 0.0,
         @SerializedName("IDR")
         var IDR:Double = 0.0,
         @SerializedName("INR")
         var INR:Double = 0.0,
         @SerializedName("BRL")
         var BRL:Double = 0.0,
         @SerializedName("RUB")
         var RUB:Double = 0.0,
         @SerializedName("HRK")
         var HRK:Double = 0.0,
         @SerializedName("JPY")
         var JPY:Double = 0.0,
         @SerializedName("THB")
         var THB:Double = 0.0,
         @SerializedName("CHF")
         var CHF:Double = 0.0,
         @SerializedName("EUR")
         var EUR:Double = 0.0,
         @SerializedName("MYR")
         var MYR:Double = 0.0,
         @SerializedName("BGN")
         var BGN:Double = 0.0,
         @SerializedName("TRY")
         var TRY:Double = 0.0,
         @SerializedName("CNY")
         var CNY:Double = 0.0,
         @SerializedName("NOK")
         var NOK:Double = 0.0,
         @SerializedName("NZD")
         var NZD:Double = 0.0,
         @SerializedName("ZAR")
         var ZAR:Double = 0.0,
         @SerializedName("USD")
         var USD:Double = 0.0,
         @SerializedName("MXN")
         var MXN:Double = 0.0,
         @SerializedName("SGD")
         var SGD:Double = 0.0,
         @SerializedName("AUD")
         var AUD:Double = 0.0,
         @SerializedName("ILS")
         var ILS:Double = 0.0,
         @SerializedName("KRW")
         var KRW:Double = 0.0,
         @SerializedName("PLN")
         var PLN:Double = 0.0,
     ):Serializable
}