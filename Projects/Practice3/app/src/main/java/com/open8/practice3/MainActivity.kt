package com.open8.practice3

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*
import kotlin.concurrent.timer
import kotlin.math.abs

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var timerTask: Timer? = null

        var isRunning = false
        var sec = 0f
        val tvR: TextView = findViewById(R.id.tv_random)
        val tvT: TextView = findViewById(R.id.tv_timer)
        val tvP: TextView = findViewById(R.id.tv_point)
        val btn: Button = findViewById(R.id.btn_kor)

        val randomBox = Random()
        val randomNum = randomBox.nextInt(1001)
        tvR.text = (randomNum.toFloat() / 100).toString()


        btn.setOnClickListener {
            isRunning = !isRunning

            if (isRunning) {
                timerTask = timer(period = 10) {
                    sec++
                    runOnUiThread {
                        tvT.text = (sec / 100).toString()
                    }
                }
            } else {
                timerTask?.cancel()
                val point = abs(sec - randomNum) / 100
                tvP.text = point.toString()
            }
        }
    }
}
