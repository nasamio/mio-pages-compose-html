package com.mio.web

interface Platform {
    val name: String
}

 fun getPlatform(): Platform = object: Platform{
     override val name: String
         get() = "wasm"

 }