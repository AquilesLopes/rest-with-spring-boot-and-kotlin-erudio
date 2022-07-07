package br.com.gluom.services

import br.com.gluom.controller.BookController
import br.com.gluom.controller.PersonController
import br.com.gluom.data.vo.v1.BookVO
import br.com.gluom.data.vo.v1.PersonVO
import br.com.gluom.exception.RequiredObjectIsNullException
import br.com.gluom.exception.ResourceNotFoundException
import br.com.gluom.mapper.DozerMapper
import br.com.gluom.model.Book
import br.com.gluom.model.Person
import br.com.gluom.repository.BookRepository
import br.com.gluom.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class UserService(@field:Autowired var userRepository: UserRepository) : UserDetailsService{

    private val logger = Logger.getLogger(UserService::class.java.name)

    override fun loadUserByUsername(username: String?): UserDetails {
        logger.info("Finding one User by userName: $username")

        val user = userRepository.findByUsername(username)

        return user ?: throw UsernameNotFoundException("User not found for username: $username")
    }

}