package com.january.assignment.global.security.annotation

import org.springframework.security.core.annotation.AuthenticationPrincipal

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : this")
annotation class LoginUser()
