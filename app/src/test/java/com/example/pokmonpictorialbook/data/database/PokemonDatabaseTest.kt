package com.example.pokmonpictorialbook.data.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.pokmonpictorialbook.data.database.dao.PokemonDao
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonDetailEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonFlavorTextEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonGeneraEntity
import com.example.pokmonpictorialbook.data.database.entitiy.PokemonNameEntity
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.IOException

/**
 * PokemonDatabase の Test
 * Robolectric に対応（カバレッジテストだとエラーが出る。）
 * TODO: これ参考に直したい。 https://github.com/robolectric/robolectric/issues/3023
 */

@RunWith(RobolectricTestRunner::class)
class PokemonDatabaseTest {

    private lateinit var database: PokemonDatabase
    private lateinit var pokemonDao: PokemonDao

    @Before
    fun createDb() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            PokemonDatabase::class.java
        ).allowMainThreadQueries().build()

        pokemonDao = database.pokemonDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        database.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertPokemonListAndFetchPokemonEntity() {
        runTest{
            val pokemonList: List<PokemonEntity> = listOf(
                PokemonEntity(id = 1, name = "bulbasaur"),
                PokemonEntity(id = 2, name = "ivysaur"),
                PokemonEntity(id = 3, name = "venusaur")
            )
            pokemonDao.insertPokemonList(pokemonList)

            val fetchedPokemonBy1: PokemonEntity? = pokemonDao.fetchPokemonById(1)
            val fetchedPokemonBy2: PokemonEntity? = pokemonDao.fetchPokemonById(2)
            val fetchedPokemonBy3: PokemonEntity? = pokemonDao.fetchPokemonById(3)

            assertEquals(1, fetchedPokemonBy1?.id)
            assertEquals(2, fetchedPokemonBy2?.id)
            assertEquals(3, fetchedPokemonBy3?.id)
            assertEquals("bulbasaur", fetchedPokemonBy1?.name)
            assertEquals("ivysaur", fetchedPokemonBy2?.name)
            assertEquals("venusaur", fetchedPokemonBy3?.name)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertPokemonListAndFetchPokemonEntityList() {
        runTest{
            val pokemonList: List<PokemonEntity> = listOf(
                PokemonEntity(id = 1, name = "bulbasaur"),
                PokemonEntity(id = 2, name = "ivysaur"),
                PokemonEntity(id = 3, name = "venusaur")
            )
            pokemonDao.insertPokemonList(pokemonList)

            val fetchedPokemonList: List<PokemonEntity?> = pokemonDao.fetchAllPokemon()

            fetchedPokemonList[0].let { pokemonEntity ->
                assertEquals(1, pokemonEntity?.id)
                assertEquals("bulbasaur", pokemonEntity?.name)
            }
            fetchedPokemonList[1].let { pokemonEntity ->
                assertEquals(2, pokemonEntity?.id)
                assertEquals("ivysaur", pokemonEntity?.name)
            }
            fetchedPokemonList[2].let { pokemonEntity ->
                assertEquals(3, pokemonEntity?.id)
                assertEquals("venusaur", pokemonEntity?.name)
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertAndFetchPokemonDetail() {
        runTest {
            setUpInsertParentTable()

            val pokemonDetail1: PokemonDetailEntity = PokemonDetailEntity(
                pokemonId = 1,
                height = 1000,
                weight = 1000,
                types = listOf("fuga", "hoge"),
                backDefault = "back",
                frontDefault = "front"
            )
            val pokemonDetail2: PokemonDetailEntity = PokemonDetailEntity(
                pokemonId = 2,
                height = 2000,
                weight = 2000,
                types = listOf("fugafuga", "hogehoge"),
                backDefault = "backBack",
                frontDefault = "frontFront"
            )
            pokemonDao.insertPokemonDetail(pokemonDetail1)
            pokemonDao.insertPokemonDetail(pokemonDetail2)

            val fetchPokemonDetailEntity: PokemonDetailEntity? = pokemonDao.fetchPokemonDetailById(1)
            assertEquals(1, fetchPokemonDetailEntity?.pokemonId)
            assertEquals(1000, fetchPokemonDetailEntity?.height)
            assertEquals(1000, fetchPokemonDetailEntity?.weight)
            assertEquals(listOf("fuga", "hoge"), fetchPokemonDetailEntity?.types)
            assertEquals("back", fetchPokemonDetailEntity?.backDefault)
            assertEquals("front", fetchPokemonDetailEntity?.frontDefault)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertPokemonDetailAndFetchPokemonDetailList() {
        runTest {
            setUpInsertParentTable()

            val pokemonDetail1: PokemonDetailEntity = PokemonDetailEntity(
                pokemonId = 1,
                height = 1000,
                weight = 1000,
                types = listOf("fuga", "hoge"),
                backDefault = "back",
                frontDefault = "front"
            )
            val pokemonDetail2: PokemonDetailEntity = PokemonDetailEntity(
                pokemonId = 2,
                height = 2000,
                weight = 2000,
                types = listOf("fugafuga", "hogehoge"),
                backDefault = "backBack",
                frontDefault = "frontFront"
            )
            pokemonDao.insertPokemonDetail(pokemonDetail1)
            pokemonDao.insertPokemonDetail(pokemonDetail2)

            val fetchedPokemonDetailList: List<PokemonDetailEntity?> = pokemonDao.fetchAllPokemonDetail()

            fetchedPokemonDetailList[0].let { pokemonDetail ->
                assertEquals(1, pokemonDetail?.pokemonId)
                assertEquals(1000, pokemonDetail?.height)
                assertEquals(1000, pokemonDetail?.weight)
                assertEquals(listOf("fuga", "hoge"), pokemonDetail?.types)
                assertEquals("back", pokemonDetail?.backDefault)
                assertEquals("front", pokemonDetail?.frontDefault)
            }

            fetchedPokemonDetailList[1].let { pokemonDetail ->
                assertEquals(2, pokemonDetail?.pokemonId)
                assertEquals(2000, pokemonDetail?.height)
                assertEquals(2000, pokemonDetail?.weight)
                assertEquals(listOf("fugafuga", "hogehoge"), pokemonDetail?.types)
                assertEquals("backBack", pokemonDetail?.backDefault)
                assertEquals("frontFront", pokemonDetail?.frontDefault)
            }
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertAndFetchPokemonName() {
        runTest {
            setUpInsertParentTable()

            val pokemonNameEntity: PokemonNameEntity = PokemonNameEntity(
                id = 1,
                pokemonId = 1,
                languageCode = "ja",
                name = "フシギダネ"
            )
            pokemonDao.insertPokemonName(pokemonNameEntity)

            val fetchedPokemonNameEntity: PokemonNameEntity? = pokemonDao.fetchPokemonNameWithTranslation(1, "ja")
            assertEquals(1, fetchedPokemonNameEntity?.id)
            assertEquals(1, fetchedPokemonNameEntity?.pokemonId)
            assertEquals("ja", fetchedPokemonNameEntity?.languageCode)
            assertEquals("フシギダネ", fetchedPokemonNameEntity?.name)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertAndFetchPokemonGenera() {
        runTest {
            setUpInsertParentTable()

            val pokemonGeneraEntity: PokemonGeneraEntity = PokemonGeneraEntity(
                id = 2,
                pokemonId = 2,
                languageCode = "fe",
                genera = "さんぷるポケモン"
            )
            pokemonDao.insertPokemonGenera(pokemonGeneraEntity)

            val fetchPokemonGeneraEntity: PokemonGeneraEntity? = pokemonDao.fetchPokemonGeneraWithTranslation(2, "fe")
            assertEquals(2, fetchPokemonGeneraEntity?.id)
            assertEquals(2, fetchPokemonGeneraEntity?.pokemonId)
            assertEquals("fe", fetchPokemonGeneraEntity?.languageCode)
            assertEquals("さんぷるポケモン", fetchPokemonGeneraEntity?.genera)
        }
    }

    @Test
    @Throws(Exception::class)
    fun insertAndFetchPokemonFlavorText() {
        runTest {
            setUpInsertParentTable()

            val pokemonFlavorTextEntity: PokemonFlavorTextEntity = PokemonFlavorTextEntity(
                id = 3,
                pokemonId = 3,
                languageCode = "en",
                flavorText = "hello world!!"
            )
            pokemonDao.insertPokemonFlavorText(pokemonFlavorTextEntity)

            val fetchedPokemonFlavorTextEntity: PokemonFlavorTextEntity? = pokemonDao.fetchPokemonFlavorTextWithTranslation(3, "en")
            assertEquals(3, fetchedPokemonFlavorTextEntity?.id)
            assertEquals(3, fetchedPokemonFlavorTextEntity?.pokemonId)
            assertEquals("en", fetchedPokemonFlavorTextEntity?.languageCode)
            assertEquals("hello world!!", fetchedPokemonFlavorTextEntity?.flavorText)
        }
    }

    /**
     * 子テーブルを作成のため親テーブルを事前に作成しておく
     */
    private fun setUpInsertParentTable() {
        runTest {
            val parentTableData: List<PokemonEntity> = listOf(
                PokemonEntity(id = 1, name = "bulbasaur"),
                PokemonEntity(id = 2, name = "ivysaur"),
                PokemonEntity(id = 3, name = "venusaur")
            )
            pokemonDao.insertPokemonList(
                pokemonEntityList = parentTableData
            )
        }
    }
}