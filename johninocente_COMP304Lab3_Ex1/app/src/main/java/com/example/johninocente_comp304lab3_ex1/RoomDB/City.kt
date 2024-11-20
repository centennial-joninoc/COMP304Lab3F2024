package com.example.johninocente_comp304lab3_ex1.RoomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city")
data class City(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo("city_name")
    var name: String,

    @ColumnInfo("province")
    var province: String,

    @ColumnInfo("country")
    var country: String

) {

}