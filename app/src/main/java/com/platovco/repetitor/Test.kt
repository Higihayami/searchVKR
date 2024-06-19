package com.platovco.repetitor

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.io.BufferedReader
import java.io.InputStreamReader

fun main(){

}

data class Tutor(
    val birthday: String = "",
    val city: String = "",
    val direction: String = "",
    val education: String = "",
    val name: String = "",
    val photoUrl: String = "",
    val price: String = "",
    val reason: String = "",
    val surname: String = "",
    val uuid: String = ""
)