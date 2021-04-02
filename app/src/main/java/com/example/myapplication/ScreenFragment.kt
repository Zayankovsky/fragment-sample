package com.example.myapplication

import android.content.ComponentCallbacks2
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.fragment.app.BaseFragment
import androidx.transition.Slide
import kotlinx.android.synthetic.main.fragment_screen.*

class ScreenFragment : BaseFragment(R.layout.fragment_screen), ComponentCallbacks2 {

    private val root get() = parentFragment as? RootFragment

    private val number by lazy(LazyThreadSafetyMode.NONE) { requireArguments().getInt(keyNumber) }

    private var clickCount = 0

    init {
        enterTransition = Slide(Gravity.END).setDuration(2000)
        exitTransition = Slide(Gravity.START).setDuration(2000)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.registerComponentCallbacks(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            clickCount = savedInstanceState.getInt(keyClickCount)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        text.text = "Number #$number"
        text.setOnClickListener {
            text.text = "Number #$number:${++clickCount}"
        }
        push.setOnClickListener {
            root?.push(number + 1)
        }
        pop.setOnClickListener {
            root?.pop()
        }
        clear.setOnClickListener {
            root?.clear()
        }
        setRoot.setOnClickListener {
            container?.setRoot()
        }
    }

    override fun onAnimationStart(enter: Boolean) {
        println("zayankov: ScreenFragment.onAnimationStart: $number, enter = $enter")
    }

    override fun onAnimationEnd(enter: Boolean) {
        println("zayankov: ScreenFragment.onAnimationEnd: $number, enter = $enter")
    }

    override fun onResume() {
        super.onResume()
        println("zayankov: ScreenFragment.onResume: $number")
    }

    override fun onPause() {
        println("zayankov: ScreenFragment.onPause: $number")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(keyClickCount, clickCount)
    }

    override fun onDetach() {
        requireContext().unregisterComponentCallbacks(this)
        super.onDetach()
    }

    override fun onTrimMemory(level: Int) {
        println("zayankov: MainFragment.onTrimMemory: ")
    }

    companion object {

        private const val keyNumber = "number"
        private const val keyClickCount = "clickCount"

        fun create(number: Int): ScreenFragment {
            val bundle = Bundle().apply { putInt(keyNumber, number) }
            return ScreenFragment().apply { arguments = bundle }
        }
    }
}