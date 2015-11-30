package org.rorschach.depend

import java.io.File
import java.text.MessageFormat
import java.util.*

public fun main(args: Array<String>) {
    for (i in loadDependGraph("declare-depend.txt")) {
        println(MessageFormat.format("{0} -> {1}", i.key, i.value))
    }
    print(isGraphLooped(loadDependGraph("declare-depend.txt"), "c"))
}

public fun loadDependGraph(path: String): Map<String, List<String>> {
    val result = HashMap<String, List<String>>()
    File(path).forEachLine {
        val e = it.replace(" ", "").split(":")
        val target = e[0]

        val d = ArrayList<String>()
        if (e.size >= 2) {
            for (i in e[1].split(",")) {
                d.add(i)
            }
        }
        result[target] = d
    }

    return result
}

public fun isGraphLooped(graph: Map<String, List<String>>, node: String): Boolean {
    var path: List<List<String>> = listOf(listOf(node))

    while(path.size != 0) {
        val newpath: MutableList<List<String>> = ArrayList<List<String>>()

        for(i in path) {
            val next = graph[i.last()] ?: throw NullPointerException()
            if (next.size == 0) {
                for(j in i) {
                    print(j);print(" -> ")
                }
                println("[end]")
            } else {
                for(j in next) {
                    if(i.any{it.equals(j)}) {
                        for(k in i) {
                            print(k);print(" -> ")
                        }
                        println(MessageFormat.format("[loop to {0}]", j))
                        return false
                    } else {
                        newpath.add(i + listOf(j))
                    }
                }
            }
            path = newpath
        }
    }
    return true
}