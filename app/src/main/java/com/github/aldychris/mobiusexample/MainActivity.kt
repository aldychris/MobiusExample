package com.github.aldychris.mobiusexample

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.aldychris.mobiusexample.util.SubtypeEffectHandlerBuilder
import com.github.aldychris.mobiusexample.util.loopFactory
import com.github.aldychris.mobiusexample.util.updateWrapper
import com.spotify.mobius.*
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.functions.Consumer
import com.spotify.mobius.rx2.RxMobius
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var loop: MobiusLoop<Int, CounterEvent?, CounterEffect>

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rxEffectHandler: ObservableTransformer<CounterEffect, CounterEvent?> = SubtypeEffectHandlerBuilder<CounterEffect, CounterEvent?>()
            .addConsumer<ReportError>({
                showErrorMessage()
            }, AndroidSchedulers.mainThread())
            .build()

        loop = RxMobius.loop(updateWrapper(CounterLogic::updateWithEffect), rxEffectHandler)
            .startFrom(2)

        loop.observe(Consumer { counter: Int ->
            tvCounter.text = counter.toString()
        })
    }

    private fun showErrorMessage() {
        Toast.makeText(this, "Minus value", Toast.LENGTH_LONG).show()
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