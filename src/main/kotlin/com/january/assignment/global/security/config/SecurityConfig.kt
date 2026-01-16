package com.january.assignment.global.security.config

import com.january.assignment.domain.user.enum.Role
import com.january.assignment.global.security.filter.JwtFilter
import com.january.assignment.global.security.util.JwtUtil
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfigurationSource

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtUtil: JwtUtil,
    private val corsConfigurationSource : CorsConfigurationSource
) {

    companion object {
        private val WHITE_LIST = arrayOf(
            "/health/**",
            "/actuator/**",
            "/swagger-ui/**",
            "/v3/api-docs/**",
            "/auth/login",
            "/auth/join",
            "/auth/reissue"
        )

        private val ADMIN_LIST = arrayOf(
            "/admin/feedback"
        )
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            httpBasic { disable() }
            formLogin { disable() }
            cors { configurationSource = corsConfigurationSource }
            csrf { disable() }
            sessionManagement { SessionCreationPolicy.STATELESS }
        }

        http {
            authorizeHttpRequests {

                authorize(HttpMethod.OPTIONS, "/**", permitAll)

                WHITE_LIST.forEach { path ->
                    authorize(path, permitAll)
                }

                ADMIN_LIST.forEach { path ->
                    authorize(path, hasAuthority(Role.ADMIN.role))
                }

                authorize(anyRequest, authenticated)
            }
        }

        http {
            addFilterBefore<UsernamePasswordAuthenticationFilter>(
                filter = JwtFilter(jwtUtil)
            )
        }

        return http.build()
    }
}
