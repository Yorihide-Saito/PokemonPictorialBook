package com.example.pokmonpictorialbook.data.repository

import com.example.pokmonpictorialbook.data.common.PokemonMapper
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.fake.api.FakePokemonApiService
import com.example.pokmonpictorialbook.fake.database.FakePokemonDao
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonRepositoryImplTest {
    
    private lateinit var fakePokemonApiService: FakePokemonApiService
    private lateinit var fakePokemonDao: FakePokemonDao
    private lateinit var repository: PokemonRepositoryImpl
    
    @Before
    fun setUp() {
        fakePokemonApiService = FakePokemonApiService()
        repository = PokemonRepositoryImpl(
            apiService = fakePokemonApiService,
            pokemonDao = mock(),
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

}