package test.com.github.fj.android.awesomeapp.photo

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import com.github.fj.android.awesomeapp.core.photo.model.ImageDetail
import test.com.github.fj.android.util.getRandomAlphaNumericString
import test.com.github.fj.android.util.randomUInt
import java.util.*

fun randomImageDetail() = ImageDetail.create(
    id = UUID.randomUUID().toString(),
    author = getRandomAlphaNumericString(16),
    width = randomUInt(1024),
    height = randomUInt(1024),
    url = getRandomAlphaNumericString(32),
    downloadUrl = getRandomAlphaNumericString(64)
)

fun randomImageDetails(maxSize: Int = 8) = ArrayList<ImageDetail>().apply {
    repeat(randomUInt(maxSize)) {
        add(randomImageDetail())
    }
}
