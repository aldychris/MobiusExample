package com.github.aldychris.mobiusexample

import com.spotify.mobius.Effects
import com.spotify.mobius.Next

internal object CounterLogic {

    @JvmStatic
    fun updateWithEffect(counter: Int, counterEvent: CounterEvent?): Next<Int, CounterEffect> {
        return when (counterEvent) {
            Up -> Next.next(counter + 1)
            Down -> {
                return if (counter > 0) {
                    Next.next(counter - 1)
                } else {
                    Next.next(counter,Effects.effects(IsError))
                }
            }
            else -> Next.next(counter)
        }
    }

}