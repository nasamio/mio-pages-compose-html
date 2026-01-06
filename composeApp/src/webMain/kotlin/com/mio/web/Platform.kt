package com.mio.web

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform