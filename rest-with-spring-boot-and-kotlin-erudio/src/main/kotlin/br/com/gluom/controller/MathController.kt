package br.com.gluom.controller

import br.com.gluom.exceptions.UnsupportedMathOperationException
import br.com.gluom.services.CalcService
import br.com.gluom.services.NumberService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.atomic.AtomicLong

@RestController
class MathController {

    private val calcService = CalcService()

    @RequestMapping("/sum/{numberOne}/{numberTwo}")
    fun sum(@PathVariable(value="numberOne") numberOne: String?,
            @PathVariable(value="numberTwo") numberTwo: String?
    ): Double {
        if(!NumberService.isNumeric(numberOne) || !NumberService.isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, use only numeric values")

        return calcService.sum(NumberService.convertToDouble(numberOne), NumberService.convertToDouble(numberTwo))
    }

    @RequestMapping("/sub/{numberOne}/{numberTwo}")
    fun sub(@PathVariable(value="numberOne") numberOne: String?,
                    @PathVariable(value="numberTwo") numberTwo: String?
    ): Double {
        if(!NumberService.isNumeric(numberOne) || !NumberService.isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, use only numeric values")

        return calcService.sub(NumberService.convertToDouble(numberOne), NumberService.convertToDouble(numberTwo))
    }

    @RequestMapping("/div/{numberOne}/{numberTwo}")
    fun div(@PathVariable(value="numberOne") numberOne: String?,
                 @PathVariable(value="numberTwo") numberTwo: String?
    ): Double {
        if(!NumberService.isNumeric(numberOne) || !NumberService.isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, use only numeric values")

        return calcService.div(NumberService.convertToDouble(numberOne), NumberService.convertToDouble(numberTwo))
    }

    @RequestMapping("/mul/{numberOne}/{numberTwo}")
    fun mul(@PathVariable(value="numberOne") numberOne: String?,
                       @PathVariable(value="numberTwo") numberTwo: String?
    ): Double {
        if(!NumberService.isNumeric(numberOne) || !NumberService.isNumeric(numberTwo))
            throw UnsupportedMathOperationException("Please, use only numeric values")

        return calcService.mul(NumberService.convertToDouble(numberOne), NumberService.convertToDouble(numberTwo))
    }

    @RequestMapping("/sqrt/{number}")
    fun squareRoot(@PathVariable(value="number") number: String?): Double {
        if(!NumberService.isNumeric(number))
            throw UnsupportedMathOperationException("Please, use only numeric values")

        return calcService.sqrt(NumberService.convertToDouble(number))
    }


}