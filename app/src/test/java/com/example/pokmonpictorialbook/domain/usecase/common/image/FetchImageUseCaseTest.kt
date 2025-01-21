package com.example.pokmonpictorialbook.domain.usecase.common.image

import android.graphics.Bitmap
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.repository.PokemonRepository
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class FetchImageUseCaseTest {

    private lateinit var fetchImageUseCase: FetchImageUseCase
    private lateinit var mockPokemonRepository: PokemonRepository

    @Before
    fun setUp() {
        mockPokemonRepository = mock<PokemonRepository>()
        fetchImageUseCase = FetchImageUseCase(mockPokemonRepository)
    }

    @Test
    fun invoke_FetchImages_ReturnCorrectData() {
        runTest {
            // Arrange
            // DetailEntity は何でもいい。
            val pokemonDetailEntity = PokemonDetailEntity(
                pokemonId = 1,
                height = 100,
                weight = 100,
                types = listOf("hoge, fuga"),
                backDefault = "hogeBack",
                frontDefault = "hogeFront",
            )
            val frontImage = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            val backImage = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888)
            whenever(mockPokemonRepository.fetchImage(any())).thenReturn(frontImage).thenReturn(backImage)

            // Act
            val result = fetchImageUseCase(pokemonDetailEntity).toList()

            // Assert
            assertNotNull(result)
            assertEquals(1, result.size)
            assertEquals(frontImage, result[0].first)
            assertEquals(backImage, result[0].second)
        }
    }
}