package com.january.assignment.domain.auth

import com.january.assignment.domain.auth.dto.JoinRequest
import com.january.assignment.domain.auth.dto.JoinResponse
import com.january.assignment.global.response.SuccessResponse
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    fun join(request : JoinRequest) : ResponseEntity<SuccessResponse<JoinResponse>> {
        return ResponseEntity.status(CREATED).body(SuccessResponse.created(authService.join(request)))
    }
}
