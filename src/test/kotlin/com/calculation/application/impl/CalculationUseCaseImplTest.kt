package com.calculation.application.impl

import com.calculation.adapter.database.CalculationJpaRepository
import com.calculation.adapter.database.MathJpaRepository
import com.calculation.adapter.database.entities.CalculationEntity
import com.calculation.adapter.database.entities.MathEntity
import com.calculation.adapter.rest.controller.DTOs.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import java.time.Instant

@ExtendWith(MockitoExtension::class)
class CalculationUseCaseImplTest {

    @Mock
    private lateinit var repositoryMath: MathJpaRepository

    @Mock
    private lateinit var repository: CalculationJpaRepository

    @InjectMocks
    private lateinit var calculationUseCase: CalculationUseCaseImpl

    @Test
    fun calculationTest() {
        val requestList = listOf(
            CalculationRequest(calculationType = CalculationType.SUM, values = listOf(1, 2, 3)),
            CalculationRequest(calculationType = CalculationType.MULTIPLICATION, values =  listOf(2, 3, 4)),
            CalculationRequest(calculationType = CalculationType.DIVISION, values =  listOf(4, 4)),
            CalculationRequest(calculationType = CalculationType.SUBTRATION, values =  listOf(4, 4))
        )

        val calculationEntity = CalculationEntity()
        calculationEntity.calculationDate = Instant.now()

        `when`(repository.save(any())).thenReturn(calculationEntity)
        `when`(repositoryMath.save(any())).thenAnswer {
            val mathEntityArgument = it.arguments[0] as MathEntity
            mathEntityArgument.id = 1L
            mathEntityArgument
        }

        val result = calculationUseCase.calculation(requestList)

        result.calculations.getOrNull(0)?.let { assertEquals(6.0 , it.result ) }
        result.calculations.getOrNull(1)?.let { assertEquals(24.0, it.result ) }
        result.calculations.getOrNull(2)?.let { assertEquals(1.0, it.result ) }
        result.calculations.getOrNull(3)?.let { assertEquals(0.0, it.result ) }
    }

    @Test
    fun calculationResponseFullFactoryCreateTest() {

        val calculationDate = Instant.now()
        val calculationEntity = getCalculationEntity(calculationDate)

        val result: CalculationResponseFull = CalculationResponseFullFactory.create(calculationEntity)

        val calc0 =  result.calculations.get(0)
        assertEquals(10.0, calc0.result)
        assertEquals(mutableListOf(2, 5), calc0.valuesCalculation)
        assertEquals(CalculationType.MULTIPLICATION, calc0.calculationType)

        val calc1 =  result.calculations.get(1)
        assertEquals(20.0, calc1.result)
        assertEquals(mutableListOf(10, 10), calc1.valuesCalculation)
        assertEquals(CalculationType.SUM, calc1.calculationType)

        assertEquals(calculationDate, result.calculationDate)
    }


    @Test
    fun getAllCalculationPaginated_firstPage_success() {
        val calculationDate = Instant.now()
        val calculationEntity = getCalculationEntity(calculationDate)

        val page = PageImpl(mutableListOf(calculationEntity))
        val pageable: Pageable = PageRequest.of(0, 10)

        `when`(repository.findAll(pageable)).thenReturn(page)

        val calculationUseCase = CalculationUseCaseImpl(repository, repositoryMath)

        val dtoPage = calculationUseCase.getAllSumPaginated(pageable)
        val calculationResponseFull1 = dtoPage.content.first()
        val calculationResponseFull2 = dtoPage.content.last()

        val calcFirst =  calculationResponseFull1.calculations.get(0)
        assertEquals(calculationDate, calculationResponseFull1.calculationDate)
        assertEquals(mutableListOf(2, 5), calcFirst.valuesCalculation)
        assertEquals(10.0, calcFirst.result)
        assertEquals(CalculationType.MULTIPLICATION, calcFirst.calculationType)

        val calcSecond =  calculationResponseFull2.calculations.get(1)
        assertEquals(calculationDate, calculationResponseFull2.calculationDate)
        assertEquals(mutableListOf(10, 10), calcSecond.valuesCalculation)
        assertEquals(20.0, calcSecond.result)
        assertEquals(CalculationType.SUM, calcSecond.calculationType)
    }


    @Test
    fun getAllCalculationPaginated_secondPage_empty_success() {
        val page = Page.empty<CalculationEntity>()
        val pageable: Pageable = PageRequest.of(1, 10)

        `when`(repository.findAll(pageable)).thenReturn(page)

        val calculationUseCase = CalculationUseCaseImpl(repository, repositoryMath)
        val dtoPage = calculationUseCase.getAllSumPaginated(pageable)

        assertEquals(0, dtoPage.content.size)
    }

    private fun getCalculationEntity(calculationDate: Instant): CalculationEntity {
        val calculationEntity = CalculationEntity()
        calculationEntity.calculationDate = calculationDate

        val mathEntity1 = MathEntity()
        mathEntity1.result = 10.0
        mathEntity1.valuesCalculation = mutableListOf(2, 5)
        mathEntity1.calculationType = CalculationType.MULTIPLICATION

        val mathEntity2 = MathEntity()
        mathEntity2.result = 20.0
        mathEntity2.valuesCalculation = mutableListOf(10, 10)
        mathEntity2.calculationType = CalculationType.SUM

        calculationEntity.calculations = listOf(
            mathEntity1,
            mathEntity2
        ).toMutableList()

        return calculationEntity
    }
}