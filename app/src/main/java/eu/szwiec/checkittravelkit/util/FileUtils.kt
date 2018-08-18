package eu.szwiec.checkittravelkit.util

import android.content.Context
import java.io.IOException

fun readFile(context: Context, inFile: String): String {
    var content = ""

    try {
        val stream = context.assets.open(inFile)

        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()
        content = String(buffer)
    } catch (e: IOException) {
        // TODO Handle exceptions here
    }

    return content
}