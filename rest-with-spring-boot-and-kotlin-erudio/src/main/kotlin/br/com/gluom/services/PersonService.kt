package br.com.gluom.services

import br.com.gluom.model.Person
import org.springframework.stereotype.Service
import java.util.concurrent.atomic.AtomicLong
import java.util.logging.Logger

@Service
class PersonService {

    private val counter: AtomicLong = AtomicLong()
    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<Person> {
        logger.info("Person findAll")

        val persons: MutableList<Person> = ArrayList()

        for(i in 0..7){
            val person = mockPerson(i)
            persons.add(person)
        }

        return persons
    }

    fun findById(id: Long): Person {
        logger.info("Person findById: $id")

        val person = Person()
        person.id = counter.incrementAndGet()
        person.firstName = "John"
        person.lastName = "Doe"
        person.address = "123 Main St."
        person.gender = "Male"
        return person
    }

    fun create(person: Person) : Person {
        logger.info("Person create")

        person.id = counter.incrementAndGet()
        return person
    }

    fun update(person: Person) : Person {
        logger.info("Person update")

        return person
    }

    fun delete(id: Long) {}

    private fun mockPerson(i: Int): Person {
        val person = Person()
        person.id = counter.incrementAndGet()
        person.firstName = "Person name $i"
        person.lastName = "last name $i"
        person.address = "123 Main St."
        person.gender = "Male"

        return person
    }

}