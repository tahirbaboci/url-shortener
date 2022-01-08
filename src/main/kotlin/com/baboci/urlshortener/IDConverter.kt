package com.baboci.urlshortener
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.math.pow


object IDConverter {

    init{
        initializeCharToIndexTable()
        initializeIndexToCharTable()
    }
    var charToIndexTable: HashMap<Char, Int>? = null
    var indexToCharTable: ArrayList<Char>? = null

    private fun initializeCharToIndexTable() {
        charToIndexTable = HashMap()
        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
        for (i in 0..25) {
            var c = 'a'
            c += i
            charToIndexTable!![c] = i
        }
        for (i in 26..51) {
            var c = 'A'
            c += (i - 26)
            charToIndexTable!![c] = i
        }
        for (i in 52..61) {
            var c = '0'
            c += (i - 52)
            charToIndexTable!![c] = i
        }
    }

    private fun initializeIndexToCharTable() {
        // 0->a, 1->b, ..., 25->z, ..., 52->0, 61->9
        indexToCharTable = ArrayList()
        for (i in 0..25) {
            var c = 'a'
            c += i
            indexToCharTable!!.add(c)
        }
        for (i in 26..51) {
            var c = 'A'
            c += (i - 26)
            indexToCharTable!!.add(c)
        }
        for (i in 52..61) {
            var c = '0'
            c += (i - 52)
            indexToCharTable!!.add(c)
        }
    }

    fun createUniqueID(id: Long): String {
        val base62ID = convertBase10ToBase62ID(id)
        val uniqueURLID = StringBuilder()
        for (digit in base62ID) {
            uniqueURLID.append(indexToCharTable?.get(digit))
        }
        return uniqueURLID.toString()
    }

    private fun convertBase10ToBase62ID(id: Long): List<Int> {
        var id = id
        val digits: List<Int> = LinkedList()
        while (id > 0) {
            val remainder = (id % 62).toInt()
            (digits as LinkedList<Int>).addFirst(remainder)
            id /= 62
        }
        return digits
    }


    fun getDictionaryKeyFromUniqueID(uniqueID: String): Long {
        val base62Number: ArrayList<Char> = ArrayList()
        for (element in uniqueID) {
            base62Number.add(element)
        }
        return convertBase62ToBase10ID(base62Number)
    }

    private fun convertBase62ToBase10ID(ids: List<Char>): Long {
        var id = 0.0
        val exp = ids.size - 1
        for (element in ids) {
            val base10 = charToIndexTable?.get(element)
            id += (base10!! * 62.0.pow(exp.toDouble()))
        }
        return id.toLong()
    }
}