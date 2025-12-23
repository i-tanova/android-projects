package com.example.test.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.test.networking.API
import java.time.LocalDate
import java.time.Period

class BaseKotlinTypes(val api: API) {

    @RequiresApi(Build.VERSION_CODES.O)
    fun main() {
        /**
         * Floating-point types
         */
        println("Float value containing more than 7 digits is rounded:")
        print("val fl = 2.12345678f -> ")
        val fl = 2.12345678f
        println(fl)
        println()

        // You can't use Float or Int instead of Double parameter
        // doubleParamOnly(12f)
        // doubleParamOnly(12)

        // Write numbers as pro
        val oneMillion = 1_000_000

        // Go to Tools -> Show Kotlin Bytecode -> Decompile
        // In Java Kotlin decompiles to primitive types if possible
        // this line is decompiled to:
        // int primitiveCast = 32;
        print("This line is decompiled to int -> ")
        val primitiveCast: Int = 32
        println(primitiveCast)
        println()

        // This one is decompiled to:
        // Integer intCast = (Integer)null;
        val intCast: Int? = null

        // Compare floating point values
        println("Compare floating point values:")
        print("3.145 == 3.14 -> ")
        println(3.145 == 3.14)
        print("3.12345678900 == 3.123456789 -> ")
        println(3.12345678900 == 3.123456789)
        print("3.123456789001 == 3.123456789 -> ")
        println(3.123456789001 == 3.123456789)
        print("3.123456789001 > 3.123456789 -> ")
        println(3.123456789001 > 3.123456789)
        println()

        // Ranges
        println("Ranges: ")
        print("2 in 1..5 -> ")
        println(2 in 1..5)
        println()

        /**
         *  Explicit conversions
         */

//    You can't converse Byte to Int at assignment
//    val byte: Byte = 1
//    val int: Int = byte

        println("You can converse Byte to Int in expression:")
        print(
            """
        val byte: Byte = 1
        val result: Int = 1 + byte
        -> """
        )
        val byte: Byte = 1
        val result: Int = 1 + byte
        println(result)
        println()


        /**
         *  Arithmetic operations
         */

        println("Two Integer values return Integer value (fraction is lost):")
        print(" val x = 5 / 2 -> ")
        val x = 5 / 2
        println(x)
        println()

        println("You need to cast one of the Integer values to Double in order to get double result:")
        print("val dx = 5 / 2.toDouble() -> ")
        val dx = 5 / 2.toDouble()
        println(dx)
        println()

        /**
         * Unsigned integers
         */
        println("Unsigned integers:")
        print("val b: UByte = 1u -> ")
        val b: UByte = 1u
        println(b)
        println()

        // You can't cast unsigned to signed value
        // val i: Int = 1u

        /**
         * Null as Boolean
         */
        println("Null as Boolean:")
        print("val bool: Boolean? = null -> ")
        val bool: Boolean? = null
        println(bool)
        println()

        /**
         * Characters
         */
        println("Declare a Char:")
        print("val char: Char = 'a' -> ")
        val char: Char = 'a'
        println(char)
        println()

        println("Use digit value of a Char:")
        val numChar: Char = '9'
        val sum = numChar + 1
        print(
            """val numChar: Char = '9'
        |val sum = numChar + 1 -> """.trimMargin()
        )
        println(sum)
        println()
        print(
            """val numChar: Char = '9'
        |val sum = numChar.digitToInt() + 1 -> """.trimMargin()
        )
        val sum2 = numChar.digitToInt() + 1
        println(sum2)
        println()

        /**
         * Strings
         */
        println("Iterate over chars of a String:")
        print(
            """for(ch in string){
        |  print(ch)
    |}
    |-> """.trimMargin()
        )
        val string = "12345567"
        for (ch in string) {
            print("$ch, ")
        }
        println()
        println()

        println("The first element in the expression needs to be String:")
        // val s = 1 + "abc"  -> Can't compile this
        println("val s = 1 + \"abc\"  -> Can't compile this")
        println()

        /**
         * String literals
         */
        println("Escape characters:")
        println("\"a\"")
        println()

        println("Escape whole String:")
        println(
            """Sentence 1
        Sentence 2 on new line
    """
        )
        println()

        println("Use trimMargin() ->  | to add new line in text:")
        println(
            """     
        |Sentence 1
        |Sentence 2 on new line     
        |Sentence 3""".trimMargin()
        )
        println()

        println("Use trimIndent() -> to remove blank lines in the beginning and the end of a String:")
        val withoutIndent =
            """
            ABC
            123
            456
        """.trimIndent()
        println(withoutIndent)
        println()

        /**
         *  String templates
         */

        println("Use ${'$'} to insert expressions to a String:")
        val name = "Hamilton"
        val bday = LocalDate.parse("1975-12-12")
        println(
            "Hello $name. You are ${
                Period.between(
                    bday,
                    LocalDate.now()
                ).years
            } years old. You owe me 1${'$'} for this calculation!"
        )
        println()

        /**
         *  Arrays
         */

        println("Create array containing values:")
        val array = arrayOf(3, 4, 5)

        printArray(array)
        println()
        println()

        println("Create array containing null:")
        val arrayNul = arrayOfNulls<Int>(5)

        // That's the same as:
        // val array2 = Array<Int?>(3) { null }

        printArray(arrayNul)
        println()
        println()

        println("Set value in array: ")
        arrayNul[0] = 1
        arrayNul[1] = 2
        arrayNul[2] = arrayNul[0]?.plus(arrayNul[1] ?: 1)

        printArray(arrayNul)
        println()
        println()

        println("Use index to assign value to array: ")
        val asc = Array(5) { i -> i.toString() }
        printArray(asc)
        println()
        println()

        println("Array assignable: ")
        val stringArray = arrayOfNulls<String>(3)
        //  val anyArray: Array<Any> = stringArray  -> won't compile
        val anyArray: Array<out Any?> = stringArray

        println("Primitive arrays: ")
        val intArray = intArrayOf(1, 2, 3)
        val intArray2 = IntArray(3) // Will be initialized with 0
        val intArray42 = IntArray(3) { 42 } // Will be initialized with 42
        val intArray4 = IntArray(3) { it * 1 } // Int array 1,2,3
    }

    private fun printArray(asc: Array<out Any?>) {
        for (i in asc) {
            print("$i, ")
        }
    }

    fun doubleParamOnly(d: Double) {
        println(d)
    }
}