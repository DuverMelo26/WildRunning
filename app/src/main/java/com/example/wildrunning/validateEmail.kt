package com.example.wildrunning

import java.util.regex.Matcher
import java.util.regex.Pattern

class validateEmail {

    companion object{
        var pattern: Pattern ?= null
        var matcher: Matcher ?= null

        fun isEmail(Email: String): Boolean {
            pattern = Pattern.compile("^[\\w\\-\\_\\+]+(\\.[\\w\\-\\_]+)*@([A-Za-z0-9-]+\\.)+[A-Za-z]{2,4}$")
            matcher = pattern!!.matcher(Email)
            return matcher!!.find()
        }
    }

}