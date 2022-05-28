package com.heymaster.heymaster.model.masterprofile

import android.content.Context
import java.util.*
import kotlin.collections.ArrayList

sealed class Portfolio{

    val id: String = ""

    class Add(

    ): Portfolio()

    class Image(
        val name: String
    ): Portfolio()
}