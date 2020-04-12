package com.gornostaevas.pushify.saved_nets

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SavedAuthorization::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): SocNetDao
}