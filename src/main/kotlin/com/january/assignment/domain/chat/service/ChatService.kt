package com.january.assignment.domain.chat.service

import com.january.assignment.domain.chat.Chat
import com.january.assignment.domain.chat.dto.ChatRequest
import com.january.assignment.domain.chat.dto.ChatResponse
import com.january.assignment.domain.chat.repository.ChatRepository
import com.january.assignment.domain.user.repository.UserRepository
import com.january.assignment.global.security.userdetails.CustomUserDetails
import org.springframework.ai.chat.client.ChatClient
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ChatService(
    private val chatClient: ChatClient,
    private val chatRepository : ChatRepository,
    private val userRepository: UserRepository
) {

    fun ask(request: ChatRequest, user: CustomUserDetails): ChatResponse {
        val loginUser = userRepository.findByIdOrNull(user.id)
            ?: throw IllegalArgumentException("사용자가 존재하지 않습니다!")

        val answer = askToAI(request)
        val chat = Chat(
            question = request.question,
            answer = answer,
            user = loginUser
        )
        chatRepository.save(chat)

        return ChatResponse(request.question, answer)
    }

    private fun askToAI(request: ChatRequest): String {
        val answer = chatClient.prompt()
            .user(request.question)
            .call()
            .content()

        return answer ?: "답변이 도착하지 않았습니다..."
    }
}
