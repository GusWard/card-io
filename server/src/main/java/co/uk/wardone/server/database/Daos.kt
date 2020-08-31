package co.uk.wardone.server.database

import androidx.room.*

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recording: ServerCard)

    @Delete
    fun delete(recording: ServerCard)

    @Query("select * from ServerCard order by timestamp desc")
    fun getAll(): List<ServerCard>
}