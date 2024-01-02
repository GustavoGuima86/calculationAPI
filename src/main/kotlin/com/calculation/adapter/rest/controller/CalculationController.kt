package com.calculation.adapter.rest.controller

import com.calculation.adapter.rest.controller.DTOs.CalculationRequest
import com.calculation.adapter.rest.controller.DTOs.CalculationResponseFull
import com.calculation.application.CalculationUseCase
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.web.bind.annotation.*

@RestController
@Tag( name = "Calculation API", description = "Calculation controller")
class CalculationController(private val calculationUseCase: CalculationUseCase) {


    @PostMapping("/calculation")
    fun postCalculation(@RequestBody bodyRequest: List<CalculationRequest>): CalculationResponseFull {
        return calculationUseCase.calculation(bodyRequest)
    }

    @GetMapping("/calculation/pagination")
    fun getAllCalculations(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): Page<CalculationResponseFull> {
        val pageable = PageRequest.of(page, size)
        return calculationUseCase.getAllSumPaginated(pageable)
    }
}