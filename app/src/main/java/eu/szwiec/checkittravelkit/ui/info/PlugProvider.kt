package eu.szwiec.checkittravelkit.ui.info

import android.content.Context
import eu.szwiec.checkittravelkit.R

class PlugProvider(private val context: Context) {

    fun provide(symbol: String): Plug {
        val defaultPlug = context.getDrawable(R.drawable.ic_plug)
        val resId = map[symbol]
        val icon = if (resId != null) context.getDrawable(resId) else defaultPlug
        return Plug(symbol, icon)
    }

    private val map = mapOf(
            "A" to R.drawable.plug_a,
            "B" to R.drawable.plug_b,
            "C" to R.drawable.plug_c,
            "D" to R.drawable.plug_d,
            "E" to R.drawable.plug_e,
            "F" to R.drawable.plug_f,
            "G" to R.drawable.plug_g,
            "H" to R.drawable.plug_h,
            "I" to R.drawable.plug_i,
            "J" to R.drawable.plug_j,
            "K" to R.drawable.plug_k,
            "L" to R.drawable.plug_l
    )

}