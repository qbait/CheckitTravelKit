package eu.szwiec.checkittravelkit.ui.search

import android.os.Bundle
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.constraintlayout.ConstraintSet
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.databinding.FragmentSearchBinding
import eu.szwiec.checkittravelkit.ui.common.hideKeyboard
import eu.szwiec.checkittravelkit.ui.common.navigateToInfo
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModel()
    private val favoritesViewModel: FavoritesViewModel by viewModel()
    private val constraintSetOnlyNationality = ConstraintSet()
    private val constraintSetAllViews = ConstraintSet()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentSearchBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        binding.let {
            it.searchViewModel = searchViewModel
            it.favoritesViewModel = favoritesViewModel
            it.setLifecycleOwner(this)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        constraintSetOnlyNationality.clone(context, R.layout.fragment_search)
        constraintSetAllViews.clone(context, R.layout.fragment_search_alt)

        searchViewModel.initState()
        searchViewModel.state.observe(this, Observer { state ->
            when (state) {
                is State.ChooseOrigin -> {
                    animate(constraintSetOnlyNationality)
                }
                is State.ChooseDestination -> {
                    animate(constraintSetAllViews)
                    destination.requestFocus()
                }
                is State.ShowInfo -> {
                    navigateToInfo(constraintLayout, state.countryName)
                    activity?.hideKeyboard()
                    searchViewModel.setState(State.ChooseDestination)
                }
            }
        })

        favoritesViewModel.favorites.observe(this, Observer { })

        setupOriginListeners()
        setupDestinationListeners()
    }

    private fun setupOriginListeners() {
        origin.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                searchViewModel.setState(State.ChooseOrigin)
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
            searchViewModel.submitDestination()
        }

        destination.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                searchViewModel.submitDestination()
            }
            true
        }
    }

    private fun animate(constraintSet: ConstraintSet) {
        TransitionManager.beginDelayedTransition(constraintLayout)
        constraintSet.applyTo(constraintLayout)
    }
}
