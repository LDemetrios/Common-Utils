@file:Suppress("unused")

package org.ldemetrios.utils

import java.io.*

fun File.recreate() {
    if (exists()) delete()
    forcefullyCreate()
}

fun File.forcefullyCreate() {
    if (exists()) throw IllegalAccessException()
    try {
        createNewFile()
    } catch (e: IOException) {
        File(absolutePath.split("/").dropLast(1).joinToString("/")).mkdirs()
        createNewFile()
    }
}
