package com.tngdev.sportevent.model

class MatchesList<T> {
    lateinit var matches: InternalMatchesList<T>
}

class InternalMatchesList<T> {
    lateinit var previous: List<T>
    lateinit var upcoming: List<T>
}