package com.example.xportmaster.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.xportmaster.R
import com.example.xportmaster.models.ExporterModel
import com.google.firebase.database.FirebaseDatabase

class ExportersDetailsActivity : AppCompatActivity() {

    private lateinit var tvExpId: TextView
    private lateinit var tvExpName: TextView
    private lateinit var tvCNumber: TextView
    private lateinit var quantity: TextView
    private lateinit var tvSAddress: TextView
    private lateinit var tvRAddress: TextView
    private lateinit var tvExpItem: TextView
    private lateinit var tvPMethod: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exporters_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("exporterId").toString(),
                intent.getStringExtra("expName").toString()

            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("exporterId").toString()
            )
        }
    }

    private fun deleteRecord(
        id:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Exports").child(id)
        val xtask = dbRef.removeValue()

        xtask.addOnSuccessListener {
            Toast.makeText(this, "Deleted Successfully", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener {error ->
            Toast.makeText(this, "Deleted Unsuccessful ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView() {
        tvExpId = findViewById(R.id.tvExpId)
        tvExpName = findViewById(R.id.tvExpName)
        tvCNumber = findViewById(R.id.tvCNumber)
        quantity = findViewById(R.id.quantity)
        tvSAddress = findViewById(R.id.tvSAddress)
        tvRAddress = findViewById(R.id.tvRAddress)
        tvExpItem = findViewById(R.id.tvExpItem)
        tvPMethod = findViewById(R.id.tvPMethod)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {

        tvExpId.text = intent.getStringExtra("exporterId")
        tvExpName.text = intent.getStringExtra("expName")
        tvCNumber.text = intent.getStringExtra("expNumber")
        quantity.text = intent.getStringExtra("quantity")
        tvSAddress.text = intent.getStringExtra("sAddress")
        tvRAddress.text = intent.getStringExtra("rAddress")
        tvExpItem.text = intent.getStringExtra("expItem")
        tvPMethod.text = intent.getStringExtra("pMethod")

    }

    private fun openUpdateDialog(
        exporterId : String,
        expName: String
    ) {
        val xDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val xDialogView = inflater.inflate(R.layout.update_dialog, null)

        xDialog.setView(xDialogView)

        val etExpName = xDialogView.findViewById<EditText>(R.id.etExpName)
        val etCNumber = xDialogView.findViewById<EditText>(R.id.etCNumber)
        val etQuantity = xDialogView.findViewById<EditText>(R.id.etQuantity)
        val etSAddress = xDialogView.findViewById<EditText>(R.id.etSAddress)
        val etRAddress = xDialogView.findViewById<EditText>(R.id.etRAddress)
        val etExpItem = xDialogView.findViewById<EditText>(R.id.etExpItem)
        val etPMethod = xDialogView.findViewById<EditText>(R.id.etPMethod)
        val btnUpdateData = xDialogView.findViewById<Button>(R.id.btnUpdateData)

        etExpName.setText(intent.getStringExtra("expName").toString())
        etCNumber.setText(intent.getStringExtra("expNumber").toString())
        etQuantity.setText(intent.getStringExtra("quantity").toString())
        etSAddress.setText(intent.getStringExtra("sAddress").toString())
        etRAddress.setText(intent.getStringExtra("rAddress").toString())
        etExpItem.setText(intent.getStringExtra("expItem").toString())
        etPMethod.setText(intent.getStringExtra("pMethod").toString())

        xDialog.setTitle("Update form of $expName")

        val alertDialog = xDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateExpData(
                exporterId,
                etExpName.text.toString(),
                etCNumber.text.toString(),
                etQuantity.text.toString(),
                etSAddress.text.toString(),
                etRAddress.text.toString(),
                etExpItem.text.toString(),
                etPMethod.text.toString()
            )

            Toast.makeText(applicationContext, "Updated Successfully", Toast.LENGTH_LONG).show()

            //Setting Updated data to textviews
            tvExpName.text = etExpName.text.toString()
            tvCNumber.text = etCNumber.text.toString()
            quantity.text = etQuantity.text.toString()
            tvSAddress.text = etSAddress.text.toString()
            tvRAddress.text = etRAddress.text.toString()
            tvExpItem.text = etExpItem.text.toString()
            tvPMethod.text = etPMethod.text.toString()

            alertDialog.dismiss()

        }
    }

    private fun updateExpData(
        id:String,
        name:String,
        number:String,
        quantity:String,
        sAddress:String,
        rAddress:String,
        item:String,
        pMethod:String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Exports").child(id)
        val expInfo =ExporterModel(id, name, number, quantity, sAddress, rAddress,item, pMethod)
        dbRef.setValue(expInfo)
    }
}











