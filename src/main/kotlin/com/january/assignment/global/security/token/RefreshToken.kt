package com.january.assignment.global.security.token

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.TimeToLive

@RedisHash(value = "refreshToken")
class RefreshToken private constructor(

    @field:Id
    val userId : Long,

    val uuid : String,

    @TimeToLive
    val tokenTTL : Long
) {

    companion object {
        fun createRefreshToken(userId: Long, uuid: String, tokenTTL: Long) : RefreshToken {
            return RefreshToken(userId, uuid, tokenTTL)
        }
    }
}
