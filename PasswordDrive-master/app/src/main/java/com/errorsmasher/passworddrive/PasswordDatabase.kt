package com.errorsmasher.passworddrive

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.errorsmasher.passworddrive.Password


@Database(entities = [Password::class],version = 1)
abstract class PasswordDatabase : RoomDatabase() {

    abstract fun contactDao() : PasswordDAO



companion object{
    @Volatile
    private var INSTANCE: PasswordDatabase? = null

    fun getDatabase(context: Context):PasswordDatabase{
        if(INSTANCE== null){
            synchronized(this){
                INSTANCE = Room.databaseBuilder(context.applicationContext,PasswordDatabase::class.java,"TrueLove").build()

            }

        }

        return INSTANCE!!

    }
}





}