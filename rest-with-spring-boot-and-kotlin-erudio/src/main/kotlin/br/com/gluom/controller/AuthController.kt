package br.com.gluom.controller

import br.com.gluom.data.vo.v1.AccountCredentialsVO
import br.com.gluom.services.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Authentication Endpoints")
@RestController
@RequestMapping("/auth")
class AuthController {

    @Autowired
    lateinit var authService: AuthService

    @Operation(summary = "Authenticate user", description = "Authenticate user and return a token JWT")
    @PostMapping(value = ["/signin"])
    fun signin(@RequestBody data: AccountCredentialsVO?) : ResponseEntity<*> {
        return if (data!!.username.isNullOrBlank() || data!!.password.isNullOrBlank() )
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid credentials")
            else authService.signin(data!!)
    }

    @Operation(summary = "Refresh token", description = "Refresh token and return a new token JWT")
    @PutMapping(value = ["/refresh/{username}"])
    fun refreshToken(@PathVariable("username") username: String?,
                     @RequestHeader("Authorization") refreshToken: String?) : ResponseEntity<*> {
        return if (username.isNullOrBlank() || refreshToken.isNullOrBlank() )
            ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Invalid credentials refresh Token")
            else authService.refreshToken(username, refreshToken)
    }
}