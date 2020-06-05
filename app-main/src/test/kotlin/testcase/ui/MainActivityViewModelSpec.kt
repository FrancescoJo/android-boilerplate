package testcase.ui

import com.github.fj.android.awesomeapp.core.photo.usecase.PhotoUseCase
import com.github.fj.android.awesomeapp.ui.MainActivityViewModel
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import test.com.github.fj.android.awesomeapp.photo.randomImageDetails
import testcase.AndroidTestBase

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
class MainActivityViewModelSpec : AndroidTestBase() {
    private val mockPhotoUc: PhotoUseCase = mock()

    private lateinit var sut: MainActivityViewModel

    @Before
    override fun setup() {
        super.setup()
        this.sut = MainActivityViewModel(mockPhotoUc)
    }

    @Test
    fun `user can observe photo list changes`() {
        // given:
        val expected = randomImageDetails()
        `when`(mockPhotoUc.load()).thenReturn(Single.just(expected))

        // when:
        val result = sut.observePhotos(true).test()
        super.flushAllSchedulers()

        // then:
        val actual = result.values()

        // expect:
        assertEquals(expected, actual)
    }

    @Test
    fun `user can observe error upon fetching photo list`() {
        // given:
        `when`(mockPhotoUc.load()).thenReturn(
            Single.error(
                UnsupportedOperationException("Test")
            )
        )

        // when:
        val result = sut.observePhotos(true).test()
        super.flushAllSchedulers()

        // then:
        val actual = result.errorCount()

        // expect:
        assertEquals(1, actual)
    }
}
