package test.com.github.fj.android.util

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import org.mockito.Mockito
import org.mockito.plugins.MockMaker

/**
 * @return whether given object [instance] is mock object created by [Mockito] or not.
 *
 * This logic might not work if Mockito2 version is changed or [MockMaker]
 * implementation is changed.
 */
fun isMock(instance: Any) =
    instance::class.simpleName?.contains("\$MockitoMock") == true
