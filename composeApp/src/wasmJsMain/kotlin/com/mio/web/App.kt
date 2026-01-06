package com.mio.web

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import org.jetbrains.compose.resources.Font

@Composable
fun App() {
    // 1. 初始化自定义字体
    val customFontFamily = FontFamily(
        Font(Res.font.font)
    )

    // 2. 配置 MaterialTheme 默认使用该字体
    val customTypography = Typography(
        bodyLarge = TextStyle(fontFamily = customFontFamily),
        bodyMedium = TextStyle(fontFamily = customFontFamily),
        labelLarge = TextStyle(fontFamily = customFontFamily),
        titleMedium = TextStyle(fontFamily = customFontFamily)
    )

    MaterialTheme(typography = customTypography) {
        var showContent by remember { mutableStateOf(false) }
        var dbData by remember { mutableStateOf("点击按钮加载数据库数据...") }
        val scope = rememberCoroutineScope()

        // 初始化 HttpClient
        val client = remember {
            HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        prettyPrint = true
                        ignoreUnknownKeys = true
                    })
                }
            }
        }

        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.primaryContainer)
                .safeContentPadding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // 此处的 Text 会自动应用上面 Typography 定义的自定义字体
            Text(
                text = "正在使用 font.ttf 字体",
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = {
                showContent = !showContent

                scope.launch {
                    try {
                        dbData = "正在请求..."
                        // 使用 Ktor 发起 GET 请求
                        val response: HttpResponse = client.get("/api/test")
                        val responseBody = response.bodyAsText()

                        dbData = "D1 返回成功: $responseBody"
                    } catch (e: Exception) {
                        dbData = "请求失败: ${e.message}"
                        e.printStackTrace()
                    }
                }
            }) {
                Text("Click me to fetch D1!")
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(text = dbData)

            AnimatedVisibility(showContent) {
                // 假设 Greeting 类在同一包或已导入
                val greeting = remember { "Hello!" }
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(Icons.Default.ShoppingCart, null)
                    Text("Compose Greeting: $greeting")
                }
            }
        }
    }
}
