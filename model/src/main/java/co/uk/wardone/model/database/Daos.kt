package co.uk.wardone.model.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RecordingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(recording: Recording)

    @Delete
    fun delete(recording: Recording)

    @Query("select * from Recording order by timestamp desc")
    fun getAll(): LiveData<List<Recording>>

    @Query("select * from Recording where id = :id")
    fun getById(id: Long): LiveData<Recording>

    @Query("select * from Recording where selected = 1")
    fun getSelected(): LiveData<List<Recording>>
}