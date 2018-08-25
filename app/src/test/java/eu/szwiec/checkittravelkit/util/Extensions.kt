package eu.szwiec.checkittravelkit.util

import android.graphics.drawable.Drawable
import org.robolectric.Shadows

val Drawable.resId: Int
    get() = Shadows.shadowOf(this).createdFromResId