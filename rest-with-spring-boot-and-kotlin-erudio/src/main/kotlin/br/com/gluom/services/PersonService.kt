package br.com.gluom.services

import br.com.gluom.controller.PersonController
import br.com.gluom.data.vo.v1.PersonVO
import br.com.gluom.exception.RequiredObjectIsNullException
import br.com.gluom.exception.ResourceNotFoundException
import br.com.gluom.mapper.DozerMapper
import br.com.gluom.model.Person
import br.com.gluom.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var personRepository: PersonRepository

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(): List<PersonVO> {
        logger.info("PersonVO findAll")
        val people = personRepository.findAll()

        val vos = DozerMapper.parseListObjects(people, PersonVO::class.java)

        for(personVO in vos) {
            val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
            personVO.add(withSelfRel)
        }

        return vos
    }

    fun findById(id: Long): PersonVO {
        logger.info("PersonVO findById: $id")
        val person = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No record found for this ID") }

        val personVO = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun create(personVO: PersonVO?) : PersonVO {
        if(personVO == null) throw RequiredObjectIsNullException()
        logger.info("PersonVO create with name ${personVO.firstName}")

        var person = DozerMapper.parseObject(personVO, Person::class.java)
        person = personRepository.save(person)

        val personVOResponse = DozerMapper.parseObject(person, PersonVO::class.java)

        val withSelfRel = linkTo(PersonController::class.java).slash(personVOResponse.key).withSelfRel()
        personVOResponse.add(withSelfRel)

        return personVOResponse
    }

    fun update(personVO: PersonVO?) : PersonVO {
        if(personVO == null) throw RequiredObjectIsNullException()
        logger.info("PersonVO update with name ${personVO.firstName}")

        val person = personRepository.findById(personVO.key)
            .orElseThrow { ResourceNotFoundException("No record found for this ID") }

        person.firstName = personVO.firstName
        person.lastName = personVO.lastName
        person.address = personVO.address
        person.gender = personVO.gender

        val personVOResponse = DozerMapper.parseObject(personRepository.save(person), PersonVO::class.java)

        val withSelfRel = linkTo(PersonController::class.java).slash(personVOResponse.key).withSelfRel()
        personVOResponse.add(withSelfRel)

        return personVOResponse
    }


    @Transactional
    fun disablePerson(id: Long): PersonVO {
        logger.info("Disabling one person findById: $id")

        var person = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No record found for this ID") }

        person.enabled = false

        personRepository.disablePerson(person.id)

        val personVO = DozerMapper.parseObject(person, PersonVO::class.java)
        val withSelfRel = linkTo(PersonController::class.java).slash(personVO.key).withSelfRel()
        personVO.add(withSelfRel)
        return personVO
    }

    fun delete(id: Long) {
        val person = personRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No record found for this ID") }
        logger.info("Deleted person with id ${person.id}")
        return personRepository.delete(person)
    }

}