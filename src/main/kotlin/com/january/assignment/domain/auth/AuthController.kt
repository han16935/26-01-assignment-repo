package com.january.assignment.domain.auth

import com.january.assignment.domain.auth.dto.JoinRequest
import com.january.assignment.domain.auth.dto.JoinResponse
import com.january.assignment.domain.auth.dto.LoginRequest
import com.january.assignment.domain.auth.dto.LoginResponse
import com.january.assignment.global.response.SuccessResponse
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Valid
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    @Value("\${spring.jwt.refresh-ttl-seconds}")
    private val refreshTokenTTL : Long,
    private val authService: AuthService
) {

    @PostMapping("/join")
    fun join(@Valid @RequestBody request : JoinRequest) : ResponseEntity<SuccessResponse<JoinResponse>> {
        return ResponseEntity.status(CREATED).body(SuccessResponse.created(authService.join(request)))
    }

    @PostMapping("/login")
    fun login(@Valid @RequestBody request : LoginRequest, res : HttpServletResponse) : ResponseEntity<SuccessResponse<LoginResponse>> {
        val loginResponse = authService.login(request)

        res.setHeader("Authorization", "Bearer ${loginResponse.accessToken}")
        val cookie = Cookie("refreshToken", loginResponse.refreshTokenValue).apply {
            secure = false // HTTP 환경
            isHttpOnly = true
            maxAge = refreshTokenTTL.toInt()
        }
        res.addCookie(cookie)

        return ResponseEntity.status(OK).body(
            SuccessResponse.okWithNoData()
        )
    }
}
