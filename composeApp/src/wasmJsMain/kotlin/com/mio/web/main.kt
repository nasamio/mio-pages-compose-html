package com.mio.web

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    // 必须挂载到 body，因为你的 HTML JS 监听的是 document.body 的节点变化
    val body = document.body ?: return

    ComposeViewport(body) {
        App()
    }
}