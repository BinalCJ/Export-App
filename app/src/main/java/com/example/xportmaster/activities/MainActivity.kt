package com.example.xportmaster.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.xportmaster.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myImageView = findViewById<ImageView>(R.id.addNewBtn)
        myImageView.setOnClickListener {

            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        val myImageView2 = findViewById<ImageView>(R.id.detailViewBtn)
        myImageView2.setOnClickListener {
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }

    }


}