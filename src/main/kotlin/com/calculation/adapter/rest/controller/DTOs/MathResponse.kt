package com.calculation.adapter.rest.controller.DTOs

import com.calculation.adapter.database.entities.MathEntity

data class MathResponse(
    val id: Long?,
    var valuesCalculation: MutableList<Int>,
    var calculationType: CalculationType,
    var result: Double
)
object MathResponseFactory {
    fun create(math: MathEntity): MathResponse {
        return MathResponse(math.id, math.valuesCalculation, math.calculationType, math.result)
    }
}