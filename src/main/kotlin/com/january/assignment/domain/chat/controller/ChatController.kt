package com.january.assignment.domain.chat.controller

import com.january.assignment.domain.chat.dto.ChatRequest
import com.january.assignment.domain.chat.dto.ChatResponse
import com.january.assignment.domain.chat.service.ChatService
import com.january.assignment.global.response.SuccessResponse
import com.january.assignment.global.security.annotation.LoginUser
import com.january.assignment.global.security.userdetails.CustomUserDetails
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/chat")
class ChatController(
    private val chatService : ChatService
) {

    @PostMapping
    fun ask(@Valid @RequestBody request : ChatRequest, @LoginUser user : CustomUserDetails) : ResponseEntity<SuccessResponse<ChatResponse>> {
        return ResponseEntity.status(HttpStatus.CREATED).body(SuccessResponse.created(chatService.ask(request, user)))
    }
}
