package com.zj.dailydemo

import org.junit.Test

class TestAdd {
    @Test
    fun testAdd() {
//        println(longestPalindrome("abbaccccccadaaccc"))
        println(twoSum(intArrayOf(3, 3), 6)[0])
        println(twoSum(intArrayOf(3, 3), 6)[1])
    }

    private fun longestPalindrome(s: String): String {
        if (s.isEmpty()) {
            return s
        }
        val charArr = s.toCharArray()
        var maxStart = 0
        var maxEnd = 0
        var maxCount = 0
        for (i in charArr.indices) {
            var childEnd = i
            var childCount = 0
            println(" i is $i")
            for (j in i + 1 until charArr.size) {
                if (charArr[j] == charArr[i]) {
                    childEnd = j
                    childCount = j - i
                }
            }
            if (maxCount < childCount) {
                maxCount = childCount
                maxStart = i
                maxEnd = childEnd
            }
        }
        return s.substring(maxStart, maxEnd + 1)
    }

    private fun twoSum(nums: IntArray, target: Int): IntArray {
        if (nums.size < 2) {
            return IntArray(0)
        }
        for (i in nums.indices) {
            for (j in i + 1 until nums.size) {
                if (nums[i] + nums[j] == target) {
                    return intArrayOf(i, j)
                }
            }
        }
        return IntArray(0)
    }
}