package com.github.aldychris.mobiusexample

import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer

internal object CounterEffectHandler {
    @JvmStatic
    fun effectHandler(eventConsumer: Consumer<CounterEvent?>?): Connection<CounterEffect> {
        return object : Connection<CounterEffect> {
            override fun accept(effect: CounterEffect) {
                if (effect === ReportError) {
                    println("error!")
                }
            }

            override fun dispose() { // No resources to release, so we can leave this empty.
            }
        }
    }
}