package com.gornostaevas.pushify.saved_nets

import androidx.annotation.DrawableRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * In future can store multiple separate tokens. But now application depends on how sdks store the
 * keys.
 */
@Entity(tableName = "soc_networks")
data class SavedAuthorization(
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "adiitional") var adiitional: String,
    @ColumnInfo(name = "drawable_id") @DrawableRes var drawable : Int,
    @ColumnInfo(name = "network_type") var networkType : Int,
    @ColumnInfo(name= "access_data") var data : String?
) {
    @PrimaryKey(autoGenerate = true)  var uid: Int = 0
}