package com.luqman.weather.core.network.exception

class ImplementationShouldBeNotCalledException: Exception() {
    override val message: String
        get() = "Implementation should be not called exception"
}