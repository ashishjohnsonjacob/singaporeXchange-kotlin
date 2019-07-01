package com.ashishjacob.singaporexchange_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.EditText
import okhttp3.OkHttpClient
import java.io.IOException;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Call;
import okhttp3.Callback;

class MainActivity : AppCompatActivity() {

    var client = OkHttpClient()

    fun apiCall (view: View) {
        // Get the text view
        val outputINRTextView = findViewById<TextView>(R.id.outputINR)
        val inputSGD = findViewById<EditText>(R.id.editText)

        val request = Request.Builder()
            .url("https://api.currconv.com/api/v7/convert?q=SGD_INR&compact=ultra&apiKey=a7a2f090-9a3d-40df-869b-f7d7340139df")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}
            override fun onResponse(call: Call, response: Response)
            {
                var rate = response.body()?.string()
                var subStr = rate?.substring(11,16)

                var subStrVar = subStr.toString()
                var rateDouble = subStrVar?.toDouble()

                var inputSGDStr = inputSGD.text.toString()

                // empty input

                if (inputSGDStr != null && inputSGDStr.length > 0) {
                    var inputSGDDouble = inputSGDStr?.toDouble()
                    var finalAmount = rateDouble * inputSGDDouble
                    println(finalAmount)

                    runOnUiThread {
                        outputINRTextView.text = inputSGDStr + " SGD = " + finalAmount.toString() + " INR"
                    }
                }
                else
                {
                    println(rateDouble)

                    runOnUiThread {
                        outputINRTextView.text = "1 SGD = " + rateDouble.toString() + " INR"
                    }
                }
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
