package eu.szwiec.checkittravelkit.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import eu.szwiec.checkittravelkit.R
import kotlinx.android.synthetic.main.fragment_info.*

class InfoFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val id = InfoFragmentArgs.fromBundle(arguments).countryId
        //tv.text = id

        toolbar.setNavigationOnClickListener {
            it.findNavController().navigateUp()
        }
    }
}
