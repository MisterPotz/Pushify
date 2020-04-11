package com.gornostaevas.pushify.di

import javax.inject.Scope

// Singletone on level of application
@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class ApplicationScope

// Singletone on level of observing authorized nets
@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class AuthorizedListScope

// Singletone on level of creating new authorized connection
@Scope
@Retention(value = AnnotationRetention.RUNTIME)
annotation class AuthorizationScope