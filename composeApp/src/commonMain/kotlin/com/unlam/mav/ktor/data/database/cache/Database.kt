package com.unlam.mav.ktor.data.database.cache

import com.unlam.mav.ktor.data.database.entity.DelightCharacter

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = MarvelDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.marvelDatabaseQueries

    internal fun getAllCharacters(): List<DelightCharacter> {
        return dbQuery.selectAllCharacters(::mapCharacterQueryResult).executeAsList()
    }

    internal fun getAllCharactersFromPage(page: Long): List<DelightCharacter> {
        return dbQuery.selectAllCharactersFromPage(page, ::mapCharacterQueryResult).executeAsList()
    }

    internal fun insertCharacter(character: DelightCharacter) {
        dbQuery.transaction {
            dbQuery.insertCharacter(
                id = character.id.toLong(),
                name = character.name,
                description = character.description,
                thumbnail = character.thumbnail,
                page = character.page.toLong()
            )
        }
    }

    internal fun insertCharacterList(characters: List<DelightCharacter>) {
        dbQuery.transaction {
            characters.forEach { character ->
                dbQuery.insertCharacter(
                    id = character.id.toLong(),
                    name = character.name,
                    description = character.description,
                    thumbnail = character.thumbnail,
                    page = character.page.toLong()
                )
            }
        }
    }

    private fun mapCharacterQueryResult(
        id: Long,
        name: String,
        description: String?,
        thumbnail: String,
        page: Long
    ) = DelightCharacter(
        id = id.toInt(),
        name = name,
        description = if(description.isNullOrBlank()) {
            "Empty"
        } else {
            description
        },
        thumbnail = thumbnail,
        page = page.toInt()
    )
}
