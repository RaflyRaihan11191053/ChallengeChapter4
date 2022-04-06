package com.example.challengechapter4

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Cash (
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "date") var date: String,
    @ColumnInfo(name = "cash") var cash: Int,
    @ColumnInfo(name = "income") var income: Int,
    @ColumnInfo(name = "outcome") var outcome: Int
    ): Parcelable{
    }