package com.january.assignment.domain.chat

import com.january.assignment.domain.user.User
import com.january.assignment.global.entity.BaseEntity
import jakarta.persistence.*

@Entity
class Chat (
    val question : String,
    val answer : String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val user : User
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
}
