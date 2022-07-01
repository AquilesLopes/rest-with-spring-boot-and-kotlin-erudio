package br.com.gluom.controller

import br.com.gluom.data.vo.v1.BookVO
import br.com.gluom.exception.ExceptionResponse
import br.com.gluom.services.BookService
import br.com.gluom.util.MediaType
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/book")
@Tag(name = "Book", description = "Endpoints for Managing books")
class BookController {

    @Autowired
    private lateinit var bookService: BookService

    @GetMapping(produces=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Find all books", description = "Find all books",
        tags = ["Book"],
        responses = [
            ApiResponse(responseCode = "200", description = "Success", content = [
                Content(array = ArraySchema(schema = Schema(implementation = BookVO::class)))
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
    fun findAll(): List<BookVO> {
        return bookService.findAll()
    }

    @GetMapping(value=["/{id}"], produces=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Find a Book", description = "Find a Book by id",
        tags = ["Book"],
        responses = [
            ApiResponse(responseCode = "200", description = "Success", content = [
                Content(schema = Schema(implementation = BookVO::class))
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
    fun findById(@PathVariable(value="id") id: Long): BookVO {
        return bookService.findById(id)
    }

    @PostMapping(consumes=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
                 produces=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Add a new Book", description = "Add a new Book",
        tags = ["Book"],
        responses = [
            ApiResponse(responseCode = "200", description = "Success", content = [
                Content(schema = Schema(implementation = BookVO::class))
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
    fun create(@RequestBody bookVO: BookVO): BookVO {
        return bookService.create(bookVO)
    }

    @PutMapping(consumes=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML],
                produces=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Update a Book", description = "Update a Book",
        tags = ["Book"],
        responses = [
            ApiResponse(responseCode = "200", description = "Success", content = [
                Content(schema = Schema(implementation = BookVO::class))
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
    fun update(@RequestBody bookVO: BookVO): BookVO {
        return bookService.update(bookVO)
    }

    @DeleteMapping(value=["/{id}"], produces=[MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML])
    @Operation(summary = "Delete a Book", description = "Delete a Book",
        tags = ["Book"],
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
        bookService.delete(id)
        return ResponseEntity.noContent().build<Any>()
    }
}