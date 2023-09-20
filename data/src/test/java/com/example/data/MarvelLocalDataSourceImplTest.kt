package com.example.data

import com.example.data.local.dao.MarvelCharacterDao
import com.example.data.local.datasource.MarvelLocalDatasourceImpl
import com.example.data.local.dto.toDto
import com.example.domain.model.MarvelCharacter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`
import java.util.Date

@RunWith(MockitoJUnitRunner.Silent::class)
class MarvelLocalDataSourceImplTest {

    @Mock
    private lateinit var mockMarvelCharacterDao: MarvelCharacterDao
    private lateinit var mockMarvelLocalDataSource: MarvelLocalDatasourceImpl

    private val mockMarvelCharacter = MarvelCharacter(
        1,
        "Spider-Man",
        "this is a mock data",
        "https://mock.com",
        true,
        saveDate = Date()
    )

    private val mockMarvelCharacters = listOf(
        mockMarvelCharacter,
        mockMarvelCharacter,
        mockMarvelCharacter
    )

    private val mockMarvelCharacterLocalDtos = listOf(
        mockMarvelCharacter.toDto(),
        mockMarvelCharacter.toDto(),
        mockMarvelCharacter.toDto()
    )

        @Before
    fun setUp() {
        mockMarvelCharacterDao = mock(MarvelCharacterDao::class.java)
        mockMarvelLocalDataSource = MarvelLocalDatasourceImpl(mockMarvelCharacterDao)
    }

    @Test
    fun `SaveMarvelCharacter Successfully Saved MarvelCharacter`(): Unit = runBlocking {
        val mockData = mockMarvelCharacter.toDto()
        // given
        `when`(mockMarvelCharacterDao.saveMarvelCharacter(mockData)).thenReturn(Unit)
        // when
        mockMarvelLocalDataSource.saveMarvelCharacter(mockMarvelCharacter)
        // then
        verify(mockMarvelCharacterDao).saveMarvelCharacter(mockData)
    }

    @Test
    fun `DeleteMarvelCharacter Successfully Deleted MarvelCharacter`(): Unit = runBlocking {
        // given
        `when`(mockMarvelCharacterDao.deleteMarvelCharacter(1)).thenReturn(Unit)
        // when
        mockMarvelLocalDataSource.deleteMarvelCharacter(1)
        // then
        verify(mockMarvelCharacterDao).deleteMarvelCharacter(1)
    }

    @Test
    fun `GetMarvelCharactersAll Successfully got List of MarvelCharacters`(): Unit = runBlocking {
        // given
        `when`(mockMarvelCharacterDao.getMarvelAllCharacters()).thenReturn(mockMarvelCharacterLocalDtos)
        // when
        val result = mockMarvelLocalDataSource.getMarvelAllCharacters()
        // then
        assertEquals(mockMarvelCharacters, result)
    }

    @Test
    fun `GetMarvelCharactersAllFlow Successfully got List of MarvelCharacters`(): Unit = runBlocking {
        // given
        `when`(mockMarvelCharacterDao.getMarvelAllCharactersFlow()).thenReturn(flowOf(mockMarvelCharacterLocalDtos))
        // when
        val result = mockMarvelLocalDataSource.getMarvelAllCharactersFlow()
        // then
        assertEquals(mockMarvelCharacters, result.first())
    }
}