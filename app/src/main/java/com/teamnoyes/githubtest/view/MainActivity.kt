package com.teamnoyes.githubtest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.teamnoyes.githubtest.R
import com.teamnoyes.githubtest.utils.NetworkLiveData

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        NetworkLiveData.observe(this, {
            if (!it) {
                Toast.makeText(this, getString(R.string.pleas_check_your_network), Toast.LENGTH_SHORT).show()
            }
        })
    }
}