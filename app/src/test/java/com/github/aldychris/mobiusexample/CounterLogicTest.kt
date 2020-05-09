package com.github.aldychris.mobiusexample

import com.spotify.mobius.Effects
import com.spotify.mobius.First
import com.spotify.mobius.test.InitSpec
import com.spotify.mobius.test.UpdateSpec
import org.junit.Test

class CounterLogicTest {

    private val initSpec = InitSpec(::init)
    private val updateSpec = UpdateSpec(CounterLogic::updateWithEffect)

    private fun init(model: Int) : First<Int, CounterEffect> = model.let {
        First.first(0, Effects.effects())
    }

    @Test
    fun testPlus() {
        updateSpec
            .given(1)
            .whenEvent(Up)
            .then{
                assert(it.model() == 2)
            }
    }

    @Test
    fun testMinus() {
        updateSpec
            .given(1)
            .whenEvent(Down)
            .then{
                assert(it.model() == 0)
            }
    }

    @Test
    fun testError() {
        updateSpec
            .given(0)
            .whenEvent(Down)
            .then{
                assert(it.model() == 0)
                assert(it.lastNext().effects().contains(IsError))
            }
    }
}