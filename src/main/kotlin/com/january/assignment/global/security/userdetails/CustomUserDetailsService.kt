package com.january.assignment.global.security.userdetails

import com.january.assignment.domain.user.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class CustomUserDetailsService(
    private val userRepository : UserRepository,
) : UserDetailsService {

    override fun loadUserByUsername(email: String): CustomUserDetails {
        val user = (userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("ID/PW를 다시 입력해주세요!"))

        return CustomUserDetails(user.id!!, user.email, user.name, user.role)
    }
}
