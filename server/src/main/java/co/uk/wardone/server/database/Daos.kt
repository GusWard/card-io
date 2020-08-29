package co.uk.wardone.server.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recording: Card)

    @Delete
    fun delete(recording: Card)

    @Query("select * from Card order by timestamp desc")
    fun getAll(): List<Card>
}