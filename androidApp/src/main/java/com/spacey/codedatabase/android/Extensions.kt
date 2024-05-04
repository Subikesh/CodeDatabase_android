package com.spacey.codedatabase.android

fun String.getRoute(vararg args: String): String {
    var route = this
    for (arg in args) {
        route = route.replaceFirst("\\{\\w+\\}".toRegex(), arg)
    }
    return route
}