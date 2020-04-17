package com.github.aldychris.mobiusexample;

import com.spotify.mobius.Effects;
import com.spotify.mobius.Next;


class CounterLogic {
    static int update(int counter, CounterEvent counterEvent) {
        switch (counterEvent) {
            case UP:
                return counter + 1;

            case DOWN:
                if (counter > 0) {
                    return counter - 1;
                }
            default: return counter;
        }
    }

    static Next<Integer, CounterEffect> updateWithEffect(int counter, CounterEvent counterEvent){
        switch (counterEvent) {
            case UP:
                return Next.next(counter + 1);
            case DOWN:
                if (counter > 0)
                    return Next.next(counter - 1);

                return Next.next(counter, Effects.effects(CounterEffect.REPORT_ERROR_NEGATIVE));

            default: return Next.next(counter);
        }
    }

}
