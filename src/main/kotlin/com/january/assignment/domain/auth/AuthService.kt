package com.january.assignment.domain.auth

import com.january.assignment.domain.auth.dto.JoinRequest
import com.january.assignment.domain.auth.dto.JoinResponse
import com.january.assignment.domain.user.User
import com.january.assignment.domain.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {
    fun join(request: JoinRequest): JoinResponse {
        val email = request.email

        if (userRepository.existsByEmail(email))
            throw IllegalArgumentException("이미 사용 중인 이메일입니다!")

        val user = userRepository.save(encryptPassword(request))
        return JoinResponse(user.id!!)
    }

    private fun encryptPassword(request : JoinRequest) : User {
        val encryptedPassword = passwordEncoder.encode(request.password)
        return User(request.email, encryptedPassword, request.name)
    }
}
