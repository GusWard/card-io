package co.uk.wardone.model.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(card: Card)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(cards: List<Card>)

    @Delete
    fun delete(card: Card)

    @Delete
    fun deleteAll(card: List<Card>)

    @Query("select * from Card order by timestamp desc")
    fun getAll(): LiveData<List<Card>>

    @Query("select * from Card order by timestamp desc")
    fun getAllSync(): List<Card>

    @Query("select * from Card order by timestamp desc limit 1")
    fun getLatest(): Card

    @Query("""
        select *
        from Card 
        where (title like :query
            or description like :query
            or link like :query)
        """)
    fun searchInternal(query: String): LiveData<List<Card>>

    fun search(query: String): LiveData<List<Card>> = searchInternal("%$query%")
}