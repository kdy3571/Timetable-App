package kr.ac.kumoh.s20170419.mygradecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityTimetableAddBinding

class TimetableAdd : AppCompatActivity() {
    lateinit var binding : ActivityTimetableAddBinding
    companion object{
        const val QUEUE_TAG = "VolleyRequest"
    }
    private lateinit var mQueue : RequestQueue
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTimetableAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val yeardata:Array<String> = resources.getStringArray(R.array.grade)
        val yearadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, yeardata)
        binding.yearSpinner.adapter = yearadapter

        val termdata:Array<String> = resources.getStringArray(R.array.semester)
        val termadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, termdata)
        binding.termSpinner.adapter = termadapter

        val areadata:Array<String> = resources.getStringArray(R.array.area)
        val areaadapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, areadata)
        binding.areaSpinner.adapter = areaadapter

        val majordata:Array<String> = resources.getStringArray(R.array.major)
        val majoradapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, majordata)
        binding.majorSpinner.adapter = majoradapter

        mQueue = Volley.newRequestQueue(this)
        val search_button : Button = findViewById(R.id.search_button)
        search_button.setOnClickListener{

        }
    }

    override fun onStop() {
        super.onStop()
        mQueue.cancelAll(QUEUE_TAG)
    }
    private fun requestList() {
        val url = "https://expresssongdb-ocmes.run.goorm.io/?t=1651835082540"

        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            {
                binding.listview.text = it.toString()
            },
            {
                Toast.makeText(this, it.toString(), Toast.LENGTH_LONG).show()
                binding.listview.text = it.toString()
            }
        )
        request.tag = QUEUE_TAG
        mQueue.add(request)
    }
}
