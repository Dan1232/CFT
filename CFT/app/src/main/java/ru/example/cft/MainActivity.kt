package ru.example.cft

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import ru.example.cft.adapter.LibraryAdapter
import ru.example.cft.databinding.ActivityMainBinding
import ru.example.cft.db.MainDB
import ru.example.cft.db.MainViewModel
import ru.example.cft.entities.LibraryItem


class MainActivity : AppCompatActivity(), LibraryAdapter.Listener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: LibraryAdapter
    private val mainViewModel: MainViewModel by viewModels {
        MainViewModel.MainViewModelFactory((applicationContext as MainApp).database)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRcView()
        observer()

        binding.bFound.setOnClickListener{
            val code = binding.edBin.text.toString()
            addLibrary(code)
            requestBinData(code)

        }
    }

    private fun initRcView() = with(binding){
        rcLibrary.layoutManager = LinearLayoutManager(this@MainActivity)
        adapter = LibraryAdapter(this@MainActivity)
        rcLibrary.adapter = adapter
    }
    private fun addLibrary(code: String){
        val item = LibraryItem(
            null,
            code
        )
        mainViewModel.insertLibrary(item)
    }

    private fun observer(){
        mainViewModel.getAllLibraryItems.observe(this, {
            adapter.submitList(it)
        })
    }

    private fun requestBinData(code:String){
        val url = "https://lookup.binlist.net/${code}"

        val queue = Volley.newRequestQueue(this)
        val request = StringRequest(
            Request.Method.GET,
            url,
            {
                    result ->
                val intent = Intent(this, AccountActivity::class.java).apply {
                    putExtra(NUMBER_BIN, code)
                    putExtra(DATA_ACCOUNT, result)
                }

                startActivity(intent)
            },
            {
                val transaction  = supportFragmentManager.beginTransaction()
                transaction.show(HttpFragment.newInstance())
                transaction.commit()
            }
        )
        queue.add(request)
    }

    companion object {
        const val NUMBER_BIN = "NUMBER_BIN"
        const val DATA_ACCOUNT = "DATA_ACCOUNT"

    }

    override fun onClick(code: String) {
        requestBinData(code)
    }
}