package com.vinay.myapplication.data.remote.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "test_messages")
data class TestModel(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var bubbleId : Int



)