package com.january.assignment.global.security.userdetails

import com.january.assignment.domain.user.enum.Role
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(
    val id : Long,
    val email : String,
    val name : String,
    val role : Role,
) : UserDetails{

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(role.role))
    }

    override fun getPassword(): String {
        return ""
    }

    override fun getUsername(): String {
        return email
    }
}
