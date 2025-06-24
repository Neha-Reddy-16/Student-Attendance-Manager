package com.example.attendence.util

object AttendanceUtils {
    fun maxPossibleAttendance(attended: Int, total: Int, future: Int): Double {
        return ((attended + future).toDouble() / (total + future)) * 100
    }

    fun minRequiredAttendance(attended: Int, total: Int, future: Int, targetPercent: Double): Int {
        for (y in 0..future) {
            if (((attended + y).toDouble() / (total + future)) * 100 >= targetPercent) {
                return y
            }
        }
        return -1
    }
}