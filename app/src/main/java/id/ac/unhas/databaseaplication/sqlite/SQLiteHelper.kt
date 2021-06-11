package id.ac.unhas.databaseaplication.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import id.ac.unhas.databaseaplication.adapter.OrangModel
import java.lang.Exception

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "orang.db"
        private const val TBL_ORANG = "tbl_orang"
        private const val ID = "id"
        private const val NAMA = "nama"
        private const val EMAIL = "email"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblOrang = ("CREATE TABLE " + TBL_ORANG + "("
                + ID + " INTEGER PRIMARY KEY," + NAMA + " TEXT,"
                + EMAIL + " TEXT" + ")")
        db?.execSQL(createTblOrang)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_ORANG")
        onCreate(db)
    }

    fun insertOrang(org: OrangModel): Long {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, org.id)
        contentValues.put(NAMA, org.nama)
        contentValues.put(EMAIL, org.email)

        val success = db.insert(TBL_ORANG, null, contentValues)
        db.close()
        return success
    }

    fun getAllOrang(): ArrayList<OrangModel>{
        val orgList: ArrayList<OrangModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_ORANG"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var nama: String
        var email: String

        if (cursor.moveToFirst()){
            do{
                id = cursor.getInt(cursor.getColumnIndex("id"))
                nama = cursor.getString(cursor.getColumnIndex("nama"))
                email = cursor.getString(cursor.getColumnIndex("email"))

                val org = OrangModel(id = id, nama = nama, email = email)
                orgList.add(org)
            }while (cursor.moveToNext())

        }
        return orgList
    }

    fun updateOrang(org: OrangModel): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, org.id)
        contentValues.put(NAMA, org.nama)
        contentValues.put(EMAIL, org.email)

        val success = db.update(TBL_ORANG, contentValues, "id=" + org.id, null)
        db.close()
        return success
    }

    fun deleteOrangById(id: Int): Int {
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID,id)

        val success = db.delete(TBL_ORANG, "id=$id", null)
        db.close()
        return  success
    }

}