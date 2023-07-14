package com.example.xportmaster.activities

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.xportmaster.R
import com.example.xportmaster.models.ExporterModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class MainActivity2 : AppCompatActivity() {

    private lateinit var name: EditText
    private lateinit var sPhoneNumber: EditText
    private lateinit var quantity: EditText
    private lateinit var sPostalAddress: EditText
    private lateinit var rPostalAddress: EditText
    private lateinit var exportItem: EditText
    private lateinit var paymentMethod: AutoCompleteTextView
    private lateinit var confirm1: Button
    //private lateinit var cancel: Button

    private lateinit var dbref: DatabaseReference

//    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        val cancelBtn = findViewById<Button>(R.id.cancel)
        cancelBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        name = findViewById(R.id.name)
        sPhoneNumber = findViewById(R.id.sPhoneNumber)
        quantity = findViewById(R.id.quantity)
        sPostalAddress = findViewById(R.id.sPostalAddress)
        rPostalAddress = findViewById(R.id.rPostalAddress)
        exportItem = findViewById(R.id.exportItem)
        paymentMethod = findViewById(R.id.paymentMethod1)
        confirm1 = findViewById(R.id.confirm)


        dbref = FirebaseDatabase.getInstance().getReference("Exports")

        confirm1.setOnClickListener {
            saveExportData()
        }

        val payments = listOf("Cash", "Online Transfer", "PayPal")
        val autoComplete: AutoCompleteTextView = findViewById(R.id.paymentMethod1)

        val adapter = ArrayAdapter(this, R.layout.list_payment, payments)
        autoComplete.setAdapter(adapter)

        autoComplete.onItemClickListener =
            AdapterView.OnItemClickListener { adapterView, _, i, _ ->
                val paymentSelected = adapterView.getItemAtPosition(i)
                Toast.makeText(this, "Payment:$paymentSelected", Toast.LENGTH_SHORT).show()
            }
    }

    private fun saveExportData() {
        val userName = name.text.toString()
        val phoneNumber = sPhoneNumber.text.toString()
        val quantity1 = quantity.text.toString()
        val sAddress = sPostalAddress.text.toString()
        val rAddress = rPostalAddress.text.toString()
        val Export_Item = exportItem.text.toString()
        val Payment_Method = paymentMethod.text.toString()



        if (userName.isEmpty()) {
            name.error = "Name required"

        }
        if (phoneNumber.isEmpty()) {
            sPhoneNumber.error = "Contact number required"

        }
        if (quantity1.isEmpty()) {
            quantity.error = "Quantity required"

        }
        if (sAddress.isEmpty()) {
            sPostalAddress.error = "Sender's Postal Address required"

        }
        if (rAddress.isEmpty()) {
            rPostalAddress.error = "Receivers Postal Address required"

        }
        if (Export_Item.isEmpty()) {
            exportItem.error = "Export Item required"

        }
        if (Payment_Method.isEmpty()) {
            paymentMethod.error = "Export Item required"
        }


        val exporterId = dbref.push().key!!

        val exporter =
            ExporterModel(exporterId, userName, phoneNumber, quantity1, sAddress, rAddress, Export_Item, Payment_Method)

        if (name.text.isNotEmpty() && sPhoneNumber.text.isNotEmpty() && quantity.text.isNotEmpty() && sPostalAddress.text.isNotEmpty() && rPostalAddress.text.isNotEmpty() && exportItem.text.isNotEmpty()) {
            dbref.child(exporterId).setValue(exporter).addOnCompleteListener {
                Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_LONG).show()
            }.addOnFailureListener { err ->
                Toast.makeText(this, "Data Not Insert ${err.message}", Toast.LENGTH_LONG).show()
            }
            confirm1.setOnClickListener {
                startActivity(
                    Intent(
                        this,
                        FetchingActivity::class.java
                    )
                )
            }
        }
    }
}