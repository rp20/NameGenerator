package com.example.namegenerator.models

import com.google.gson.annotations.SerializedName

const val MALE_STR: String = "male";

public data class NamePopularity(
    @SerializedName("year")
    var yearOfBirth: Int,
    @SerializedName("gender")
    var gender: String,
    @SerializedName("ethnicity")
    var ethnicity: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("number")
    var numberOfBabies: Int,
    @SerializedName("rank")
    var nameRank: Int
) {
    var isMale: Boolean = MALE_STR.equals(gender, true)
}