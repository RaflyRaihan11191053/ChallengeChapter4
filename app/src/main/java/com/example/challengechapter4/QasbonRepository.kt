package com.example.challengechapter4

import android.content.Context
import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class QasbonRepository(context: Context) {

    private val myDB = QasbonDatabase.getInstance(context)

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllCash(): List<Cash>? {
        return myDB?.qasbonDao()?.getAllCash()
    }

    suspend fun insertCash(cash: Cash): Long? {
        return myDB?.qasbonDao()?.insertCash(cash)
    }

    suspend fun updateCash(cash: Cash): Int? {
        return myDB?.qasbonDao()?.updateCash(cash)
    }

    suspend fun deleteCash(cash: Cash): Int? {
        return myDB?.qasbonDao()?.deleteCash(cash)
    }

}