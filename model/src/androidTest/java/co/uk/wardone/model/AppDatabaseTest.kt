package co.uk.wardone.model

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import co.uk.wardone.model.database.CardDao
import co.uk.wardone.model.database.AppDatabase
import co.uk.wardone.model.database.Card
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
class AppDatabaseTest {

    private lateinit var cardDao: CardDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java).build()
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

        val newCard = Card(
            id = 1,
            title = "Test Card",
            description = "Test card description",
            link = "Test card link",
            image = "Test card image",
            timeStamp = System.currentTimeMillis())

        cardDao.insert(newCard)

        val cards = cardDao.getAllSync()
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

        val newCard = Card(
            id = 1,
            title = "Test Card",
            description = "Test card description",
            link = "Test card link",
            image = "Test card image",
            timeStamp = System.currentTimeMillis())

        val newCardTwo = Card(
            id = 2,
            title = "Test Card 2",
            description = "Test card description 2",
            link = "Test card link 2",
            image = "Test card image 2",
            timeStamp = System.currentTimeMillis())

        cardDao.insert(newCard)
        cardDao.insert(newCardTwo)

        val cards = cardDao.getAllSync()
        assertNotNull(cards)
        assertEquals(2, cards.size)

        val dbCard = cards[0]
        assertThat(dbCard.title, equalTo(newCard.title))
        assertThat(dbCard.description, equalTo(newCard.description))
        assertThat(dbCard.link, equalTo(newCard.link))
        assertThat(dbCard.image, equalTo(newCard.image))
        assertThat(dbCard.timeStamp, equalTo(newCard.timeStamp))

        val dbCardTwo = cards[1]
        assertThat(dbCardTwo.title, equalTo(dbCardTwo.title))
        assertThat(dbCardTwo.description, equalTo(dbCardTwo.description))
        assertThat(dbCardTwo.link, equalTo(dbCardTwo.link))
        assertThat(dbCardTwo.image, equalTo(dbCardTwo.image))
        assertThat(dbCardTwo.timeStamp, equalTo(dbCardTwo.timeStamp))
    }

    @Test
    @Throws(Exception::class)
    fun cardDoesNotExistAfterDelete() {

        val newCard = Card(
            id = 1,
            title = "Test Card",
            description = "Test card description",
            link = "Test card link",
            image = "Test card image",
            timeStamp = System.currentTimeMillis())

        cardDao.insert(newCard)
        val dbCard = cardDao.getAllSync()[0]
        cardDao.delete(dbCard)

        val cards = cardDao.getAllSync()
        assertEquals(0, cards.size)
    }
}