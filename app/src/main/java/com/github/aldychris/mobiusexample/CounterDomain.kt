package com.github.aldychris.mobiusexample

sealed class CounterEvent
object Up : CounterEvent()
object Down : CounterEvent()
class InputError(error: IsError) : CounterEvent()

sealed class CounterEffect
object IsError: CounterEffect()
