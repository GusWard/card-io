package co.uk.wardone.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Recording(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "path") var path: String,
    @ColumnInfo(name = "length") var length: Long,
    @ColumnInfo(name = "selected") var selected: Boolean,
    @ColumnInfo(name = "timestamp") val timeStamp: Long)

