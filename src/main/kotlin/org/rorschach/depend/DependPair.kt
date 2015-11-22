package org.rorschach.depend

import java.io.File
import java.util.*

public data class DependPair(val target: String, val source: String?)

public fun loadDependMap(path: String): Array<DependPair> {
    val set: MutableSet<DependPair> = HashSet()
    File(path).forEachLine {
        set.addAll(element(it))
    }
    return set.toTypedArray()
}

private fun element(line: String): Array<DependPair> {
    val a = line.replace(" ", "")
    val b = a.split(":")
    val target = b[0]
    val s : MutableSet<DependPair> = HashSet()

    if (b.size >= 2) {
        val c = b[1].split(",")
        for(d in c) {
            s.add(DependPair(target, d))
        }
    } else {
        s.add(DependPair(target, null))
    }

    return s.toTypedArray()
}