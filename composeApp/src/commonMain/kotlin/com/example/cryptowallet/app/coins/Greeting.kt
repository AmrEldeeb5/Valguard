package com.example.cryptowallet.app.coins

import com.example.cryptowallet.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}