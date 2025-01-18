package com.wirasukmasaputra.pemesanantiketpesawat.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wirasukmasaputra.pemesanantiketpesawat.database.dao.DatabaseDao
import com.wirasukmasaputra.pemesanantiketpesawat.model.ModelDatabase


@Database(entities = [ModelDatabase::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun databaseDao(): DatabaseDao?
}