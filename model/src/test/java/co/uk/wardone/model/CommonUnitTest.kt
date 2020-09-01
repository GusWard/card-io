package co.uk.wardone.model

import co.uk.wardone.model.repositories.CardRepository
import co.uk.wardone.model.utils.calculateListDiff
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CommonUnitTest {

    @Test
    fun listDiffWorks() {

        val oldList = listOf(1, 2, 3, 4, 5)
        val newList = listOf(1, 3, 4, 5)
        val diff = calculateListDiff(oldList, newList)
        assertEquals(diff[0], 2)
    }

    @Test
    fun listDiffWorksWithAnyType() {

        val oldList = listOf("1", "2", "3", "4", "5")
        val newList = listOf("1", "3", "4", "5")
        val diff = calculateListDiff(oldList, newList)
        assertEquals(diff[0], "2")
    }
}