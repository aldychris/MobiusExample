package com.github.aldychris.mobiusexample

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.spotify.mobius.Mobius
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.functions.Consumer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var loop: MobiusLoop<Int, CounterEvent?, CounterEffect>

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loop = Mobius.loop<Int, CounterEvent?, CounterEffect>(CounterLogic::updateWithEffect, CounterEffectHandler::effectHandler)
            .startFrom(2)

        loop.observe(Consumer { counter: Int ->
            tvCounter.text = counter.toString()
        })
    }

    fun btnPlusClicked(view: View?) {
        loop.dispatchEvent(Up)
    }

    fun btnMinClicked(view: View?) {
        loop.dispatchEvent(Down)
    }

    override fun onStop() {
        loop.dispose()
        super.onStop()
    }

    override fun onDestroy() {
        loop.dispose()
        super.onDestroy()
    }
}