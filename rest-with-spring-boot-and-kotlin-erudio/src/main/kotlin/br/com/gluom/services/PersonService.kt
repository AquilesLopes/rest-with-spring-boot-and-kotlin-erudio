package br.com.gluom.services

import br.com.gluom.controller.PersonController
import br.com.gluom.data.vo.v1.PersonVO
import br.com.gluom.exception.RequiredObjectIsNullException
import br.com.gluom.exception.ResourceNotFoundException
import br.com.gluom.mapper.DozerMapper
import br.com.gluom.model.Person
import br.com.gluom.repository.PersonRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PagedResourcesAssembler
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.logging.Logger

@Service
class PersonService {

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var assembler: PagedResourcesAssembler<PersonVO>

    private val logger = Logger.getLogger(PersonService::class.java.name)

    fun findAll(pageable : Pageable): PagedModel<EntityModel<PersonVO>> {
        logger.info("PersonVO findAll")
        val people = personRepository.findAll(pageable)

        val vos = pagePersonToPageVOs(people)

        return assembler.toModel(vos)
    }

    fun findPersonByName(firstName: String, pageable : Pageable): PagedModel<EntityModel<PersonVO>> {
        logger.info("PersonVO ByName")
        val people = personRepository.findPersonByName(firstName, pageable)

        val vos = pagePersonToPageVOs(people)

        return assembler.toModel(vos)
    }
    
    fun pagePersonToPageVOs(page: Page<Person>): Page<PersonVO> {
        val vos = page.map { p -> DozerMapper.parseObject(p, PersonVO::class.java) }
        vos.map { p -> p.add(linkTo(PersonController::class.java).slash(p.key).withSelfRel()) }
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