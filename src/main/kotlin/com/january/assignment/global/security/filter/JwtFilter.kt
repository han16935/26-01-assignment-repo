package com.january.assignment.global.security.filter

import com.january.assignment.global.security.util.JwtUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

class JwtFilter (
    private val jwtUtil: JwtUtil
) : OncePerRequestFilter() {

    companion object {
        private const val AUTHORIZATION = "Authorization"
        private const val BEARER = "Bearer"
        private val IGNORE_JWT_FILTER_PATH = listOf(
            "/auth/login",
            "/auth/refresh"
        )
    }

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (request.requestURI in IGNORE_JWT_FILTER_PATH) {
            return filterChain.doFilter(request, response)
        }

        resolveToken(request)?.let { token ->
            jwtUtil.checkExpireToken(token)
            jwtUtil.getAuthentication(token).also { authentication ->
                SecurityContextHolder.getContext().authentication = authentication
            }
        }
    }

    private fun resolveToken(request: HttpServletRequest): String? =
        request.getHeader(AUTHORIZATION)
            ?.takeIf { it.startsWith(BEARER) && StringUtils.hasText(it) }
            ?.substringAfter("$BEARER ")
}
