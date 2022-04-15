package com.example.challengechapter4

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QasbonDao {
    @Query("SELECT * FROM User WHERE username = :username AND password = :password")
    fun signIn(username: String, password: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun signUp(user: User): Long

    @Query("SELECT * FROM cash_table")
    fun getAllCash(): List<Cash>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCash(cash: Cash): Long

    @Update
    fun updateCash(cash: Cash): Int

    @Delete
    fun deleteCash(cash: Cash): Int
}
