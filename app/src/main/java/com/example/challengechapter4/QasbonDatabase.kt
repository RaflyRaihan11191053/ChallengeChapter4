package com.example.challengechapter4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Cash::class], version = 1)
abstract class QasbonDatabase: RoomDatabase() {

    abstract fun qasbonDao(): QasbonDao

    companion object {
        private var INSTANCE: QasbonDatabase? = null

        fun getInstance(context: Context): QasbonDatabase? {
            if (INSTANCE == null) {
                synchronized(QasbonDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        QasbonDatabase::class.java, "student.db").build()
                }
            }
            return INSTANCE
        }

        fun destroyInstance(){
            INSTANCE = null
        }
    }

}