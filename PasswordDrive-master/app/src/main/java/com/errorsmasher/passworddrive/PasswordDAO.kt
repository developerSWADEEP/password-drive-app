package com.errorsmasher.passworddrive

import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface PasswordDAO {

    @Insert
    suspend fun insertContact(contact: Password)

    @Update
    suspend fun updateContact(contact: Password)

    @Delete
    suspend fun deletContact(contact: Password)

    @Query("DELETE FROM password")
    suspend fun deletAllContact()

    @Query("DELETE FROM password WHERE id = :id")
    fun deleteById(id: Long)


    @Query("SELECT * FROM password")
    fun getContact(): LiveData<List<Password>>


}