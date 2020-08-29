package co.uk.wardone.model.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Card(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long = 0,
    @ColumnInfo(name = "title") var title: String,
    @ColumnInfo(name = "description") var description: String,
    @ColumnInfo(name = "image") var image: String = "",
    @ColumnInfo(name = "link") var link: String = "",
    @ColumnInfo(name = "timestamp") val timeStamp: Long
)