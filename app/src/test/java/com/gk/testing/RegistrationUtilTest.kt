package com.gk.testing

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class RegistrationUtilTest {

    @Test
    fun `empty username return false`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "",
            "123",
            "123"
        )
        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly repeated password return true`() {
        val result = RegistrationUtil.validateRegistrationInput(
            "Philip",
            "123",
            "123"
        )
        assertThat(result).isTrue()
    }

    @Test
    fun `username already exists return false`() {
        val user = RegistrationUtil.validateRegistrationInput(
            "Ganesh",
            "5446",
            "5446"
        )
        assertThat(user).isFalse()
    }

    @Test
    fun `empty password return false`() {
        val user = RegistrationUtil.validateRegistrationInput(
            "Gnasf",
            "",
            ""
        )
        assertThat(user).isFalse()
    }

    @Test
    fun `confirmed password is not same as password return false`() {
        val user = RegistrationUtil.validateRegistrationInput(
            "Test",
            "15456",
            "456545"
        )
        assertThat(user).isFalse()
    }

    @Test
    fun `password is less than 2 digit return false`() {
        val user = RegistrationUtil.validateRegistrationInput(
            "Test",
            "2",
            "656"
        )
        assertThat(user).isFalse()
    }

    @Test
    fun `check 0th  position return zero`() {
        val result = RegistrationUtil.fib(0)
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `check 1th position return one`() {
        val result = RegistrationUtil.fib(1)
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `return n-th number of series`() {
        val result = RegistrationUtil.fib(10)
        assertThat(result).isEqualTo(34)
    }

    @Test
    fun `braces set correctly return true`() {
        val result = RegistrationUtil.checkBraces("(656)")
        assertThat(result).isTrue()
    }

    @Test
    fun `braces set incorrectly return false`() {
        val result = RegistrationUtil.checkBraces("((566)")
        assertThat(result).isFalse()
    }
}