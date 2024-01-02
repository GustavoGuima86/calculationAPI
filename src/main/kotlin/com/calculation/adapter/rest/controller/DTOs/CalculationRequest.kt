package com.calculation.adapter.rest.controller.DTOs

import io.swagger.v3.oas.annotations.media.Schema

data class CalculationRequest(

    @field:Schema(required = true, description = "Required", minLength = 0)
    val values: List<Int>,

    @field:Schema(required = true, description = "Required", minLength = 0)
    val calculationType: CalculationType
)