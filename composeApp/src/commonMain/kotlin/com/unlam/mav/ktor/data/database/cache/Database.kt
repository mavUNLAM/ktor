package com.unlam.mav.ktor.data.database.cache

import com.unlam.mav.ktor.data.database.entity.DelightCharacter

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = MarvelDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.marvelDatabaseQueries

    internal fun getAllCharacters(): List<DelightCharacter>
}
