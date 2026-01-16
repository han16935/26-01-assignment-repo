package com.january.assignment.global.security.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig {

    @Bean("corsConfigurationSource")
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = listOf(
                "developOrigin",
                "productOrigin",
                "http://localhost:3000"
            )
            allowedMethods = listOf("OPTIONS", "HEAD", "GET", "POST", "PUT", "PATCH", "DELETE")
            allowedHeaders = listOf("*")
            allowCredentials = true
            maxAge = 3000
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }
}
