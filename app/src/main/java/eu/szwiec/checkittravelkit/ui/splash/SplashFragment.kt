package eu.szwiec.checkittravelkit.ui.splash

import android.animation.Animator
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.databinding.FragmentSplashBinding
import kotlinx.android.synthetic.main.fragment_splash.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SplashFragment : Fragment() {

    val viewModel: SplashViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val binding: FragmentSplashBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_splash, container, false)
        binding.let {
            it.viewModel = viewModel
            it.setLifecycleOwner(this)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        animationView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                Handler().postDelayed({
                    navigateNext()
                }, 1000)
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }
        })

        if (viewModel.isFirstLaunch) {
            animationView.playAnimation()
        } else {
            navigateNext()
        }
    }

    private fun navigateNext() {
        view?.findNavController()?.navigate(R.id.next_action)
    }
}
