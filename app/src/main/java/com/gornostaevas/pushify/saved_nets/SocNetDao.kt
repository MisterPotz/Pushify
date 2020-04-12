package com.gornostaevas.pushify.saved_nets

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SocNetDao {
    @Query("SELECT * FROM soc_networks")
    fun getAll(): List<SavedAuthorization>

    @Insert
    fun insertAll(list : List<SavedAuthorization>)

    @Delete
    fun delete(list : List<SavedAuthorization>)
}