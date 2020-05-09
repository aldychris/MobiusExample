package com.github.aldychris.mobiusexample

import com.spotify.mobius.Connection
import com.spotify.mobius.functions.Consumer
import io.reactivex.Observable

internal object CounterEffectHandler {
    @JvmStatic
    fun effectHandler(eventConsumer: Consumer<CounterEvent?>?): Connection<CounterEffect> {
        return object : Connection<CounterEffect> {
            override fun accept(effect: CounterEffect) {
                if (effect === IsError) {
                    println("error!")
                }
            }

            override fun dispose() {
                // No resources to release, so we can leave this empty.
            }
        }
    }

    @JvmStatic
    fun rxEffectHandler(effects: Observable<CounterEffect>): Observable<CounterEvent> {
        return effects.ofType(IsError.javaClass)
            .flatMap {
                Observable.just(InputError(it))
            }
    }
}