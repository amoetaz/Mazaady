package com.moetaz.mazaady.domain.useCase

import com.google.common.truth.Truth.assertThat
import com.moetaz.mazaady.data.repository.FakeMainRepository
import com.moetaz.mazaady.domain.base.Resource
import com.moetaz.mazaady.domain.models.Categories
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetCategoriesUseCaseTest {

    private lateinit var getCategoriesUseCase: GetCategoriesUseCase
    private lateinit var repository: FakeMainRepository


    @Before
    fun setUp() {
        repository = FakeMainRepository()
        getCategoriesUseCase = GetCategoriesUseCase(repository)

        (1..5).forEachIndexed { index, item ->
            val children = arrayListOf<Categories.Category.Children>().apply {
                (1..3).forEachIndexed { index, i ->
                    add(Categories.Category.Children(children = null,
                        id = index,
                        description = "adada asd--$index",
                        name = "$index subCat",
                        circleIcon = null, disableShipping = null, image = null, slug = null
                    ))
                }
            }
            repository.catList.add(Categories.Category(
                children = children,
                circleIcon = "www.icon$index.com",
                description = "asdjka akdjaklsd--$index",
                id = index,
                name = "$index name",
                disableShipping = null, image = null, slug = null
            ))
        }

        repository.catList.shuffle()
    }

    @Test
    fun `Response type is Success Resource, return correct`(): Unit = runBlocking {
        val response = getCategoriesUseCase().last()

        println("moetaz: Get categories, return correct: $response")
        assertThat(response).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `Get categories, return correct`(): Unit = runBlocking {
        val response = getCategoriesUseCase().last()
        val list = response.toData()

        assertThat(list.isNullOrEmpty()).isFalse()
    }

}



