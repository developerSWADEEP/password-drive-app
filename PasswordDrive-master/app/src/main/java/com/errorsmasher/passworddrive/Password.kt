package com.errorsmasher.passworddrive

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "password")
data class Password(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    val name : String,
    val pass: String

)