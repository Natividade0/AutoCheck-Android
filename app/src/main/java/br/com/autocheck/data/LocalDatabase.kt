package br.com.autocheck.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

data class Vehicle(val id: Long, val make: String, val model: String, val year: Int, val engine: String, val mileage: Int) {
    val name: String get() = "$make $model $year"
}

data class Consultation(val id: Long, val vehicle: String, val caseId: String, val title: String,
                        val level: String, val detail: String, val createdAt: Long)

class LocalDatabase(context: Context) : SQLiteOpenHelper(context, "autocheck_local.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE vehicles (id INTEGER PRIMARY KEY AUTOINCREMENT, make TEXT NOT NULL, model TEXT NOT NULL, year INTEGER NOT NULL, engine TEXT NOT NULL, mileage INTEGER NOT NULL DEFAULT 0)")
        db.execSQL("CREATE TABLE consultations (id INTEGER PRIMARY KEY AUTOINCREMENT, vehicle TEXT NOT NULL, case_id TEXT NOT NULL, title TEXT NOT NULL, level TEXT NOT NULL, detail TEXT NOT NULL, created_at INTEGER NOT NULL)")
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Migrações futuras devem preservar os dados. Nunca descartar as tabelas automaticamente.
    }
    fun vehicles(): List<Vehicle> {
        val items = mutableListOf<Vehicle>()
        readableDatabase.rawQuery("SELECT id,make,model,year,engine,mileage FROM vehicles ORDER BY id DESC", null).use { cur ->
            while (cur.moveToNext()) items.add(Vehicle(cur.getLong(0),cur.getString(1),cur.getString(2),cur.getInt(3),cur.getString(4),cur.getInt(5)))
        }
        return items
    }
    fun saveVehicle(make: String, model: String, year: Int, engine: String, mileage: Int, id: Long? = null): Long {
        val values = ContentValues().apply {
            put("make",make.trim());put("model",model.trim());put("year",year)
            put("engine",engine.trim());put("mileage",mileage)
        }
        if (id != null) { writableDatabase.update("vehicles",values,"id=?",arrayOf(id.toString())); return id }
        return writableDatabase.insertOrThrow("vehicles",null,values)
    }
    fun deleteVehicle(id: Long) = writableDatabase.delete("vehicles","id=?",arrayOf(id.toString()))
    fun consultations(): List<Consultation> {
        val items = mutableListOf<Consultation>()
        readableDatabase.rawQuery("SELECT id,vehicle,case_id,title,level,detail,created_at FROM consultations ORDER BY created_at DESC, id DESC",null).use { cur ->
            while (cur.moveToNext()) items.add(Consultation(cur.getLong(0),cur.getString(1),cur.getString(2),cur.getString(3),cur.getString(4),cur.getString(5),cur.getLong(6)))
        }
        return items
    }
    fun saveConsultation(vehicle: String, caseId: String, title: String, level: String, detail: String): Long {
        val data = ContentValues().apply {
            put("vehicle",vehicle);put("case_id",caseId);put("title",title)
            put("level",level);put("detail",detail);put("created_at",System.currentTimeMillis())
        }
        return writableDatabase.insertOrThrow("consultations",null,data)
    }
    fun deleteConsultation(id: Long) = writableDatabase.delete("consultations","id=?",arrayOf(id.toString()))
}
