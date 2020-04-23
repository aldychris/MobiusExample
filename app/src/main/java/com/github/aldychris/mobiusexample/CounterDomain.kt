package com.github.aldychris.mobiusexample

sealed class CounterEvent
object Up : CounterEvent()
object Down : CounterEvent()
class InputError(error: ReportError) : CounterEvent()

sealed class CounterEffect
object ReportError: CounterEffect()
