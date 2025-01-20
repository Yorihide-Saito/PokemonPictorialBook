package com.example.pokmonpictorialbook.data.repository

import com.example.pokmonpictorialbook.data.common.PokemonMapper
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonSpeciesEntity
import com.example.pokmonpictorialbook.fake.api.FakePokemonApiService
import com.example.pokmonpictorialbook.fake.database.FakePokemonDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class PokemonRepositoryImplTest {
    
    private lateinit var fakePokemonApiService: FakePokemonApiService
    private lateinit var fakePokemonDao: FakePokemonDao
    private lateinit var repository: PokemonRepositoryImpl
    
    @Before
    fun setUp() {
        fakePokemonApiService = FakePokemonApiService()
        fakePokemonDao = mock() // TODO: Fakeに変更したいかも、、、
        repository = PokemonRepositoryImpl(
            apiService = fakePokemonApiService,
            pokemonDao = fakePokemonDao,
            localImageDataSource = mock(),
            remoteImageDataSource = mock(),
            pokemonMapper = PokemonMapper()
        )
    }

    @Test
    fun fetchTotalNumberOfPokemonFromApi_ResponseIsSuccessful_ReturnListOfPokemonEntity() {
        runTest {
            val count = 1000
            val pokemonEntityList: List<PokemonEntity> = repository.fetchTotalNumberOfPokemonFromApi(count)

            assertEquals(count, pokemonEntityList.count())
            assertEquals(0, pokemonEntityList[0].id)
            assertEquals("pokemon0", pokemonEntityList[0].name)
        }
    }

    @Test(expected = IOException::class)
    fun fetchTotalNumberOfPokemonFromApi_ResponseNull_ThrowIOException() {
        runTest {
            fakePokemonApiService.setReturnNull(true)
            repository.fetchTotalNumberOfPokemonFromApi()
        }
    }

    @Test(expected = IOException::class)
    fun fetchTotalNumberOfPokemonFromApi_ResponseError_ThrowIOException() {
        runTest {
            fakePokemonApiService.setReturnError(true)
            repository.fetchTotalNumberOfPokemonFromApi()
        }
    }

    @Test
    fun fetchPokemonFromApi_ResponseIsSuccessful_ReturnPokemonDetailEntity() {
        runTest {
            val pokemonName = "hoge"

            val response: PokemonDetailEntity = repository.fetchPokemonFromApi(pokemonName)

            // Fake データを検証データとして使用してるので、直したほうがいいかも
            assertEquals(fakePokemonApiService.fakePokemonResponse[pokemonName]?.id ,response.pokemonId)
            assertEquals(fakePokemonApiService.fakePokemonResponse[pokemonName]?.height, response.height)
            assertEquals(fakePokemonApiService.fakePokemonResponse[pokemonName]?.weight, response.weight)
            assertEquals(fakePokemonApiService.fakePokemonResponse[pokemonName]?.types?.get(0)?.type?.name, response.types[0])
            assertEquals(fakePokemonApiService.fakePokemonResponse[pokemonName]?.sprites?.frontDefault, response.frontDefault)
            assertEquals(fakePokemonApiService.fakePokemonResponse[pokemonName]?.sprites?.backDefault, response.backDefault)
        }
    }

    @Test(expected = IOException::class)
    fun fetchPokemonFromApi_ResponseNull_ThrowIoException() {
        runTest {
            fakePokemonApiService.setReturnNull(true)
            repository.fetchPokemonFromApi("hoge")
        }
    }

    @Test(expected = IOException::class)
    fun fetchPokemonFromApi_ResponseError_ThrowIOException() {
        runTest{
            fakePokemonApiService.setReturnError(true)
            repository.fetchPokemonFromApi("hoge")
        }
    }

    // 妥協している。もう少し Assert 作成したら Coverage 増えそう。
    // TODO: fake データをそのまま検証データとして使用している部分直すかも。
    @Test
    fun fetchPokemonSpeciesFromAPi_ResponseIsSuccessful_ReturnPokemonSpeciesEntity() {
        runTest {
            val pokemonName = "hoge"

            val response: PokemonSpeciesEntity = repository.fetchPokemonSpeciesFromApi("hoge")

            assertEquals(
                fakePokemonApiService.fakePokemonSpeciesResponse[pokemonName]?.names?.get(0)?.name,
                response.pokemonNameEntity[0].name
            )
            assertEquals(
                fakePokemonApiService.fakePokemonSpeciesResponse[pokemonName]?.names?.get(0)?.language?.languageCode,
                response.pokemonNameEntity[0].languageCode
            )
            assertEquals(
                fakePokemonApiService.fakePokemonSpeciesResponse[pokemonName]?.flavorTextEntries?.get(0)?.flavorText,
                response.pokemonFlavorTextEntity[0].flavorText
            )
            assertEquals(
                fakePokemonApiService.fakePokemonSpeciesResponse[pokemonName]?.flavorTextEntries?.get(0)?.language?.languageCode,
                response.pokemonFlavorTextEntity[0].languageCode
            )
            assertEquals(
                fakePokemonApiService.fakePokemonSpeciesResponse[pokemonName]?.genera?.get(0)?.genus,
                response.pokemonGeneraEntity[0].genera
            )
            assertEquals(
                fakePokemonApiService.fakePokemonSpeciesResponse[pokemonName]?.genera?.get(0)?.language?.languageCode,
                response.pokemonGeneraEntity[0].languageCode
            )
        }
    }

    @Test(expected = IOException::class)
    fun fetchPokemonSpeciesFromApi_ResponseNull_ThrowIOException() {
        runTest{
            fakePokemonApiService.setReturnNull(true)
            repository.fetchPokemonSpeciesFromApi("hoge")
        }
    }

    @Test(expected = IOException::class)
    fun fetchPokemonSpeciesFromApi_ResponseError_ThrowIOException() {
        runTest{
            fakePokemonApiService.setReturnError(true)
            repository.fetchPokemonSpeciesFromApi("hoge")
        }
    }


}