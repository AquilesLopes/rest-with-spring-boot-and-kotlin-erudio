package br.com.gluom.services

import br.com.gluom.exception.ResourceNotFoundException
import br.com.gluom.model.Person
import br.com.gluom.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var personRepository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<Person> {
        logger.info("Person findAll")
        return personRepository.findAll()
    }

    fun findById(id: Long): Person {
        logger.info("Person findById: $id")
        return personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No record found for this ID") }
    }

    fun create(person: Person) : Person {
        logger.info("Person create with name ${person.firstName}")

        return personRepository.save(person)
    }

    fun update(person: Person) : Person {
        logger.info("Person update with name ${person.firstName}")

        val personDB = findById(person.id)

        personDB.firstName = person.firstName
        personDB.lastName = person.lastName
        personDB.address = person.address
        personDB.gender = person.gender

        return personRepository.save(person)
    }

    fun delete(id: Long) {
        val person = findById(id)
        logger.info("Deleted person with id ${person.id}")
        return personRepository.delete(person)
    }


}