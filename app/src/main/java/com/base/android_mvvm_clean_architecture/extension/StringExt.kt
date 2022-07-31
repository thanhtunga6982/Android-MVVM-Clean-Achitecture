package com.base.android_mvvm_clean_architecture.extension


import java.util.regex.Pattern

val String.isEmail: Boolean
    get() {
        val p = Pattern.compile("^\\w+([+])*([.-]?\\w+([+])*)*@\\w+([+])*([.-]?\\w+([+])*)*(\\.\\w{2,3})+\$", Pattern.CASE_INSENSITIVE).toRegex()
        return matches(p)
    }