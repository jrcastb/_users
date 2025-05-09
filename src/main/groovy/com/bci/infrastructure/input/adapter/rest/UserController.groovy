package com.bci.infrastructure.input.adapter.rest

import com.bci.application.service.UserService
import com.bci.domain.LoginResponse
import com.bci.domain.SignUpRequest
import com.bci.domain.SignUpResponse
import groovy.transform.Canonical
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
class UserController {

    private final UserService service

    @Autowired
    UserController(UserService service){
        this.service = service
    }

    @PostMapping("/sign-up")
    ResponseEntity<SignUpResponse> signUp(@RequestBody SignUpRequest request){
        log.info(request.toString())
        return ResponseEntity.status(HttpStatus.CREATED).body(service.signUp(request))
    }

    @GetMapping("/login")
    ResponseEntity<LoginResponse> login(@RequestParam String token){
        return ResponseEntity.ok().body(service.login(token))
    }

}
