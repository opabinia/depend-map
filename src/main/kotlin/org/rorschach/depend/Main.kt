package org.rorschach.depend

public fun main(args: Array<String>) {
    val depend = loadDependMap("declare-depend.txt")

    for(it in depend) println(it)
}