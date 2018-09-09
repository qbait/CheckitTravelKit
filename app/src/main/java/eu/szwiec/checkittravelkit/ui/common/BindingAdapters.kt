package eu.szwiec.checkittravelkit.ui.common

import android.view.LayoutInflater
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import com.google.android.material.floatingactionbutton.FloatingActionButton
import eu.szwiec.checkittravelkit.R
import eu.szwiec.checkittravelkit.databinding.PlugBinding
import eu.szwiec.checkittravelkit.ui.info.Plug

@BindingAdapter("plugs")
fun plugs(container: LinearLayout, plugs: List<Plug>) {
    if (container.childCount != plugs.size) {
        for (plug in plugs) {
            val imageView = LayoutInflater.from(container.context).inflate(R.layout.plug, container, false) as ImageView
            val binding = DataBindingUtil.bind<PlugBinding>(imageView)
            binding?.item = plug
            container.addView(imageView)
        }
    }
}

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
            .transition(withCrossFade())
            .into(view)
}

@BindingAdapter("items")
fun setItems(view: AutoCompleteTextView, list: List<String>?) {
    if (list != null && list.isNotEmpty()) {
        val adapter = SearchableAdapter(view.context, list)
        view.setAdapter(adapter)
    }
}

@BindingAdapter("setIcon")
fun setIcon(fab: FloatingActionButton, isFavorite: Boolean) {
    val resId = if (isFavorite) R.drawable.ic_favorite_white_24dp else R.drawable.ic_favorite_border_white_24dp
    val drawable = fab.context.getDrawable(resId)

    //workaround for https://issuetracker.google.com/issues/111316656
    fab.hide()
    fab.setImageDrawable(drawable)
    fab.show()
}
