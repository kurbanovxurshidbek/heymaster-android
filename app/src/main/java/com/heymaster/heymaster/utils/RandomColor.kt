package com.heymaster.heymaster.utils

import com.heymaster.heymaster.R
import java.util.*
import kotlin.collections.ArrayList


object RandomColor {

    fun randomColor(): Int {
        val random = Random()
        val colorList = ArrayList<Int>()

        colorList.add(R.color.random1)
        colorList.add(R.color.random2)
        colorList.add(R.color.random3)
        colorList.add(R.color.random4)

        return colorList[random.nextInt(colorList.size)]
    }
}