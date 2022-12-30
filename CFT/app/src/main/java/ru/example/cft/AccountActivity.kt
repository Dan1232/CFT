package ru.example.cft

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.json.JSONObject
import ru.example.cft.databinding.ActivityAccountBinding
import ru.example.cft.databinding.ActivityMainBinding

class AccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = intent.getStringExtra(MainActivity.DATA_ACCOUNT).toString()
        val code = intent.getStringExtra(MainActivity.NUMBER_BIN).toString()
        binding = ActivityAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        parseAccData(data, code)
    }
    private fun parseAccData(data: String, code: String) = with(binding){
        val mainObject = JSONObject(data)
        if (mainObject.has("brand")){
            tvBrand.text = if (mainObject.getString("brand") == "null") "?" else mainObject.getString("brand")
        }
        else{
            tvBrand.text = "?"
        }
        tvCode.text = code
        if (mainObject.has("scheme")){
            tvSC.text = if (mainObject.getString("scheme")=="null") "?" else mainObject.getString("scheme")
        }else{
            tvSC.text = "?"

        }
        if (mainObject.has("type")){
            tvType.text = if (mainObject.getString("type") == "null") "?" else mainObject.getString("type")
        }
        else{
            tvType.text = "?"
        }
        if (mainObject.has("prepaid")){
            tvPrepaid.text = if (mainObject.getString("prepaid")=="null") "?" else mainObject.getString("prepaid")
        }
        else{
            tvPrepaid.text = "?"
        }
        if (mainObject.getString("number") !="null" && mainObject.getJSONObject("number").length()>0 && mainObject.has("number")){
            tvLenght.text = if (mainObject.getJSONObject("number").getString("length")=="null") "?" else mainObject.getJSONObject("number").getString("length")
            tvLuhn.text = if (mainObject.getJSONObject("number").getString("luhn")=="null") "?" else mainObject.getJSONObject("number").getString("luhn")
        } else{
            tvLenght.text = "?"
            tvLuhn.text = "?"
        }
        if (mainObject.has("country")){
            if (mainObject.getString("country") !="null" && mainObject.getJSONObject("country").length()>0){
                if (mainObject.getJSONObject("country").getString("emoji") != "null" && mainObject.getJSONObject("country").getString("name") != "null"){
                    tvCountry.text = "${mainObject.getJSONObject("country").getString("emoji")} ${mainObject.getJSONObject("country").getString("name")}"
                }else if (mainObject.getJSONObject("country").getString("emoji")=="null"){
                    tvCountry.text = "? ${mainObject.getJSONObject("country").getString("name")}"
                }else if (mainObject.getJSONObject("country").getString("name") == "null"){
                    tvCountry.text = "${mainObject.getJSONObject("country").getString("emoji")} ?"
                }else{
                    tvCountry.text = "?"
                }

                if (mainObject.getJSONObject("country").getString("latitude") != "null" && mainObject.getJSONObject("country").getString("longitude") != "null"){
                    tvLatandLong.text = "(latitude: ${mainObject.getJSONObject("country").getString("latitude").toFloat().toInt().toString()}, longitude: ${mainObject.getJSONObject("country").getString("longitude").toFloat().toInt().toString()})"
                }else if (mainObject.getJSONObject("country").getString("latitude") == "null"){
                    tvLatandLong.text = "(latitude: ?, longitude: ${mainObject.getJSONObject("country").getString("longitude").toFloat().toInt().toString()})"
                }else if (mainObject.getJSONObject("country").getString("longitude") == "null"){
                    tvLatandLong.text = "(latitude: ${mainObject.getJSONObject("country").getString("latitude").toFloat().toInt().toString()}, longitude: ?)"
                }else{
                    tvLatandLong.text = "?"
                }
            }else{
                tvCountry.text = "?"
                tvLatandLong.text = "?"
            }
        }else{
            tvCountry.text = "?"
            tvLatandLong.text = "?"
        }
        if (mainObject.has("bank")){
            if (mainObject.getString("bank") !="null" && mainObject.getJSONObject("bank").length()>0){
                if (mainObject.getJSONObject("bank").getString("name") != "null" && mainObject.getJSONObject("bank").getString("city") != "null"){
                    tvNameBank.text = "${mainObject.getJSONObject("bank").getString("name")} ${mainObject.getJSONObject("bank").getString("city")}"
                }else if (mainObject.getJSONObject("bank").getString("name") == "null"){
                    tvNameBank.text = "? ${mainObject.getJSONObject("bank").getString("city")}"
                }else if (mainObject.getJSONObject("bank").getString("city") == "null"){
                    tvNameBank.text = "${mainObject.getJSONObject("bank").getString("name")} ?"
                }else{
                    tvNameBank.text = "?"
                }
                tvUrlBank.text = if (mainObject.getJSONObject("bank").getString("url") == "null") "?" else mainObject.getJSONObject("bank").getString("url")
                tvPhoneBank.text = if (mainObject.getJSONObject("bank").getString("phone") == "null") "?" else mainObject.getJSONObject("bank").getString("phone")
            }
            else{
                tvNameBank.text = "?"
                tvUrlBank.text = "?"
                tvPhoneBank.text = "?"
            }
        }else{
            tvNameBank.text = "?"
            tvUrlBank.text = "?"
            tvPhoneBank.text = "?"
        }

    }
}