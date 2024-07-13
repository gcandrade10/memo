package com.ger.memo

import com.ger.memo.viewmodel.Image

object ListTransformation {
    fun printIt(n: Int, m: Int, numbers: IntArray) {
        var numbIndex = 0
        val arr = Array(n) {
            IntArray(
                m
            )
        }
        var x = n / 2
        if (n % 2 == 0) {
            x = (n - 1) / 2
        }
        var y = m / 2
        if (m % 2 == 0) {
            y = (m - 1) / 2
        }
        var d = 0
        var s = 1
        var max = n
        if (m > n) {
            max = m
        }
        for (k in 1..max - 1) {
            for (j in 0 until if (k < max - 1) 2 else 3) {
                for (i in 0 until s) {
                    if (x < n && y < m && x > -1 && y > -1) {
                        if (numbIndex < numbers.size) {
                            arr[x][y] = numbers[numbIndex]
                            numbIndex++
                        } else {
                            arr[x][y] = -1
                        }
                    }
                    if (d == 0 && y < m) {
                        y = y + 1
                    } else if (d == 1) {
                        x = x + 1
                    } else if (d == 2) {
                        y = y - 1
                    } else if (d == 3) {
                        x = x - 1
                    }
                }
                d = (d + 1) % 4
            }
            s = s + 1
        }
        println("Circular Matrix Clockwise:")
        for (ii in 0 until n) {
            for (jj in 0 until m) {
                print(arr[ii][jj].toString() + "\t")
            }
            println()
        }
    }

    private fun matrixToList(arr: Array<Array<Image?>>): List<Image?> {
        val list = mutableListOf<Image?>()
        for (i in 0 until arr.size) {
            for (j in 0 until arr[0].size) {
                list.add(arr[i][j])
            }
        }
        return list
    }

    fun fillAndSort(list: List<Image>, n: Int = 8, m: Int = 5): List<Image?> {
        var numbIndex = 0
        val arr = Array(n) {
            arrayOfNulls<Image?>(m)
        }
        var x = n / 2
        if (n % 2 == 0) {
            x = (n - 1) / 2
        }
        var y = m / 2
        if (m % 2 == 0) {
            y = (m - 1) / 2
        }
        var d = 0
        var s = 1
        var max = n
        if (m > n) {
            max = m
        }
        for (k in 1..max - 1) {
            for (j in 0 until if (k < max - 1) 2 else 3) {
                for (i in 0 until s) {
                    if (x < n && y < m && x > -1 && y > -1) {
                        if (numbIndex < list.size) {
                            arr[x][y] = list[numbIndex]
                            numbIndex++
                        } else {
                            arr[x][y] = null
                        }
                    }
                    if (d == 0 && y < m) {
                        y = y + 1
                    } else if (d == 1) {
                        x = x + 1
                    } else if (d == 2) {
                        y = y - 1
                    } else if (d == 3) {
                        x = x - 1
                    }
                }
                d = (d + 1) % 4
            }
            s = s + 1
        }
        return matrixToList(arr)
    }

}