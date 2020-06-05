package testcase.core.photo

import com.github.fj.android.awesomeapp.core.photo.datasource.PhotoApiDataSource
import com.github.fj.android.awesomeapp.core.photo.datasource.PhotoApiMemDataSource
import com.github.fj.android.awesomeapp.core.photo.repository.PhotoRepositoryImpl
import com.github.fj.android.awesomeapp.exception.DataExpiredException
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import test.com.github.fj.android.awesomeapp.photo.randomImageDetailDto
import testcase.UnitTestBase

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
class PhotoRepositoryImplTest : UnitTestBase() {
    private val mockApiDataSrc: PhotoApiDataSource = mock()
    private val mockMemCachedDataSrc: PhotoApiMemDataSource = mock()

    private lateinit var sut: PhotoRepositoryImpl

    @Before
    override fun setup() {
        this.sut = PhotoRepositoryImpl(mockApiDataSrc, mockMemCachedDataSrc)
    }

    @Test
    fun `loadList(true) invokes API call only once`() {
        // given:
        `when`(mockApiDataSrc.getAll()).thenReturn(Single.just(emptyList()))

        // when:
        val actual = sut.loadList(true)

        // then:
        val value = actual.test().singleValue()

        // expect:
        assertTrue(value.isEmpty())
        verify(mockApiDataSrc, times(1)).getAll()
    }

    @Test
    fun `loadList(true) saves its result in memory cache`() {
        // given:
        val mockedData = listOf(randomImageDetailDto())
        `when`(mockApiDataSrc.getAll()).thenReturn(Single.just(mockedData))

        // and:
        val actual = sut.loadList(true)

        // then:
        val value = actual.test().singleValue()

        // expect:
        assertEquals(1, value.size)
        verify(mockMemCachedDataSrc, times(1)).putAll(mockedData)
    }

    @Test
    fun `loadList(false) does not call API if data was previously cached`() {
        // given:
        val mockedData = listOf(randomImageDetailDto())
        `when`(mockMemCachedDataSrc.getAll()).thenReturn(mockedData)

        // when:
        val actual = sut.loadList(false)

        // then:
        val value = actual.test().singleValue()

        // expect:
        assertEquals(1, value.size)
        verify(mockApiDataSrc, times(0)).getAll()
    }

    @Test
    fun `loadList(false) calls API if access to cached was failed`() {
        // given:
        val mockedData = listOf(randomImageDetailDto())
        `when`(mockMemCachedDataSrc.getAll()).thenThrow(DataExpiredException())
        `when`(mockApiDataSrc.getAll()).thenReturn(Single.just(mockedData))

        // when:
        val actual = sut.loadList(false)

        // then:
        val value = actual.test().singleValue()

        // expect:
        assertEquals(1, value.size)
        verify(mockApiDataSrc, times(1)).getAll()
    }
}
