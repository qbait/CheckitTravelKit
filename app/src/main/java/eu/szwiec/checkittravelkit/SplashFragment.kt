package eu.szwiec.checkittravelkit

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_splash.*

class SplashFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (isFirstLaunch()) {
            animationView.playAnimation()
            animationView.addAnimatorListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                }

                override fun onAnimationEnd(animation: Animator) {
                    Handler().postDelayed({
                        view.findNavController().navigate(R.id.next_action)
                    }, 1000)
                }

                override fun onAnimationCancel(animation: Animator) {
                }

                override fun onAnimationRepeat(animation: Animator) {
                }
            })
        } else {
            view.findNavController().navigate(R.id.next_action)
        }
    }

    private fun isFirstLaunch(): Boolean {
        return true
    }
}
