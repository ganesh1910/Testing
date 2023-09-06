package com.gk.testing

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComprerTest {

    private lateinit var resourceComprer: ResourceComprer

    @Before
    fun setup(){
        resourceComprer = ResourceComprer()
    }

    @After
    fun teardown(){

    }

    @Test
    fun stringResourceSameAsGivenString_returnsTrue() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComprer.isEqual(context, R.string.app_name, "Testing")
        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDifferentAsGivenString_returnFalse() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComprer.isEqual(context, R.string.app_name, "Ganesh")
        assertThat(result).isFalse()
    }
}