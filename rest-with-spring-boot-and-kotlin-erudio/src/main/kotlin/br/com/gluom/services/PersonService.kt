package br.com.gluom.services

import br.com.gluom.data.vo.v1.PersonVO
import br.com.gluom.exception.ResourceNotFoundException
import br.com.gluom.mapper.DozerMapper
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

    fun findAll(): List<PersonVO> {
        logger.info("PersonVO findAll")
        val people = personRepository.findAll()
        return DozerMapper.parseListObjects(people, PersonVO::class.java)
    }

    fun findById(id: Long): PersonVO {
        logger.info("PersonVO findById: $id")
        val person = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No record found for this ID") }
        return DozerMapper.parseObject(person, PersonVO::class.java)
    }

    fun create(personVO: PersonVO) : PersonVO {
        logger.info("PersonVO create with name ${personVO.firstName}")

        var person = DozerMapper.parseObject(personVO, Person::class.java)
        person = personRepository.save(person)

        return DozerMapper.parseObject(person, PersonVO::class.java)
    }

    fun update(personVO: PersonVO) : PersonVO {
        logger.info("PersonVO update with name ${personVO.firstName}")

        val person = personRepository.findById(personVO.id)
            .orElseThrow { ResourceNotFoundException("No record found for this ID") }

        person.firstName = personVO.firstName
        person.lastName = personVO.lastName
        person.address = personVO.address
        person.gender = personVO.gender

        return DozerMapper.parseObject(personRepository.save(person), PersonVO::class.java)
    }

    fun delete(id: Long) {
        val person = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No record found for this ID") }
        logger.info("Deleted person with id ${person.id}")
        return personRepository.delete(person)
    }

}