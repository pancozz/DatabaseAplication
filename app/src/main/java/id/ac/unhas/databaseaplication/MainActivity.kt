package id.ac.unhas.databaseaplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.ac.unhas.crud.OrangAdapter
import id.ac.unhas.databaseaplication.adapter.OrangModel
import id.ac.unhas.databaseaplication.sqlite.SQLiteHelper

class MainActivity : AppCompatActivity() {
    private lateinit var idNama: EditText
    private lateinit var idEmail: EditText
    private lateinit var tmblAdd: Button
    private lateinit var tmblView: Button
    private lateinit var tmblUpdate: Button

    private lateinit var sqLiteHelper: SQLiteHelper
    private lateinit var recyclerView: RecyclerView
    private var adapter: OrangAdapter? = null
    private var org: OrangModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        initRecyclerView()
        sqLiteHelper = SQLiteHelper(this)

        tmblAdd.setOnClickListener { addOrang() }
        tmblView.setOnClickListener { viewOrang() }
        tmblUpdate.setOnClickListener { updateOrang() }
        adapter?.setOnClickItem {
            Toast.makeText(this, it.nama, Toast.LENGTH_SHORT).show()

            idNama.setText(it.nama)
            idEmail.setText(it.email)
            org = it
        }

        adapter?.setOnClickDeleteItem {
            deleteOrang(it.id)
        }

    }
    private fun viewOrang() {
        val orgList = sqLiteHelper.getAllOrang()
        Log.e("Data", "${orgList.size}")

        adapter?.addItem(orgList)
    }

    private fun addOrang() {
        val nama = idNama.text.toString()
        val email = idEmail.text.toString()

        if (nama.isEmpty() || email.isEmpty()){
            Toast.makeText(this, "Harap Mengisi kotak yang kosong", Toast.LENGTH_SHORT).show()
        }else{
            val org = OrangModel(nama= nama, email = email)
            val status = sqLiteHelper.insertOrang(org)

            if(status > -1){
                Toast.makeText(this,"Orang ditambah...", Toast.LENGTH_SHORT).show()
                clearEditText()
                addOrang()
            }else{
                Toast.makeText(this, "Gagal menambahkan", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun updateOrang(){
        val nama = idNama.text.toString()
        val email = idEmail.text.toString()

        if(nama == org?.nama && email == org?.email){
            Toast.makeText(this, "Data telah diupdate...", Toast.LENGTH_SHORT).show()
            return
        }

        if(org == null) return

        val org = OrangModel(id = org!!.id, nama = nama, email = email)
        val status = sqLiteHelper.updateOrang(org)
        if(status > -1){
            clearEditText()
            viewOrang()
        }
    }

    private fun deleteOrang(id: Int){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Apakah anda ingin menghapusnya?")
        builder.setCancelable(true)
        builder.setPositiveButton("Ya"){ dialog, _ ->
            sqLiteHelper.deleteOrangById(id)
            viewOrang()
            dialog.dismiss()
        }
        builder.setNegativeButton("Tidak"){ dialog, _ ->
            dialog.dismiss()
        }

        val alert = builder.create()
        alert.show()

    }

    private fun clearEditText() {
        idNama.setText("")
        idEmail.setText("")
        idNama.requestFocus()
    }

    private fun initRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = OrangAdapter()
        recyclerView.adapter = adapter
    }

    private fun initView() {
        idNama = findViewById(R.id.idNama)
        idEmail = findViewById(R.id.idEmail)
        tmblAdd = findViewById(R.id.tmblAdd)
        tmblView = findViewById(R.id.tmblView)
        tmblUpdate = findViewById(R.id.tmblUpdate)
        recyclerView = findViewById(R.id.recyclerView)
    }
}