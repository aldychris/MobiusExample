package com.github.aldychris.mobiusexample;

import com.spotify.mobius.Connection;
import com.spotify.mobius.functions.Consumer;


class CounterEffectHandler {
    static Connection<CounterEffect> effectHandler(Consumer<CounterEvent> eventConsumer) {
        return new Connection<CounterEffect>() {
            @Override
            public void accept(CounterEffect effect) {
                if (effect == CounterEffect.REPORT_ERROR_NEGATIVE) {
                    System.out.println("error!");
                }
            }

            @Override
            public void dispose() {
                // No resources to release, so we can leave this empty.
            }
        };
    }
}
