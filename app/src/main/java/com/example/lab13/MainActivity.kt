package com.example.lab13

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.TextView
import com.example.lab13.model.ModelWeather
import com.example.lab13.retrofit.IRetrofitAPI
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class MainActivity : AppCompatActivity() {


   /* val URL = "https://www.nbrb.by/API/ExRates/Rates/145"
    var okHttpClient: OkHttpClient = OkHttpClient()*/

    private var weatherData: TextView? = null
    private var latitude: TextView? = null
    private var longitude: TextView? = null
    private var buttonGet: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherData = findViewById(R.id.resultView)
        buttonGet = findViewById(R.id.button)

        buttonGet?.setOnClickListener { madeRequest() }
    }

    internal fun madeRequest() {

        latitude = findViewById(R.id.latitude)
        longitude = findViewById(R.id.longitude)
        weatherData!!.text = ""

        val retrofit = Retrofit.Builder().
        baseUrl(BASE_URL).
        addConverterFactory(GsonConverterFactory.create()).
        build()

        val service = retrofit.create(IRetrofitAPI::class.java)
        val call = service.getCurrentWeatherData(longitude!!.text.toString(),
            latitude!!.text.toString(),
            APIKey)

        call.enqueue(object : Callback<ModelWeather> {
            override fun onResponse(call: Call<ModelWeather>, response: Response<ModelWeather>) {
                if (response.code() == 200) {
                    val weatherResponse = response.body()!!

                    val stringBuilder = "Country: " +
                            weatherResponse.sys!!.country +
                            "\n" +
                            "Temperature: " +
                            (weatherResponse.main!!.temp - 273) +
                            "\n" +
                            "Temperature(Min): " +
                            (weatherResponse.main!!.temp_min - 273) +
                            "\n" +
                            "Temperature(Max): " +
                            (weatherResponse.main!!.temp_max - 273) +
                            "\n" +
                            "Humidity: " +
                            weatherResponse.main!!.humidity +
                            "\n" +
                            "Pressure: " +
                            weatherResponse.main!!.pressure +
                            "\n" +
                            "Sunrise: " +
                            weatherResponse.sys!!.sunrise +
                            "\n" +
                            "Sunset: " +
                            weatherResponse.sys!!.sunset

                    weatherData!!.text = stringBuilder
                }
            }

            override fun onFailure(call: Call<ModelWeather>, t: Throwable) {
                weatherData!!.text = t.message
            }
        })

    }

    /*private fun madeRequest() {
        bank = findViewById(R.id.bank)
        state = findViewById(R.id.threadState)
       // runOnUiThread { state?.text = "Begin" }
        var request: Request = Request.Builder().
        url(URL).build()

        okHttpClient.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {
                println("Something going wrong")
            }
            override fun onResponse(call: Call?, response: Response?) {
                var json  = response?.body()?.string()
                var firstParam = (JSONObject(json).get("Cur_Name")).toString()
                var secondParam = (JSONObject(json).get("Cur_OfficialRate")).toString()
                runOnUiThread {
                    bank?.text = Html.fromHtml("$firstParam $secondParam")
                }
            }

        })

    }*/



    companion object{
        var BASE_URL = "http://api.openweathermap.org/"
        var APIKey = "755e9953779292229b046372e741ede1"
    }
}