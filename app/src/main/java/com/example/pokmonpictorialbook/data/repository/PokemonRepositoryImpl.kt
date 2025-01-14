package com.example.pokmonpictorialbook.data.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.pokmonpictorialbook.data.api.PokemonApiService
import com.example.pokmonpictorialbook.data.database.dao.PokemonDao
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.data.common.PokemonMapper
import com.example.pokmonpictorialbook.data.api.response.PokemonListResponse
import com.example.pokmonpictorialbook.data.api.response.PokemonResponse
import com.example.pokmonpictorialbook.data.api.response.PokemonSpeciesResponse
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntities
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonFlavorTextEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonGeneraEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonNameEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonSpeciesEntity
import com.example.pokmonpictorialbook.data.source.LocalImageDataSource
import com.example.pokmonpictorialbook.data.source.RemoteImageDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PokemonRepositoryImpl(
    private val apiService: PokemonApiService,
    private val pokemonDao: PokemonDao,
    private val localImageDataSource: LocalImageDataSource,
    private val remoteImageDataSource: RemoteImageDataSource,
    private val pokemonMapper: PokemonMapper = PokemonMapper()
) : PokemonRepository {

    override suspend fun fetchTotalNumberOfPokemonFromApi(limit: Int, offset: Int): List<PokemonEntity> {
        return withContext(Dispatchers.IO) {
            val response: PokemonListResponse =  apiService.fetchPokemonList(limit, offset)
            pokemonMapper.mapPokemonListResponseToEntity(response)
        }
    }

    override suspend fun fetchPokemonFromApi(pokemonName: String): PokemonDetailEntity {
        return withContext(Dispatchers.IO) {
            val response: PokemonResponse = apiService.fetchPokemon(pokemonName)
            pokemonMapper.mapPokemonResponseToPokemonDetailEntity(response)
        }
    }

    override suspend fun fetchPokemonSpeciesFromApi(pokemonName: String): PokemonSpeciesEntity {
        return withContext(Dispatchers.IO) {
            val response: PokemonSpeciesResponse = apiService.fetchPokemonSpecies(pokemonName)
            pokemonMapper.mapPokemonSpeciesResponseToEntity(response)
        }
    }

    // Database
    // PokemonEntity
    override suspend fun insertAllPokemon(
        pokemonEntityList: List<PokemonEntity>
    ) {
        pokemonDao.apply {
            insertPokemonList(pokemonEntityList)
        }
    }

    override suspend fun fetchAllPokemonFromDatabase(): List<PokemonEntity?> {
        return withContext(Dispatchers.IO) {
            pokemonDao.fetchAllPokemon()
        }
    }

    //PokemonDetailEntity
    override suspend fun insertPokemonDetailEntities(
        pokemonDetailEntities: PokemonDetailEntities
    ) {
        pokemonDao.apply {
            insertPokemonDetail(pokemonDetailEntities.pokemonDetailEntity)
            pokemonDetailEntities.pokemonSpeciesEntity.pokemonNameEntity.map { pokemonNameEntity ->
                insertPokemonName(pokemonNameEntity)
            }
            pokemonDetailEntities.pokemonSpeciesEntity.pokemonFlavorTextEntity.map { pokemonFlavorTextEntity ->
                insertPokemonFlavorText(pokemonFlavorTextEntity)
            }
            pokemonDetailEntities.pokemonSpeciesEntity.pokemonGeneraEntity.map { pokemonGeneraEntity ->
                insertPokemonGenera(pokemonGeneraEntity)
            }
        }
    }

    override suspend fun fetchAllPokemonDetailFromDatabase(): List<PokemonDetailEntity?> {
        return withContext(Dispatchers.IO) {
            pokemonDao.fetchAllPokemonDetail()
        }
    }

    override suspend fun fetchPokemonDetailFromDatabase(pokemonId: Int): PokemonDetailEntity? {
        return withContext(Dispatchers.IO) {
            pokemonDao.fetchPokemonDetailById(pokemonId)
        }
    }

    override suspend fun fetchPokemonNameWithTranslationFromDatabase(
        pokemonId: Int,
        languageCode: String
    ): PokemonNameEntity? {
        return withContext(Dispatchers.IO) {
            pokemonDao.fetchPokemonNameWithTranslation(pokemonId, languageCode)
        }
    }

    override suspend fun fetchPokemonGeneraWithTranslationFromDatabase(
        pokemonId: Int,
        languageCode: String
    ): PokemonGeneraEntity? {
        return withContext(Dispatchers.IO) {
            pokemonDao.fetchPokemonGeneraWithTranslation(pokemonId, languageCode)
        }
    }

    override suspend fun fetchPokemonFlavorTextWithTranslationFromDatabase(
        pokemonId: Int,
        languageCode: String
    ): PokemonFlavorTextEntity? {
        return withContext(Dispatchers.IO) {
            pokemonDao.fetchPokemonFlavorTextWithTranslation(pokemonId, languageCode)
        }
    }

    override suspend fun downloadImage(
        imageName: String,
        url: String
    ) {
        localImageDataSource.saveImageToInternalStorage(
            imageName = imageName,
            bitmap = remoteImageDataSource.downloadImage(url)
        )
    }

    override suspend fun fetchImage(
        imageName: String
    ): Bitmap? {
        val file = localImageDataSource.getLocalImageFile(imageName = imageName)
        val bitmap = file?.takeIf { it.exists() }?.let {
            BitmapFactory.decodeFile(it.absolutePath)
        }
        return bitmap
    }
}
