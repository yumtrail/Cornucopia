package com.appkitchen.cornucopia

fun Collection<String>.containsIgnoreCase(item: String) = any {
    it.equals(item, ignoreCase = true)
}