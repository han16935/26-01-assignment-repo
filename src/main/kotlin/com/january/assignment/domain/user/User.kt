package com.january.assignment.domain.user

import com.january.assignment.domain.user.enum.Role
import com.january.assignment.global.entity.BaseEntity
import jakarta.persistence.*

@Entity(name = "users")
class User (
    val email : String,
    val password : String,
    val name : String,

    @Enumerated(value = EnumType.STRING)
    var role : Role = Role.MEMBER
) : BaseEntity(){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null


}
