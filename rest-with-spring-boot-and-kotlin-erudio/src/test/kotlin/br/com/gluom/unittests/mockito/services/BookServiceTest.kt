package br.com.gluom.unittests.mockito.services

import br.com.gluom.exception.RequiredObjectIsNullException
import br.com.gluom.repository.BookRepository
import br.com.gluom.services.BookService
import br.com.gluom.unittests.mocks.MockBook
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class BookServiceTest {

    private lateinit var inputObject: MockBook

    @InjectMocks
    private lateinit var service: BookService

    @Mock
    private lateinit var repository: BookRepository

    @BeforeEach
    fun setUpMock() {
        inputObject = MockBook()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findAll() {
        val bookMock = inputObject.mockEntityList()

        `when`(repository.findAll()).thenReturn(bookMock)

        val books = service.findAll()

        assertNotNull(books)
        assertEquals(14, books.size)

        val bookOne = books[1]
        assertNotNull(bookOne)
        assertNotNull(bookOne.key)
        assertNotNull(bookOne.links)
        assertTrue(bookOne.links.toString().contains("/api/v1/book/1"))
        assertEquals("Title Test1", bookOne.title)
        assertEquals(1.5, bookOne.price)
        assertEquals("Machado de Assis", bookOne.author)

        val bookFour = books[4]
        assertNotNull(bookFour)
        assertNotNull(bookFour.key)
        assertNotNull(bookFour.links)
        assertTrue(bookFour.links.toString().contains("/api/v1/book/4"))
        assertEquals("Title Test4", bookFour.title)
        assertEquals(6.0, bookFour.price)
        assertEquals("Cora Coralina", bookFour.author)

        val bookSeven = books[7]
        assertNotNull(bookSeven)
        assertNotNull(bookSeven.key)
        assertNotNull(bookSeven.links)
        assertTrue(bookSeven.links.toString().contains("/api/v1/book/7"))
        assertEquals("Title Test7", bookSeven.title)
        assertEquals(10.5, bookSeven.price)
        assertEquals("Machado de Assis", bookSeven.author)
    }

    @Test
    fun findById() {
        val book = inputObject.mockEntity(1)
        book.id = 1L
        `when`(repository.findById(1L)).thenReturn(Optional.of(book))

        val bookVO = service.findById(1L)

        assertNotNull(bookVO)
        assertNotNull(bookVO.key)
        assertNotNull(bookVO.links)
        assertTrue(bookVO.links.toString().contains("/api/v1/book/1"))
        assertEquals("Title Test1", bookVO.title)
        assertEquals(1.5, bookVO.price)
        assertEquals("Machado de Assis", bookVO.author)
    }

    @Test
    fun createWithNullBook() {
        val exception: Exception = assertThrows(
            RequiredObjectIsNullException::class.java
        ) {service.create(null)}

        val exceptionMessage = "It is not allowed to persist a null object"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(exceptionMessage))
    }

    @Test
    fun create() {

    }

    @Test
    fun updateWithNullBook() {
        val exception: Exception = assertThrows(
            RequiredObjectIsNullException::class.java
        ) {service.update(null)}

        val exceptionMessage = "It is not allowed to persist a null object"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(exceptionMessage))
    }

    @Test
    fun update() {
        val book = inputObject.mockEntity(1)

        val bookDB = book.copy()
        bookDB.id = 1L

        `when`(repository.findById(1L)).thenReturn(Optional.of(book))
        `when`(repository.save(book)).thenReturn(bookDB)

        val bookVO = inputObject.mockVO(1)
        val result = service.update(bookVO)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("/api/v1/book/1"))
        assertEquals("Title Test1", result.title)
        assertEquals(1.5, result.price)
        assertEquals("Machado de Assis", result.author)
    }

    @Test
    fun delete() {
        val book = inputObject.mockEntity(1)

        `when`(repository.findById(1L)).thenReturn(Optional.of(book))

        service.delete(1L)
    }
}