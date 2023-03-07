package com.example.namegenerator

import com.example.namegenerator.models.NamePopularity
import com.example.namegenerator.static_data.NameDataset

class HandleStaticDataset
{
    companion object {
        fun get(): List<NamePopularity> {
            val data: Array<Array<String>> = NameDataset.NAME_DATASET_STATIC
            return parseData(data)
        }

        private fun parseData(data: Array<Array<String>>): List<NamePopularity>{
            val nameList: MutableList<NamePopularity> = ArrayList()

            for (nameInfo in data) {
                val namePopularity: NamePopularity = NamePopularity(
                    nameInfo[0].toInt(),
                    nameInfo[1],
                    nameInfo[2],
                    nameInfo[3],
                    nameInfo[4].toInt(),
                    nameInfo[5].toInt()
                )
                nameList.add(namePopularity)
            }

            return nameList.toList()
        }
    }
}