package co.uk.wardone.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import co.uk.wardone.model.utils.Keys
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [Recording::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "app_database"
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase? {

            if (instance == null) {

                instance = buildDatabase(context)
            }

            return instance
        }

        private fun buildDatabase(context: Context): AppDatabase {

            /* as this is medical data, we want our database to be encrypted */
            val key = Keys().getDatabaseEncryptionKey()
            val passphrase: ByteArray = SQLiteDatabase.getBytes(key.toCharArray())
            val encryptionHelper = SupportFactory(passphrase)

            val dbBuilder = Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .allowMainThreadQueries()
                .openHelperFactory(encryptionHelper)

            return dbBuilder.build()
        }
    }

    abstract fun getRecordingDao(): RecordingDao
}