package androidx.fragment.app

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.LayoutRes
import com.example.myapplication.MainActivity

abstract class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected val container get() = activity as? MainActivity

    override fun onCreateAnimation(transit: Int, enter: Boolean, nextAnim: Int): Animation? {
        if (nextAnim == 0) return super.onCreateAnimation(transit, enter, nextAnim)
        val animation = AnimationUtils.loadAnimation(context, nextAnim)
        animation.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(animation: Animation?) {
                onAnimationStart(enter)
            }

            override fun onAnimationEnd(animation: Animation?) {
                onAnimationEnd(enter)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        return animation
    }

    abstract fun onAnimationStart(enter: Boolean)

    abstract fun onAnimationEnd(enter: Boolean)
}