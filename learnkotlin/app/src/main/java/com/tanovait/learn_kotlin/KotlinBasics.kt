package com.tanovait.learn_kotlin

import com.tanovait.learn_kotlin.ui.JavaProperty

/**
 *
 *   Functions and variables
 *
 */

/**
 *  Top level function
 */
fun topLevelFunction() = {
    println("Hi")
}

/**
 *  Function at the top level of a file
 *
 *  Function with expression body, (not block body)
 *  we can omit the return type
 */
fun expressionBody() = {
    println("Hi")
    "This is my return statement"
}

/**
 *  If is expression(has value) not a statement
 */
fun ifIsExpression() = {
    println("Hi")
    if (3 > 2) 3 else 2
}

val doubleValue = 9.4 // This is of type Double

class Examples {
    /**

    val - immutable reference  Use this by default, change to var only if necessary
    var - mutable reference
     **/
    fun immutableValLaterInit() {
        val message: String  // This will be initialized for sure, so we can leave it unitialized for now
        if (true) {
            message = "True"
        } else {
            message = "False"
        }
    }

    /**
     *  String templates feature
     *
     *  This is equivalent to Java string concatination Hello + Kotlin
     *
     *   The compiled code use StringBuilder
     */
    fun stringTemplate() {
        val template = "Kotlin"
        println("Hello $template")
    }

    /**
     * Escape with \
     */
    fun printDollar() {
        println("Give me \$20")
    }

    /**
     * Double quotes inside expression
     */
    fun expression() {
        println("Hello ${if (true) "Kotlin" else ""}")
    }
}

/**
Classes

Class for data is called value object

Public is default visibility

 **/
class Person(val name: String)


/**
Property

In Java this is private field  + accessor inside the class

In Kotlin properties are first-class language feature

val has only getter

var has setter and getter. Declaring a mutable property in a class


 **/

class Properties(val name: String, var isMarried: Boolean)

/**
 *  CAll Java and Kotlin class
 *
if the property name starts with is, no additional prefix for the getter is added

the property has a corresponding backing field
 */
fun callPrp() {
    val prop = Properties("Kotlin", true)
    prop.isMarried = false

    val javaProp = JavaProperty("Java", false)
    javaProp.married = false
    // javaProp.name = "New" // NO!
}

/**
Custom accessors
 **/

class CustomAcessor(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() {
            return height == width
        }

    val isShort: Boolean
        get() = true
}

/**
 Import top level function

 import any kind of declaration using the import keyword.

 See Second Fragment. It is importing and calling our top level function
 **/

/**
But you shouldn’t hesitate to pull multiple classes into the same file, especially if the classes are small
 **/

/**
 Enums and “when”
**/