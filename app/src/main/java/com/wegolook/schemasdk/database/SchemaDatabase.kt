package com.wegolook.schemasdk.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wegolook.schemasdk.dao.LookDAO
import com.wegolook.schemasdk.entity.LookEntity

@Database(entities = [LookEntity::class],version = 1,exportSchema = false)
abstract class SchemaDatabase :RoomDatabase(){

    abstract val lookDAO:LookDAO

    companion object{
        @Volatile
        private var INSTANCE:SchemaDatabase?=null

        fun getInstance(context: Context):SchemaDatabase{
            synchronized(this){
                var instance=INSTANCE
                if (instance==null){
                    instance=Room.databaseBuilder(context.applicationContext,
                        SchemaDatabase::class.java
                    ,"schema_database").
                        fallbackToDestructiveMigration().
                    build()
                 INSTANCE=instance
                }
                return instance
            }

        }
    }
}