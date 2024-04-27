package com.spacey.codedatabase

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class BaseRepository {
    protected val defaultContext: CoroutineContext = Dispatchers.Default
}