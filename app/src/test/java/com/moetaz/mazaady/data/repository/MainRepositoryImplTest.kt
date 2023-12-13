package com.moetaz.mazaady.data.repository


import android.util.Log
import com.google.common.truth.Truth
import com.moetaz.mazaady.data.models.main.GetAllCategoriesDto
import com.moetaz.mazaady.data.models.main.GetAllCategoriesResponseDto
import com.moetaz.mazaady.data.remote.FakeMainService
import com.moetaz.mazaady.domain.base.Resource
import io.mockk.every
import io.mockk.mockkStatic
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class MainRepositoryImplTest {

    private lateinit var repo: MainRepositoryImpl
    private lateinit var fakeService: FakeMainService

    @Before
    fun setUp() {
        fakeService = FakeMainService()
        repo = MainRepositoryImpl(fakeService)

        fakeService.catRes =
            GetAllCategoriesResponseDto(data = GetAllCategoriesDto(adsBanners = listOf(),
                categories = listOf(),
                googleVersion = null,
                huaweiVersion = null,
                iosLatestVersion = null,
                iosVersion = null,
                statistics = null)).apply {
                msg = ""
                code = 200
            }

        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
    }

    @Test
    fun `Response type is Success Resource, return correct`(): Unit = runBlocking {
        val response = repo.getAllCategories()
        Truth.assertThat(response).isInstanceOf(Resource.Success::class.java)
    }

    @Test
    fun `Response type is Failed Resource, Wrong Http Code, return correct`(): Unit = runBlocking {
        fakeService.catRes =
            GetAllCategoriesResponseDto(data = GetAllCategoriesDto(adsBanners = listOf(),
                categories = listOf(),
                googleVersion = null,
                huaweiVersion = null,
                iosLatestVersion = null,
                iosVersion = null,
                statistics = null)).apply {
                msg = ""
                code = 400
            }

        val response = repo.getAllCategories()
        Truth.assertThat(response).isInstanceOf(Resource.Failure::class.java)
    }


}