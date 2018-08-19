package eu.szwiec.checkittravelkit.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.databinding.FragmentInfoBinding
import eu.szwiec.checkittravelkit.ui.common.navigateUp
import kotlinx.android.synthetic.main.fragment_info.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class InfoFragment : Fragment() {

    private val viewModel: InfoViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentInfoBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        binding.let {
            it.viewModel = viewModel
            it.setLifecycleOwner(this)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val countryName = InfoFragmentArgs.fromBundle(arguments).countryId
        toolbar.setNavigationOnClickListener {
            navigateUp(it)
        }

        viewModel.setup(countryName)
        viewModel.infoData.observe(this, Observer { })
    }
}
