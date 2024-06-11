package com.muratcangzm.dummysahibinden.utils

import javax.inject.Qualifier


@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DefaultDispatcher()

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IODispatcher()

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainDispatcher()