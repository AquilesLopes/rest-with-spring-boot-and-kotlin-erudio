package br.com.gluom.unittests.mockito.services

import br.com.gluom.exception.RequiredObjectIsNullException
import br.com.gluom.model.Person
import br.com.gluom.repository.PersonRepository
import br.com.gluom.services.PersonService
import br.com.gluom.unittests.mocks.MockPerson
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
internal class PersonServiceTest {

    private lateinit var inputObject: MockPerson

    @InjectMocks
    private lateinit var service: PersonService

    @Mock
    private lateinit var repository: PersonRepository

    @BeforeEach
    fun setUpMock() {
        inputObject = MockPerson()
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun findAll() {
        val peopleMock = inputObject.mockEntityList()

        `when`(repository.findAll()).thenReturn(peopleMock)

        val people = service.findAll()

        assertNotNull(people)
        assertEquals(14, people.size)

        val personOne = people[1]
        assertNotNull(personOne)
        assertNotNull(personOne.key)
        assertNotNull(personOne.links)
        assertTrue(personOne.links.toString().contains("/api/v1/person/1"))
        assertEquals("Address Test1", personOne.address)
        assertEquals("First Name Test1", personOne.firstName)
        assertEquals("Last Name Test1", personOne.lastName)
        assertEquals("Female", personOne.gender)

        val personFour = people[4]
        assertNotNull(personFour)
        assertNotNull(personFour.key)
        assertNotNull(personFour.links)
        assertTrue(personFour.links.toString().contains("/api/v1/person/4"))
        assertEquals("Address Test4", personFour.address)
        assertEquals("First Name Test4", personFour.firstName)
        assertEquals("Last Name Test4", personFour.lastName)
        assertEquals("Male", personFour.gender)

        val personSeven = people[7]
        assertNotNull(personSeven)
        assertNotNull(personSeven.key)
        assertNotNull(personSeven.links)
        assertTrue(personSeven.links.toString().contains("/api/v1/person/7"))
        assertEquals("Address Test7", personSeven.address)
        assertEquals("First Name Test7", personSeven.firstName)
        assertEquals("Last Name Test7", personSeven.lastName)
        assertEquals("Female", personSeven.gender)
    }

    @Test
    fun findById() {
        val person = inputObject.mockEntity(1)
        person.id = 1L
        `when`(repository.findById(1L)).thenReturn(Optional.of(person))

        val result = service.findById(1L)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("/api/v1/person/1"))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    @Test
    fun createWithNullPerson() {
        val exception: Exception = assertThrows(
            RequiredObjectIsNullException::class.java
        ) {service.create(null)}

        val exceptionMessage = "It is not allowed to persist a null object"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(exceptionMessage))
    }

    @Test
    fun create() {
        val person = inputObject.mockEntity(1)

        val personDB = person.copy()
        personDB.id = 1L

        `when`(repository.save(person)).thenReturn(personDB)

        val personVO = inputObject.mockVO(1)
        val result = service.create(personVO)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("/api/v1/person/1"))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    @Test
    fun updateWithNullPerson() {
        val exception: Exception = assertThrows(
            RequiredObjectIsNullException::class.java
        ) {service.update(null)}

        val exceptionMessage = "It is not allowed to persist a null object"
        val actualMessage = exception.message

        assertTrue(actualMessage!!.contains(exceptionMessage))
    }

    @Test
    fun update() {
        val person = inputObject.mockEntity(1)

        val personDB = person.copy()
        personDB.id = 1L

        `when`(repository.findById(1L)).thenReturn(Optional.of(person))
        `when`(repository.save(person)).thenReturn(personDB)

        val personVO = inputObject.mockVO(1)
        val result = service.update(personVO)

        assertNotNull(result)
        assertNotNull(result.key)
        assertNotNull(result.links)
        assertTrue(result.links.toString().contains("/api/v1/person/1"))
        assertEquals("Address Test1", result.address)
        assertEquals("First Name Test1", result.firstName)
        assertEquals("Last Name Test1", result.lastName)
        assertEquals("Female", result.gender)
    }

    @Test
    fun delete() {
        val person = inputObject.mockEntity(1)

        `when`(repository.findById(1L)).thenReturn(Optional.of(person))

        service.delete(1L)
    }
}