package com.calculation.application.impl

import com.calculation.adapter.database.CalculationJpaRepository
import com.calculation.adapter.database.MathJpaRepository
import com.calculation.adapter.database.entities.CalculationEntity
import com.calculation.adapter.database.entities.MathEntity
import com.calculation.adapter.rest.controller.DTOs.*
import com.calculation.application.CalculationUseCase
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class CalculationUseCaseImpl(private val repository: CalculationJpaRepository,
                             private val repositoryMath: MathJpaRepository) : CalculationUseCase {

    override fun calculation(bodyRequest: List<CalculationRequest>): CalculationResponseFull {

        val calculationEntity = CalculationEntity()
        calculationEntity.calculationDate = Instant.now()

        var savedEntity = repository.save(calculationEntity)

        val maths: MutableList<MathEntity> = mutableListOf()

        bodyRequest.forEach{calculationRequest ->
            val mathEntity = MathEntity()
            mathEntity.valuesCalculation.addAll(calculationRequest.values)
            mathEntity.calculationType = calculationRequest.calculationType
            mathEntity.calculation = savedEntity
            makeMath(calculationRequest, mathEntity)
            maths.add(repositoryMath.save(mathEntity))
        }

        savedEntity.calculations.addAll(maths)
        savedEntity = repository.save(savedEntity)

        return CalculationResponseFullFactory.create(savedEntity)
    }

    private fun makeMath(calculationRequest: CalculationRequest, mathEntity: MathEntity) {
        when (calculationRequest.calculationType) {
            CalculationType.SUM -> {
                val result = calculationRequest.values.drop(1).fold(calculationRequest.values.first().toDouble()) { accumulator, current ->
                    accumulator + current
                }
                mathEntity.result = result
            }
            CalculationType.MULTIPLICATION -> {
                val result = calculationRequest.values.drop(1).fold(calculationRequest.values.first().toDouble()) { accumulator, current ->
                    accumulator * current
                }
                mathEntity.result = result
            }
            CalculationType.DIVISION -> {
                val result = calculationRequest.values.drop(1).fold(calculationRequest.values.first().toDouble()) { accumulator, current ->
                    accumulator / current
                }
                mathEntity.result = result
            }
            CalculationType.SUBTRATION -> {
                val result = calculationRequest.values.drop(1).fold(calculationRequest.values.first().toDouble()) { accumulator, current ->
                    accumulator - current
                }
                mathEntity.result = result
            }
        }
    }

    override fun getAllSumPaginated(pageable: Pageable): Page<CalculationResponseFull> {
        val pageSum = repository.findAll(pageable)

        val dtoPage: Page<CalculationResponseFull> = pageSum.map {
            CalculationResponseFullFactory.create(it)}
        return dtoPage
    }
}