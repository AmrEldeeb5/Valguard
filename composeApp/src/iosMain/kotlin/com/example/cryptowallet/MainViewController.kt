package com.example.cryptowallet

import androidx.compose.ui.window.ComposeUIViewController
import com.example.cryptowallet.app.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) {
    App()
}
