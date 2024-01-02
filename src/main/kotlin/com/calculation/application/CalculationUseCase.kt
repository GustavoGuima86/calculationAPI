package com.calculation.application

import com.calculation.adapter.rest.controller.DTOs.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface CalculationUseCase {
    fun calculation(bodyRequest: List<CalculationRequest>): CalculationResponseFull

    fun getAllSumPaginated(pageable: Pageable): Page<CalculationResponseFull>
}