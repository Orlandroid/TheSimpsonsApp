package com.example.data.simpsons

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.simpsons.Doc
import retrofit2.HttpException
import kotlin.math.max

class CharactersPagingSource(
    private val service: SimpsonsApi
) : PagingSource<Int, Doc>() {

    companion object {
        private const val START_PAGE = 1
    }

    override suspend fun load(
        params: LoadParams<Int>
    ): LoadResult<Int, Doc> {
        return try {
            val currentPage = params.key ?: START_PAGE
            val data = service.getCharacters(currentPage.toString()).docs
            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == START_PAGE) null else currentPage - 1,
                nextKey = if (data.isEmpty()) null else currentPage.plus(1)
            )
        } catch (e: Exception) {
            if (e is HttpException) {
                val errorString =
                    e.response()?.errorBody()?.byteStream()?.bufferedReader().use { it?.readText() }
                LoadResult.Error(Throwable(errorString))
            } else {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Doc>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }


    private fun ensureValidKey(key: Int) = max(START_PAGE, key)
}
