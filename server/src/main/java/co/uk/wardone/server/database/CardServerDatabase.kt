package co.uk.wardone.server.database

import android.content.Context

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.uk.wardone.server.utils.Keys
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [ServerCard::class], version = 1)
abstract class CardServerDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "server_database"
        private var instance: CardServerDatabase? = null

        fun getInstance(context: Context): CardServerDatabase? {

            if (instance == null) {

                instance = buildDatabase(context)
            }

            return instance
        }

        private fun buildDatabase(context: Context): CardServerDatabase {

            /* as this is medical data, we want our database to be encrypted */
            val key = Keys().getDatabaseEncryptionKey()
            val passphrase: ByteArray = SQLiteDatabase.getBytes(key.toCharArray())
            val encryptionHelper = SupportFactory(passphrase)

            val dbBuilder = Room.databaseBuilder(context, CardServerDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .openHelperFactory(encryptionHelper)

            return dbBuilder.build()
        }
    }

    abstract fun getCardDao(): CardDao
}