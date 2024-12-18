package com.tanovait.vanlanschotkempen.composablesamples.functional

var isEmailValid = false

data class ImmutableData(val name: String, val age: Int)
data class MutableData(var name: String, var age: Int)


fun main() {
    // Pure functions
    a()
    b(isEmailValid = false)
    b(isEmailValid = true)

    /// Immutability
    val immutableData = ImmutableData("John", 30)
    val immutableData2 = ImmutableData("John", 30)

    println("isEqual ${immutableData == immutableData2}")
    // immutableData.name = "Jane" // Compilation error
    val jane = immutableData.copy(name = "Jane")
    //jane.name = "John"

    val mutableData = MutableData("John", 30)
    val mutableData2 = MutableData("John", 30)

    println("isEqual ${mutableData == mutableData2}")

    mutableData.name = "Jane"

    println("isEqual ${mutableData == mutableData2}")

    // Beware of the Side effects
    println(mutableData)
    c(mutableData)
    println(mutableData)
}

// Beware! not a pure function
fun a() {
    println("a()")
    if (isEmailValid) {
        println("Email is valid")
    } else {
        println("Email is not valid")
    }
}

// Safe! pure function
fun b(isEmailValid: Boolean) {
    println("b()")
    if (isEmailValid) {
        println("Email is valid")
    } else {
        println("Email is not valid")
    }
}

// Beware! not a pure function
fun c(mutableData: MutableData) {
    println("c()")
    mutableData.name = "Jane 2"
    println(mutableData)
}

// Safe! immutable
fun d(immutableData: ImmutableData) {
    println("d()")
    println(immutableData)
    // immutableData.name = "Jane" // Compilation error
}

// Beware! not immutable
class Event(var isProcessed: Boolean = false)

// Beware! not immutable
fun processEvent(event: Event) {
    if (!event.isProcessed) {
        println("Processing event")
        event.isProcessed = true
    } else {
        println("Event already processed")
    }
}
