package testcase.core.photo

import com.github.fj.android.awesomeapp.core.photo.model.ImageDetail
import com.github.fj.android.awesomeapp.core.photo.repository.PhotoRepository
import com.github.fj.android.awesomeapp.core.photo.usecase.PhotoUseCaseImpl
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import test.com.github.fj.android.awesomeapp.photo.randomImageDetailDtos
import testcase.UnitTestBase

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
class PhotoUseCaseImplTest : UnitTestBase() {
    private val mockPhotoRepo: PhotoRepository = mock()
    private lateinit var sut: PhotoUseCaseImpl

    @Before
    override fun setup() {
        this.sut =
            PhotoUseCaseImpl(
                mockPhotoRepo
            )
    }

    @Test
    fun `load(strict = true) attempts no retries`() {
        // given:
        val raw = randomImageDetailDtos()
        val expected = raw.map { ImageDetail.from(it) }

        // and:
        `when`(mockPhotoRepo.loadList(anyBoolean())).thenReturn(Single.just(raw))

        // when:
        val result = sut.load(true).test()

        // then:
        val actual = result.singleValue()

        // expect:
        assertEquals(expected, actual)
        verify(mockPhotoRepo, times(1)).loadList(true)
    }

    @Test
    fun `non-strict load(forceRefresh = true) retries with other options if error in first try`() {
        // given:
        val raw = randomImageDetailDtos()
        val expected = raw.map { ImageDetail.from(it) }

        // and:
        `when`(mockPhotoRepo.loadList(true)).thenReturn(
            Single.error(UnsupportedOperationException("Impossible during tests"))
        )
        `when`(mockPhotoRepo.loadList(false)).thenReturn(Single.just(raw))

        // when:
        val result = sut.load(strict = false, forceRefresh = true).test()

        // then:
        val actual = result.singleValue()

        // expect:
        assertEquals(expected, actual)
        verify(mockPhotoRepo, times(2)).loadList(anyBoolean())
    }

    @Test
    fun `non-strict load(forceRefresh = false) simply returns non-up-to-date data`() {
        // given:
        val raw = randomImageDetailDtos()
        val expected = raw.map { ImageDetail.from(it) }

        // and:
        `when`(mockPhotoRepo.loadList(false)).thenReturn(Single.just(raw))

        // when:
        val result = sut.load(strict = false, forceRefresh = false).test()

        // then:
        val actual = result.singleValue()

        // expect:
        assertEquals(expected, actual)
        verify(mockPhotoRepo, times(1)).loadList(false)
    }
}
