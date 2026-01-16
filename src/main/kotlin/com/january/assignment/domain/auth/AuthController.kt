package com.january.assignment.domain.auth

import com.january.assignment.domain.auth.dto.JoinRequest
import com.january.assignment.domain.auth.dto.JoinResponse
import com.january.assignment.global.response.SuccessResponse
import jakarta.validation.Valid
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
    private val authService: AuthService
) {

    @PostMapping("/join")
    fun join(@Valid @RequestBody request : JoinRequest) : ResponseEntity<SuccessResponse<JoinResponse>> {
        return ResponseEntity.status(CREATED).body(SuccessResponse.created(authService.join(request)))
    }

}
