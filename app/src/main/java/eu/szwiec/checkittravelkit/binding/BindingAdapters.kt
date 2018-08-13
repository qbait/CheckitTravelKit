package eu.szwiec.checkittravelkit.binding

import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

//import eu.szwiec.checkittravelkit.databinding.PlugBinding
//import eu.szwiec.checkittravelkit.ui.info.PlugViewModel
//
//@BindingAdapter("plugs")
//fun plugs(container: LinearLayout, plugs: List<PlugViewModel>) {
//    if (container.childCount != plugs.size) {
//        for (plug in plugs) {
//            val imageView = LayoutInflater.from(container.context).inflate(R.layout.plug, container, false) as ImageView
//            val binding = DataBindingUtil.bind<PlugBinding>(imageView)
//            binding?.item = plug
//            container.addView(imageView)
//        }
//    }
//}

@BindingAdapter("hideWhenEmpty")
fun hideWhenEmpty(view: View, text: String) {
    if (text.isEmpty()) {
        view.visibility = View.GONE
    } else {
        view.visibility = View.VISIBLE
    }
}

@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, url: String) {
    Glide.with(view)
            .load(url)
            .into(view);
}

@BindingAdapter("items")
fun setItems(view: AutoCompleteTextView, list: List<String>?) {
    if (list != null && list.isNotEmpty()) {
        val adapter = ArrayAdapter<String>(view.context, android.R.layout.simple_spinner_dropdown_item, list)
        view.setAdapter(adapter)
    }
}
