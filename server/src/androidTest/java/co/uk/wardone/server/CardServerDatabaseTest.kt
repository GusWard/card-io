package co.uk.wardone.server

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.uk.wardone.server.database.CardDao
import co.uk.wardone.server.database.CardServerDatabase
import co.uk.wardone.server.database.ServerCard
import org.hamcrest.CoreMatchers.equalTo
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.io.IOException

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class CardServerDatabaseTest {

    private lateinit var cardDao: CardDao
    private lateinit var db: CardServerDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, CardServerDatabase::class.java).build()
        cardDao = db.getCardDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun cardExistsAfterWrite() {

        val newCard = ServerCard(
            title = "Test Card",
            description = "Test card description",
            link = "Test card link",
            image = "Test card image",
            timeStamp = System.currentTimeMillis())

        cardDao.insert(newCard)

        val cards = cardDao.getAll()
        assertNotNull(cards)
        assertEquals(1, cards.size)

        val dbCard = cards[0]
        assertThat(dbCard.title, equalTo(newCard.title))
        assertThat(dbCard.description, equalTo(newCard.description))
        assertThat(dbCard.link, equalTo(newCard.link))
        assertThat(dbCard.image, equalTo(newCard.image))
        assertThat(dbCard.timeStamp, equalTo(newCard.timeStamp))
    }
    @Test
    @Throws(Exception::class)
    fun multipleCardsExistsAfterWrite() {

        val newCard = ServerCard(
            title = "Test Card",
            description = "Test card description",
            link = "Test card link",
            image = "Test card image",
            timeStamp = System.currentTimeMillis())

        cardDao.insert(newCard)
        cardDao.insert(newCard)
        cardDao.insert(newCard)
        cardDao.insert(newCard)

        val cards = cardDao.getAll()
        assertNotNull(cards)
        assertEquals(4, cards.size)

        val dbCard = cards[0]
        assertThat(dbCard.title, equalTo(newCard.title))
        assertThat(dbCard.description, equalTo(newCard.description))
        assertThat(dbCard.link, equalTo(newCard.link))
        assertThat(dbCard.image, equalTo(newCard.image))
        assertThat(dbCard.timeStamp, equalTo(newCard.timeStamp))
    }

    @Test
    @Throws(Exception::class)
    fun cardDoesNotExistAfterDelete() {

        val newCard = ServerCard(
            title = "Test Card",
            description = "Test card description",
            link = "Test card link",
            image = "Test card image",
            timeStamp = System.currentTimeMillis())

        cardDao.insert(newCard)
        val dbCard = cardDao.getAll()[0]
        cardDao.delete(dbCard.id)

        val cards = cardDao.getAll()
        assertEquals(0, cards.size)
    }
}