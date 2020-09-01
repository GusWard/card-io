package co.uk.wardone.viewmodel.fragment.create

import co.uk.wardone.viewmodel.fragment.home.HomeViewModel
import org.junit.Test

import org.junit.Assert.*
import java.lang.reflect.Method

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class CreateViewModelUnitTest {

    @Test
    fun validateCardMinimumSuccess() {

        val valid = validateCard(CreateViewModelActions.CreateCard("Test", "Test"))
        assertTrue(valid)
    }

    @Test
    fun validateCardCompleteSuccess() {

        val valid = validateCard(CreateViewModelActions.CreateCard("Test", "Test", "Test", "Test"))
        assertTrue(valid)
    }

    @Test
    fun validateCardEmptyTitleFails() {

        val valid = validateCard(CreateViewModelActions.CreateCard("", "Test", "Test", "Test"))
        assertFalse(valid)
    }

    @Test
    fun validateCardEmptyDescriptionFails() {

        val valid = validateCard(CreateViewModelActions.CreateCard("Test", "", "Test", "Test"))
        assertFalse(valid)
    }
}