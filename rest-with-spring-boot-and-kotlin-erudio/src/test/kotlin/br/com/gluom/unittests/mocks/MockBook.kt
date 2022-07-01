package br.com.gluom.unittests.mocks

import br.com.gluom.data.vo.v1.BookVO
import br.com.gluom.model.Book
import java.util.*

class MockBook {
    fun mockEntity(): Book {
        return mockEntity(0)
    }

    fun mockVO(): BookVO {
        return mockVO(0)
    }

    fun mockEntityList(): ArrayList<Book> {
        val books: ArrayList<Book> = ArrayList<Book>()
        for (i in 0..13) {
            books.add(mockEntity(i))
        }
        return books
    }

    fun mockVOList(): ArrayList<BookVO> {
        val books: ArrayList<BookVO> = ArrayList()
        for (i in 0..13) {
            books.add(mockVO(i))
        }
        return books
    }

    fun mockEntity(number: Int): Book {
        val book = Book()
        book.title = "Title Test$number"
        book.price = 1.5 * number
        book.author = if (number % 2 == 0) "Cora Coralina" else "Machado de Assis"
        book.launchDate = Date()
        book.id = number.toLong()
        return book
    }

    fun mockVO(number: Int): BookVO {
        val bookVO = BookVO()
        bookVO.title = "Title Test$number"
        bookVO.price = 1.5 * number
        bookVO.author = if (number % 2 == 0) "Cora Coralina" else "Machado de Assis"
        bookVO.launchDate = Date()
        bookVO.key = number.toLong()
        return bookVO
    }
}