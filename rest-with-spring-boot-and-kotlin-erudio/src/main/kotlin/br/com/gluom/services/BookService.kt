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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.stereotype.Service
import java.util.logging.Logger

@Service
class BookService {

    @Autowired
    private lateinit var booksRepository: BookRepository

    private val logger = Logger.getLogger(BookService::class.java.name)

    fun findAll(): List<BookVO> {
        logger.info("Book findAll")
        val books = booksRepository.findAll()

        val vos = DozerMapper.parseListObjects(books, BookVO::class.java)

        for(bookVO in vos) {
            val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
            bookVO.add(withSelfRel)
        }

        return vos
    }

    fun findById(id: Long): BookVO {
        logger.info("BookVO findById: $id")
        val book = booksRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No record found for this ID") }

        val bookVO = DozerMapper.parseObject(book, BookVO::class.java)
        val withSelfRel = linkTo(BookController::class.java).slash(bookVO.key).withSelfRel()
        bookVO.add(withSelfRel)
        return bookVO
    }

    fun create(bookVO: BookVO?) : BookVO {
        if(bookVO == null) throw RequiredObjectIsNullException()
        logger.info("Book create with name ${bookVO.title}")

        val book = DozerMapper.parseObject(bookVO, Book::class.java)
        val bookVOResponse = DozerMapper.parseObject(booksRepository.save(book), BookVO::class.java)

        val withSelfRel = linkTo(PersonController::class.java).slash(bookVOResponse.key).withSelfRel()
        bookVOResponse.add(withSelfRel)

        return bookVOResponse
    }

    fun update(bookVO: BookVO?) : BookVO {
        if(bookVO == null) throw RequiredObjectIsNullException()
        logger.info("Book update with name ${bookVO.title}")

        val book : Book = booksRepository.findById(bookVO.key)
            .orElseThrow { ResourceNotFoundException("No record found for this ID") }

        book.author = bookVO.author
        book.launchDate = bookVO.launchDate
        book.price = bookVO.price
        book.title = bookVO.title

        val bookVOResponse = DozerMapper.parseObject(booksRepository.save(book), BookVO::class.java)

        val withSelfRel = linkTo(BookController::class.java).slash(bookVOResponse.key).withSelfRel()
        bookVOResponse.add(withSelfRel)

        return bookVOResponse
    }

    fun delete(id: Long) {
        val book = booksRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("No record found for this ID") }
        logger.info("Deleted book with id ${book.id}")
        return booksRepository.delete(book)
    }

}