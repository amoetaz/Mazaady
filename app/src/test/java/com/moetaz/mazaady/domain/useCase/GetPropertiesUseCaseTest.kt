package com.moetaz.mazaady.domain.useCase

import com.google.common.truth.Truth.assertThat
import com.moetaz.mazaady.data.repository.FakeMainRepository
import com.moetaz.mazaady.domain.models.Property
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetPropertiesUseCaseTest {

    private lateinit var getPropertiesUseCase: GetPropertiesUseCase
    private lateinit var repository: FakeMainRepository


    @Before
    fun setUp() {
        repository = FakeMainRepository()
        getPropertiesUseCase = GetPropertiesUseCase(repository)

        (1..5).forEachIndexed { index, item ->
            repository.propertiesList.add(Property(description = null,
                id = index,
                name = "$index name",
                options = listOf(),
                list = null,
                otherValue = null,
                parent = null,
                slug = null,
                type = null,
                value = null
            ))
        }

        repository.catList.shuffle()
    }

    @Test
    fun `check if id is Valid , id is not valid, return success`(): Unit = runBlocking {
        getPropertiesUseCase(-1)
            .catch {
                assertThat(it is GetPropertiesExceptions.SubCategoryIdException).isTrue()
            }.collectLatest {
                assert(false)
            }
    }

    @Test
    fun `check if id is Valid , id is valid, return success`(): Unit = runBlocking {
        getPropertiesUseCase(5)
            .catch {
                assert(false)
            }.collect {
                assert(true)
            }
    }

    @Test
    fun `Get Properties, return correct`(): Unit = runBlocking {
        val response = getPropertiesUseCase(1).last()
        val list = response.toData()

        assertThat(list.isNullOrEmpty()).isFalse()
    }

}