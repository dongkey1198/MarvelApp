package com.example.data

import com.example.data.remote.datasource.MarvelRemoteDataSourceImpl
import com.example.data.remote.dto.MarvelCharacterRemoteDto
import com.example.data.remote.dto.toDomain
import com.example.data.remote.service.MarvelApiService
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.Mockito.`when`

@RunWith(MockitoJUnitRunner.Silent::class)
class MarvelRemoteDataSourceImplTest {

    @Mock
    private lateinit var mockMarvelApiService: MarvelApiService
    private lateinit var mockMarvelRemoteDataSourceImpl: MarvelRemoteDataSourceImpl
    private val fakeMarvelCharacterRemoteDto = MarvelCharacterRemoteDto(
        MarvelCharacterRemoteDto.Data(
            listOf(
                MarvelCharacterRemoteDto.Result(
                    1,
                    "aaa",
                    "aaa",
                    MarvelCharacterRemoteDto.Thumbnail("", "")
                ),
                MarvelCharacterRemoteDto.Result(
                    1,
                    "aaa",
                    "aaa",
                    MarvelCharacterRemoteDto.Thumbnail("", "")
                )
            )
        )
    )

    @Before
    fun setUp() {
        mockMarvelApiService = mock(MarvelApiService::class.java)
        mockMarvelRemoteDataSourceImpl = MarvelRemoteDataSourceImpl(mockMarvelApiService)
    }

    @Test
    fun `FetchCharacters Successfully Fetched MarvelCharacters`(): Unit = runBlocking {
        val fakeMarvelCharacters = fakeMarvelCharacterRemoteDto.data.results.map { it.toDomain() }
        // given
        `when`(mockMarvelApiService.fetchCharacters(nameStartsWith = "", offset = 0))
            .thenReturn(fakeMarvelCharacterRemoteDto)
        // when
        val result = mockMarvelRemoteDataSourceImpl.fetchCharacters("", 0)
        // then
        assertEquals(fakeMarvelCharacters, result)
    }
}
