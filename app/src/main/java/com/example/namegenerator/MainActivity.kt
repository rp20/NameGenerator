package com.example.namegenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.namegenerator.controllers.MainActivityController
import com.example.namegenerator.models.NamePopularity
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var controller: MainActivityController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupViewEvents()

        controller = MainActivityController()

        fetchBabyStaticData()
        // fetchBabyData();
    }

    private fun setupViewEvents() {
        val maleBtn = findViewById<Button>(R.id.male_button)
        val femaleBtn = findViewById<Button>(R.id.female_button)

        maleBtn.setOnClickListener {
            val babyName = controller.generateName(true)
            Toast.makeText(this@MainActivity, "${getString(R.string.generated_name_description)}: $babyName", Toast.LENGTH_SHORT).show()
        }

        femaleBtn.setOnClickListener {
            val babyName = controller.generateName(false)
            Toast.makeText(this@MainActivity, "${getString(R.string.generated_name_description)}: $babyName", Toast.LENGTH_SHORT).show()
        }
    }

    private fun fetchBabyStaticData() {
        controller.fetchBabyStaticData(this::successFetch,this::failFetch)
    }

    private fun fetchBabyData() {
        controller.fetchBabyData(this::successFetch,this::failFetch)
    }

    private fun successFetch(babyList: List<NamePopularity>) {
        Toast.makeText(this@MainActivity, "Data fetch success", Toast.LENGTH_SHORT).show()
    }

    private fun failFetch(exception: Throwable) {
        Toast.makeText(this@MainActivity, "Data fetch error: ${exception.message}", Toast.LENGTH_SHORT).show()
    }

}