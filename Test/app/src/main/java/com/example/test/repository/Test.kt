package com.example.test.repository

fun <T> fooG(ints: List<T>, initial: T, op: (T, T) -> T): T {
    var acc = initial
    for (i in ints) {
        acc = op(acc, i)
    }
    return acc
}

fun main() {
    println(fooG(listOf( 1, 2, 3), 3, {a, b -> a*b}))
}