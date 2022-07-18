package br.com.gluom.controller

import br.com.gluom.data.vo.v1.PersonVO
import br.com.gluom.exception.ExceptionResponse
import br.com.gluom.services.PersonService
import br.com.gluom.util.MediaType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.PagedModel
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

//@CrossOrigin
@RestController
@RequestMapping("/api/v1/person")
@Tag(name = "Person", description = "Endpoints for Managing person")
class PersonController {

    @Autowired
    private lateinit var personService: PersonService

    @GetMapping(produces=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Find all people", description = "Find all people",
        tags = ["Person"],
        responses = [
            ApiResponse(responseCode = "200", description = "Success", content = [
                Content(array = ArraySchema(schema = Schema(implementation = PersonVO::class)))
            ]),
            ApiResponse(responseCode = "204", description = "No Content", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "404", description = "Not Found", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "500", description = "Internal Error", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
        ]
    )
    fun findAll(
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "12") size: Int,
        @RequestParam(value = "direction", defaultValue = "asc") direction: String
    ): ResponseEntity<PagedModel<EntityModel<PersonVO>>> {
        val sortDirection : Sort.Direction = if ("desc".equals(direction, ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        val pageable : Pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"))
        return ResponseEntity.ok(personService.findAll(pageable))
    }

    @GetMapping(value=["/findByName/{fisrtName}"], produces=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Find all people", description = "Find all people",
        tags = ["Person"],
        responses = [
            ApiResponse(responseCode = "200", description = "Success", content = [
                Content(array = ArraySchema(schema = Schema(implementation = PersonVO::class)))
            ]),
            ApiResponse(responseCode = "204", description = "No Content", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "404", description = "Not Found", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "500", description = "Internal Error", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
        ]
    )
    fun findByName(
        @PathVariable(value = "fisrtName") fisrtName: String,
        @RequestParam(value = "page", defaultValue = "0") page: Int,
        @RequestParam(value = "size", defaultValue = "12") size: Int,
        @RequestParam(value = "direction", defaultValue = "asc") direction: String
    ): ResponseEntity<PagedModel<EntityModel<PersonVO>>> {
        val sortDirection : Sort.Direction = if ("desc".equals(direction, ignoreCase = true)) Sort.Direction.DESC else Sort.Direction.ASC
        val pageable : Pageable = PageRequest.of(page, size, Sort.by(sortDirection, "firstName"))
        return ResponseEntity.ok(personService.findPersonByName(fisrtName, pageable))
    }

    //@CrossOrigin(origins = ["http://localhost:8080"])
    @GetMapping(value=["/{id}"], produces=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Find a Person", description = "Find a Person by id",
        tags = ["Person"],
        responses = [
            ApiResponse(responseCode = "200", description = "Success", content = [
                Content(schema = Schema(implementation = PersonVO::class))
            ]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "404", description = "Not Found", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "500", description = "Internal Error", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
        ]
    )
    fun findById(@PathVariable(value="id") id: Long): PersonVO {
        return personService.findById(id)
    }

    @PostMapping(consumes=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
                 produces=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Add a new Person", description = "Add a new Person",
        tags = ["Person"],
        responses = [
            ApiResponse(responseCode = "200", description = "Success", content = [
                Content(schema = Schema(implementation = PersonVO::class))
            ]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "404", description = "Not Found", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "500", description = "Internal Error", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
        ]
    )
    fun create(@RequestBody person: PersonVO): PersonVO {
        return personService.create(person)
    }

    @PutMapping(consumes=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
                produces=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Update a Person", description = "Update a Person",
        tags = ["Person"],
        responses = [
            ApiResponse(responseCode = "200", description = "Success", content = [
                Content(schema = Schema(implementation = PersonVO::class))
            ]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "404", description = "Not Found", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "500", description = "Internal Error", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
        ]
    )
    fun update(@RequestBody person: PersonVO): PersonVO {
        return personService.update(person)
    }


    @PatchMapping(value=["/disable/{id}"], produces=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Disable a Person", description = "Disable a Person by id",
        tags = ["Person"],
        responses = [
            ApiResponse(responseCode = "200", description = "Success", content = [
                Content(schema = Schema(implementation = PersonVO::class))
            ]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "404", description = "Not Found", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "500", description = "Internal Error", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
        ]
    )
    fun disablePerson(@PathVariable(value="id") id: Long): PersonVO {
        return personService.disablePerson(id)
    }


    @DeleteMapping(value=["/{id}"], produces=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Delete a Person", description = "Delete a Person",
        tags = ["Person"],
        responses = [
            ApiResponse(responseCode = "204", description = "No Content", content = [
                Content(schema = Schema(implementation = Unit::class))
            ]),
            ApiResponse(responseCode = "400", description = "Bad Request", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "401", description = "Unauthorized", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "404", description = "Not Found", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
            ApiResponse(responseCode = "500", description = "Internal Error", content = [
                Content(schema = Schema(implementation = ExceptionResponse::class))
            ]),
        ]
    )
    fun delete(@PathVariable(value="id") id: Long) : ResponseEntity<*> {
        personService.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }
}