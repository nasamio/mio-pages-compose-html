plugins {
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    // 注册序列化插件
    alias(libs.plugins.kotlinSerialization) apply false
}