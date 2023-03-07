package com.example.namegenerator.controllers

import com.example.namegenerator.HandleStaticDataset
import com.example.namegenerator.http_client.HttpClient
import com.example.namegenerator.models.NamePopularity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.HashMap

class MainActivityController {
    private var httpClient: HttpClient = HttpClient()

    private lateinit var babyList: List<NamePopularity>

    fun fetchBabyData(
        successCallBack: (babyList: List<NamePopularity>) -> Unit,
        failureCallback: (exception: Throwable) -> Unit
    ) {
        httpClient
            .getBabyApiService()
            .fetchBabyInfo()
            .enqueue(object : Callback<List<NamePopularity>> {
                override fun onFailure(call: Call<List<NamePopularity>>, t: Throwable) {
                    // Error fetching API
                    failureCallback?.invoke(t)
                }

                override fun onResponse(call: Call<List<NamePopularity>>, response: Response<List<NamePopularity>>) {
                    babyList = response.body()
                    successCallBack?.invoke(babyList)
                }
            })
    }

    fun fetchBabyStaticData(
        successCallBack: (babyList: List<NamePopularity>) -> Unit,
        failureCallback: (exception: Throwable) -> Unit
    ) {
        babyList = HandleStaticDataset.get()
    }

    fun generateName(isMale: Boolean): String {
        // Filter names by gender
        val nameMap: HashMap<String, MutableList<NamePopularity>> = filterNamesByGender(isMale)
        // Calculate name popularity
        val namePopularity: Map<String, Int> = calculateNamePopularity(nameMap)
        // Generate random name
        return selectRandomName(namePopularity)
    }

    private fun filterNamesByGender(isMale: Boolean): HashMap<String, MutableList<NamePopularity>> {
        // Filter by gender or whatever it is necessary to filter
        val filteredByGender: List<NamePopularity> = babyList.filter { x -> x.isMale == isMale }
        val nameMap: HashMap<String, MutableList<NamePopularity>> = HashMap()
        for (nameEntry in filteredByGender) {
            val normalizedName: String = nameEntry.name.lowercase()
            if (nameMap[normalizedName] != null) {
                nameMap[normalizedName]?.add(nameEntry)
            } else {
                nameMap[normalizedName] = arrayListOf(nameEntry)
            }
        }
        return nameMap
    }

    private fun calculateNamePopularity(nameMap: HashMap<String, MutableList<NamePopularity>>): Map<String, Int> {
        // Calculate popularity, i.e: number of appearances * sum(number of babies) / average(ranking)
        val namePopularity: HashMap<String, Int> = HashMap()
        for (name in nameMap.keys) {
            val nameList: MutableList<NamePopularity> = nameMap[name] as MutableList<NamePopularity>
            var totalNumberOfBabies: Int = 0
            var totalRanking: Int = 0

            for (element in nameList) {
                totalNumberOfBabies += element.numberOfBabies
                totalRanking += element.nameRank
            }

            namePopularity[name] = nameList.size * totalNumberOfBabies / (totalRanking / nameList.size)
        }

        return namePopularity.toList().sortedBy { (_, v) -> v }.toMap()
    }

    private fun selectRandomName(namePopularity: Map<String, Int>): String {
        val random = Random()
        val result: String = namePopularity.keys.elementAt(random.nextInt(namePopularity.size))
        return result.replaceFirstChar { x -> x.uppercase() }
    }
}