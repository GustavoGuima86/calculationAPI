package com.calculation.adapter.rest.controller.DTOs

import com.calculation.adapter.database.entities.CalculationEntity
import java.time.Instant

data class CalculationResponseFull(
    val id: Long? = null,
    val calculationDate: Instant,
    val calculations: List<MathResponse>
    )
object CalculationResponseFullFactory {
    fun create(calculation: CalculationEntity): CalculationResponseFull {
        val calcList =  calculation.calculations.map {
            MathResponseFactory.create(it)}
        return CalculationResponseFull(calculation.id, calculation.calculationDate, calcList)
    }
}

