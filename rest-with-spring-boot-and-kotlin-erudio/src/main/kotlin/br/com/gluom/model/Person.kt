package br.com.gluom.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name="person")
data class Person (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(name="first_name", nullable = false, length = 80)
    var firstName: String = "",

    @Column(name="last_name", nullable = false, length = 80)
    var lastName: String = "",

    @Column(nullable = false, length = 255)
    var address: String = "",

    @Column(nullable = false, length = 50)
    var gender: String = "",

    @Column(nullable = false)
    var enabled: Boolean = true
)