package com.calculation.adapter.database.entities

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.time.Instant

@Entity
data class CalculationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,

    @OneToMany
    var calculations: MutableList<MathEntity>,
    var calculationDate: Instant,
){
    constructor() : this(0, mutableListOf(), Instant.now())
}


