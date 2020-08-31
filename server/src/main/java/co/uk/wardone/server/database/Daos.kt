package co.uk.wardone.server.database

import androidx.room.*

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recording: ServerCard)

    @Query("delete from ServerCard where id = :id")
    fun delete(id: Long)

    @Query("select * from ServerCard order by timestamp desc")
    fun getAll(): List<ServerCard>
}