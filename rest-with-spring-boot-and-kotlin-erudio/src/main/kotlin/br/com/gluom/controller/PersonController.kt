package br.com.gluom.controller

import br.com.gluom.data.vo.v1.PersonVO
import br.com.gluom.services.PersonService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/person")
class PersonController {

    @Autowired
    private lateinit var personService: PersonService

    @GetMapping(produces=[MediaType.APPLICATION_JSON_VALUE])
    fun findAll(): List<PersonVO> {
        return personService.findAll()
    }

    @GetMapping(value=["/{id}"], produces=[MediaType.APPLICATION_JSON_VALUE])
    fun findById(@PathVariable(value="id") id: Long): PersonVO {
        return personService.findById(id)
    }

    @PostMapping(consumes=[MediaType.APPLICATION_JSON_VALUE],
                 produces=[MediaType.APPLICATION_JSON_VALUE])
    fun create(@RequestBody person: PersonVO): PersonVO {
        return personService.create(person)
    }

    @PutMapping(consumes=[MediaType.APPLICATION_JSON_VALUE],
                produces=[MediaType.APPLICATION_JSON_VALUE])
    fun update(@RequestBody person: PersonVO): PersonVO {
        return personService.update(person)
    }

    @DeleteMapping(value=["/{id}"], produces=[MediaType.APPLICATION_JSON_VALUE])
    fun delete(@PathVariable(value="id") id: Long) : ResponseEntity<*> {
        personService.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }
}