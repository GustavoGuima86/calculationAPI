package com.calculation.adapter.database.entities

import com.calculation.adapter.rest.controller.DTOs.CalculationType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

@Entity
data class MathEntity(
    @Id
    @GeneratedValue(
        strategy = GenerationType.AUTO
    )
    var id: Long?,
    var valuesCalculation: MutableList<Int>,
    var calculationType: CalculationType,

    @ManyToOne
    var calculation: CalculationEntity?,
    var result: Double
){
    
    constructor() : this(0, mutableListOf(), CalculationType.SUM, null, 0.0)
}


