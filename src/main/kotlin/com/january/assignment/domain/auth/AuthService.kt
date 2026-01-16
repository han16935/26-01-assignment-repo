package com.january.assignment.domain.auth

import com.january.assignment.domain.auth.dto.JoinRequest
import com.january.assignment.domain.auth.dto.JoinResponse
import com.january.assignment.domain.auth.dto.LoginRequest
import com.january.assignment.domain.auth.dto.LoginResponse
import com.january.assignment.domain.user.User
import com.january.assignment.domain.user.repository.UserRepository
import com.january.assignment.global.security.token.RefreshToken
import com.january.assignment.global.security.token.RefreshTokenRepository
import com.january.assignment.global.security.util.JwtUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class AuthService(
    @Value("\${spring.jwt.refresh-ttl-seconds}")
    private val refreshTokenTTL : Long,

    private val passwordEncoder: PasswordEncoder,
    private val jwtUtil: JwtUtil,
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
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

    fun login(request: LoginRequest): LoginResponse {
        // 1. 사용자 확인
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.")

        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("이메일 또는 비밀번호가 일치하지 않습니다.")
        }

        // 3. 토큰 생성 (UUID는 Refresh Token 식별용)
        val currentTime = Date()
        val accessToken = "Bearer " + jwtUtil.createAccessToken(user.id!!, user.email, user.role.role, currentTime)
        val refreshTokenUuid = UUID.randomUUID().toString()

        // 4. Redis에 RefreshToken 저장 (userId를 Key로 사용)
        val refreshToken = RefreshToken.createRefreshToken(
            userId = user.id!!,
            uuid = refreshTokenUuid,
            tokenTTL = refreshTokenTTL // JwtUtil에 정의된 TTL 값 사용
        )
        refreshTokenRepository.save(refreshToken)

        // 5. 응답 객체 반환 (쿠키에 담을 UUID 혹은 토큰값은 컨트롤러에서 처리)
        return LoginResponse(
            userId = user.id!!,
            accessToken = accessToken,
            refreshTokenValue = refreshTokenUuid // 컨트롤러 전달용 임시 필드 추가 권장
        )
    }
}
