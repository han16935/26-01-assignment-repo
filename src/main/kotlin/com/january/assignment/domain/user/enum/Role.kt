package com.january.assignment.domain.user.enum

enum class Role (val role : String, val description : String) {
    ADMIN("ROLE_ADMIN", "관리자"), MEMBER("ROLE_MEMBER", "멤버")
}
