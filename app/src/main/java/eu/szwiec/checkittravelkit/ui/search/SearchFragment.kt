package eu.szwiec.checkittravelkit.ui.search

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.constraintlayout.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.databinding.FragmentSearchBinding
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private val constraintSetOnlyNationality = ConstraintSet()
    private val constraintSetAllViews = ConstraintSet()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.let {
            it.searchViewModel = searchViewModel
            it.setLifecycleOwner(this)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        constraintSetOnlyNationality.clone(context, R.layout.fragment_search)
        constraintSetAllViews.clone(context, R.layout.fragment_search_alt)

        searchViewModel.state.observe(this, Observer { state ->
            when (state) {
                State.CHOOSE_ORIGIN -> {
                    animate(constraintSetOnlyNationality)
                }
                State.CHOOSE_DESTINATION -> {
                    animate(constraintSetAllViews)
                    destination.requestFocus()
                }
            }
        })

//        favoritesViewModel.favorites.observe(this, Observer {  })

        setupOriginListeners()
        setupDestinationListeners()
    }

    private fun setupOriginListeners() {
        origin.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                searchViewModel.editOrigin()
                origin.post { origin.setSelection(origin.text.length) }
            }
        }

        origin.setOnItemClickListener { parent, arg1, pos, id ->
            searchViewModel.submitOrigin()
        }

        origin.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchViewModel.submitOrigin()
            }
            true
        }
    }

    private fun setupDestinationListeners() {
        destination.setOnItemClickListener { parent, view, pos, id ->
            searchViewModel.submitDestination((view as TextView).text.toString())
        }

        destination.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchViewModel.submitDestination((view as TextView).text.toString())
            }
            true
        }
    }

    private fun animate(constraintSet: ConstraintSet) {
        TransitionManager.beginDelayedTransition(constraintLayout)
        constraintSet.applyTo(constraintLayout)
    }
}
