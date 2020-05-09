package com.github.aldychris.mobiusexample

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.aldychris.mobiusexample.util.SubtypeEffectHandlerBuilder
import com.github.aldychris.mobiusexample.util.loopFactory
import com.jakewharton.rxbinding3.view.clicks
import com.spotify.mobius.MobiusLoop
import com.spotify.mobius.android.AndroidLogger
import com.spotify.mobius.android.MobiusAndroid
import com.spotify.mobius.rx2.RxConnectables
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mobiusController: MobiusLoop.Controller<Int, CounterEvent>

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val rxEffectHandler: ObservableTransformer<CounterEffect, CounterEvent> = SubtypeEffectHandlerBuilder<CounterEffect, CounterEvent>()
            .addConsumer<IsError>({
                showErrorMessage()
            }, AndroidSchedulers.mainThread())
            .build()


        mobiusController = createController(rxEffectHandler, 2)
        mobiusController.connect(RxConnectables.fromTransformer(this::connectViews))

        if (savedInstanceState != null) {
            val value = savedInstanceState.getInt("value")
            mobiusController.replaceModel(value)
        }
    }

    private fun connectViews(models: Observable<Int>): Observable<CounterEvent> {
        val disposables = CompositeDisposable()
        disposables.add(models.subscribe { model ->
            tvCounter.text = model.toString()
        })

        return Observable.mergeArray(
            btnPlus.clicks().map { Up },
            btnMinus.clicks().map { Down }
        ).doOnDispose(disposables::dispose)
    }

    private fun showErrorMessage() {
        Toast.makeText(this, "Minus value", Toast.LENGTH_LONG).show()
    }

    override fun onResume() {
        super.onResume()
        mobiusController.start()
    }

    override fun onPause() {
        super.onStop()
        mobiusController.stop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mobiusController.disconnect()
    }

    override fun onSaveInstanceState(@NonNull outState: Bundle) {
        super.onSaveInstanceState(outState)
        val model = mobiusController.model
        outState.putInt("value", model)
    }

    private fun createController(effectHandlers: ObservableTransformer<CounterEffect, CounterEvent>, defaultModel: Int): MobiusLoop.Controller<Int, CounterEvent> {
        return MobiusAndroid.controller(createLoop(effectHandlers), defaultModel)
    }

    private fun createLoop(effectHandlers: ObservableTransformer<CounterEffect, CounterEvent>): MobiusLoop.Factory<Int, CounterEvent, CounterEffect> {
        return loopFactory(CounterLogic::updateWithEffect, effectHandlers)
            .logger(AndroidLogger.tag("Counter App"))
    }
}