package test.com.github.fj.android.awesomeapp.photo

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import com.github.fj.android.awesomeapp.core.photo.dto.ImageDetailDto
import test.com.github.fj.android.util.getRandomAlphaNumericString
import test.com.github.fj.android.util.randomUInt
import java.util.*

internal fun randomImageDetailDto() = ImageDetailDto(
    id = UUID.randomUUID().toString(),
    author = getRandomAlphaNumericString(16),
    width = randomUInt(1024),
    height = randomUInt(1024),
    url = getRandomAlphaNumericString(32),
    downloadUrl = getRandomAlphaNumericString(64)
)

internal fun randomImageDetailDtos(maxSize: Int = 8) = ArrayList<ImageDetailDto>().apply {
    repeat(randomUInt(maxSize)) {
        add(randomImageDetailDto())
    }
}
