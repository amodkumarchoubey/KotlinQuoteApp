package com.amod.kotlinquotes.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "quoteTable")
class Quote (@ColumnInfo(name = "auther")val auther :String, @ColumnInfo(name = "quote")val quote :String, @ColumnInfo(name = "timestamp")val timeStamp :String) {
    @PrimaryKey(autoGenerate = true) var id = 0

}