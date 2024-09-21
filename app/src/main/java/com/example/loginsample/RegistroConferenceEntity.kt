package com.example.loginsample

class RegistroConferenceEntity {

    private var firstName: String = ""
    private var lastName: String = ""
    private var email:String = ""
    private var phone:String = ""
    private var nacimiento:String = ""
    private var username:String = ""
    private var password:String = ""

    // Getters
    fun getFirstName(): String {
        return firstName
    }

    fun getLastName(): String {
        return lastName
    }

    fun getEmail(): String {
        return email
    }

    fun getPhone(): String {
        return phone
    }

    fun getNacimiento(): String {
        return nacimiento
    }

    fun getUsername(): String {
        return username
    }

    fun getPassword(): String {
        return password
    }

    // Setters
    fun setFirstName(firstName: String) {
        this.firstName = firstName
    }

    fun setLastName(lastName: String) {
        this.lastName = lastName
    }

    fun setEmail(email: String) {
        this.email = email
    }

    fun setPhone(phone: String) {
        this.phone = phone
    }

    fun setNacimiento(nacimiento: String) {
        this.nacimiento = nacimiento
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun setPassword(password: String) {
        this.password = password
    }

}