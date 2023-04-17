@file:Suppress("unused")

package org.ldemetrios.utils

import java.lang.StringBuilder
import java.util.concurrent.atomic.AtomicInteger

private val count = AtomicInteger(0)
fun execute(vararg command: String, parallel: Boolean = false) {
    if (parallel) {
        Thread {
            execute0(*command)
        }.start()
    } else {
        execute0(*command)
    }
}

private fun execute0(vararg command: String) {
    val id = count.getAndIncrement()
    println("Run Terminal$id")
    val builder = ProcessBuilder(*command)
    builder.redirectErrorStream(true)
    val process = builder.start()
    val stdout = process.inputStream
    var sb = StringBuilder()
    var ch: Int
    while (stdout.read().also { ch = it } != -1) {
        sb.append(ch.toChar())
        if (ch.toChar() in NEWLINES) {
            print("Terminal$id: $sb")
            sb = StringBuilder()
        }
    }
    if (sb.isNotBlank()) {
        println("Terminal$id: $sb")
    }
    process.waitFor()
    println("Terminal$id exited")
}
