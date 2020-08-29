package co.uk.wardone.server.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recording: Card)

    @Delete
    fun delete(recording: Card)

    @Query("select * from Card where timestamp > :since order by timestamp desc")
    fun getAll(since: Long): List<Card>
}