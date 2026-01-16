package com.january.assignment.domain.user.repository

import com.january.assignment.domain.user.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email : String) : User?

    fun existsByEmail(email : String) : Boolean
}
