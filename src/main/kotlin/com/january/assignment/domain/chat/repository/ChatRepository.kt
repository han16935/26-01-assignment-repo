package com.january.assignment.domain.chat.repository

import com.january.assignment.domain.chat.Chat
import org.springframework.data.jpa.repository.JpaRepository

interface ChatRepository : JpaRepository<Chat, Long>{
}
