package com.zj.dailydemo

import org.junit.Test

class TestDominoPairs {
    @Test
    fun numEquivDominoPairs() {
        val dominoes = arrayOf(IntArray(2).apply {
            this[0] = 1
            this[1] = 0
        }, IntArray(2).apply {
            this[0] = 4
            this[1] = 3
        }, IntArray(2).apply {
            this[0] = 3
            this[1] = 4
        }, IntArray(2).apply {
            this[0] = 4
            this[1] = 5
        })
        var count = 0
        val reverseArr = dominoes.copyOf()
        reverseArr.reverse()
        loop@ for (i in dominoes.indices) {
            for (j in reverseArr.indices) {
                if (i + j == reverseArr.size - 1) {
                    continue@loop
                } else {
                    if (checkArr(dominoes[i], reverseArr[j])) {
                        count++
                    } else {
                        val intArr = reverseArr[j].copyOf()
                        intArr.reverse()
                        if (checkArr(dominoes[i], intArr)) {
                            count++
                        }
                    }
                }
            }
        }
        println(count)
//        return count
    }

    private fun checkArr(arr1: IntArray, arr2: IntArray): Boolean {
        if (arr1.size == arr2.size) {
            for (i in arr1.indices) {
                if (arr1[i] != arr2[i]) {
                    return false
                }
            }
            return true
        }
        return false
    }
}