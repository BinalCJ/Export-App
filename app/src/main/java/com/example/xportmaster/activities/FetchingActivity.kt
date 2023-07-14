package com.example.xportmaster.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.xportmaster.R
import com.example.xportmaster.adapters.ExpAdapter
import com.example.xportmaster.models.ExporterModel
import com.google.firebase.database.*

class FetchingActivity : AppCompatActivity() {

    private lateinit var exporterRecyclerView: RecyclerView
    private lateinit var tvLoadingData : TextView
    private lateinit var expList: ArrayList<ExporterModel>
    private lateinit var dbRef : DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fetching)

        exporterRecyclerView = findViewById(R.id.rvExp)
        exporterRecyclerView.layoutManager = LinearLayoutManager(this)
        exporterRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.loadingData)

        expList = arrayListOf<ExporterModel>()

        getExportersData()
    }
    private fun getExportersData() {
        exporterRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Exports")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                expList.clear()
                if(snapshot.exists()) {
                    for (expSnap in snapshot.children) {
                        val expData = expSnap.getValue(ExporterModel::class.java)
                        expList.add(expData!!)
                    }
                    val xAdapter = ExpAdapter(expList)
                    exporterRecyclerView.adapter = xAdapter

                    xAdapter.setOnItemClickListner(object : ExpAdapter.onItemClickListner{
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@FetchingActivity, ExportersDetailsActivity::class.java)

                            //Put the extra data to this intent

                            intent.putExtra("exporterId", expList[position].exporterId)
                            intent.putExtra("expName", expList[position].User_Name)
                            intent.putExtra("expNumber", expList[position].Phone_Number)
                            intent.putExtra("quantity", expList[position].Quantity)
                            intent.putExtra("sAddress", expList[position].Sender_Address)
                            intent.putExtra("rAddress", expList[position].Reciever_Address)
                            intent.putExtra("expItem", expList[position].Export_item)
                            intent.putExtra("pMethod", expList[position].Payment_Method)
                            startActivity(intent)
                        }
                    })

                    exporterRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}



















