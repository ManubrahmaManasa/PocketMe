package com.example.my_notes_app.Models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "notes")
class Notes : Serializable {
    @PrimaryKey(autoGenerate = true)
    var iD = 0

    @JvmField
    @ColumnInfo(name = "title")
    var title = ""

    @JvmField
    @ColumnInfo(name = "notes")
    var notes = ""

    @ColumnInfo(name = "date")
    var date = ""

    @ColumnInfo(name = "pinned")
    var isPinned = false
}
