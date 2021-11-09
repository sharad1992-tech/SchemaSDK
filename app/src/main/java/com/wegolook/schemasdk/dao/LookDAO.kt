package com.wegolook.schemasdk.dao

import androidx.room.Dao
import androidx.room.Insert
import com.wegolook.schemasdk.entity.LookEntity

@Dao
interface LookDAO {
    @Insert
    fun insertLook(data:LookEntity)
}