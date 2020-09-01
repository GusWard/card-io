package co.uk.wardone.viewmodel.fragment.home

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import co.uk.wardone.model.database.Card
import co.uk.wardone.viewmodel.fragment.getOrAwaitValue
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.reflect.Field
import java.lang.reflect.Method


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class HomeViewModelTests {

    @Rule @JvmField
    var instantTaskExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var application: Application
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun createDb() {
        application = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext as Application
        assertEquals("co.uk.wardone.viewmodel.test", application.packageName)
        homeViewModel = HomeViewModel(application)
    }

    @Test
    fun searchAction() {

        val queryLiveDataField: Field = HomeViewModel::class.java.getDeclaredField("queryLiveData")
        queryLiveDataField.isAccessible = true
        val queryLiveData = queryLiveDataField.get(homeViewModel) as MutableLiveData<String>
        homeViewModel.viewModelAction(HomeViewModelActions.Search("test"))
        assertEquals("test", queryLiveData.getOrAwaitValue())
    }

    @Test
    fun updateCards() {

        val homeItemsLiveDataField: Field = HomeViewModel::class.java.getDeclaredField("homeItemsLiveData")
        homeItemsLiveDataField.isAccessible = true
        val homeItemsLiveData = homeItemsLiveDataField.get(homeViewModel) as MutableLiveData<HomeData.HomeItems>

        val updateCardsMethod: Method = HomeViewModel::class.java.methods.filter { it.name.contains("updateCards") }[0]
        updateCardsMethod.isAccessible = true

        val testCard = Card(
            title = "Card title",
            description = "Card description",
            image = "Card image",
            link = "Card link"
        )
        updateCardsMethod.invoke(homeViewModel, homeViewModel, listOf(testCard))

        val cardItem = HomeData.CardItem(
            id = testCard.id.toString(),
            title = testCard.title,
            description = testCard.description,
            image = testCard.image,
            link = testCard.link)

        /* first item should always be search */
        assertEquals(HomeData.SearchItem, homeItemsLiveData.getOrAwaitValue().items[0])
        val secondItem = homeItemsLiveData.getOrAwaitValue().items[1] as HomeData.CardItem
        assertEquals(cardItem, secondItem)
    }
}