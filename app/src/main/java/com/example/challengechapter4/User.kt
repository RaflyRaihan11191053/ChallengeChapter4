package com.example.challengechapter4

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class User (
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "username") var username: String,
    @ColumnInfo(name = "email") var nama: String,
    @ColumnInfo(name = "password") var email: String
    ): Parcelable{
    }