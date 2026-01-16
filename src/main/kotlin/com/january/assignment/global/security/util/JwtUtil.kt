package com.january.assignment.global.security.util

import com.january.assignment.global.security.userdetails.CustomUserDetails
import com.january.assignment.global.security.userdetails.CustomUserDetailsService
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.time.Duration
import java.util.*
import javax.crypto.SecretKey

@Component
class JwtUtil(
    @Value("\${spring.jwt.secret}") private val secret: String,
    @Value("\${spring.jwt.access-ttl-seconds}") private val accessTokenTTL : Long,
    private val userDetailsService: CustomUserDetailsService
) {

    private val key: SecretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))

    fun createAccessToken(id: Long, email: String, role: String, currentTime: Date): String {
        validateUserInfoAndCurrentTime(email, role, currentTime)

        return Jwts.builder()
            .claim("id", id)
            .claim("tokenType", "access")
            .claim("username", email)
            .claim("role", role)
            .issuedAt(currentTime)
            .expiration(expireFrom(currentTime, Duration.ofHours(accessTokenTTL)))
            .signWith(key)
            .compact()
    }

    fun getId(token: String): Long =
        extractPayload(token).get("id", Long::class.java)

    fun getUserId(token: String): String =
        extractPayload(token).get("username", String::class.java)

    fun getRole(token: String): String =
        extractPayload(token).get("role", String::class.java)

    fun getTokenType(token: String): String =
        extractPayload(token).get("tokenType", String::class.java)

    fun isExpired(token: String, currentTime: Date): Boolean {
        val expiration = extractPayload(token).expiration
        return expiration.before(currentTime)
    }

    private fun extractPayload(token: String): Claims {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
    }

    private fun expireFrom(currentTime: Date, duration: Duration): Date {
        val instant = currentTime.toInstant()
        return Date.from(instant.plus(duration))
    }

    private fun validateUserInfoAndCurrentTime(username: String, role: String, currentTime: Date?) {
        require(username.isNotBlank()) { "username이 존재하지 않습니다!" }
        require(role.isNotBlank()) { "role이 존재하지 않습니다!" }
        requireNotNull(currentTime) { "currentTime이 존재하지 않습니다!" }
        require(currentTime.time > 0) { "currentTime이 잘못되었습니다!" }
    }

    fun checkExpireToken(token: String) {
        extractPayload(token)
    }

    fun getAuthentication(token: String): Authentication {
        val claims = extractPayload(token)
        val authorities = listOf(SimpleGrantedAuthority(claims["role"].toString()))
        return UsernamePasswordAuthenticationToken(getUserDetails(claims), "", authorities)
    }

    private fun getUserDetails(claims : Claims) : CustomUserDetails {
        val email = claims["username"].toString()
        return userDetailsService.loadUserByUsername(email)
    }
}
