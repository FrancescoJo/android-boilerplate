package test.com.github.fj.android.awesomeapp.dto.photo

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import com.github.fj.android.awesomeapp.dto.photo.ImageDetailDto
import test.com.github.fj.android.util.getRandomAlphaNumericString
import test.com.github.fj.android.util.randomUInt
import java.util.*

fun randomImageDetailDto() = ImageDetailDto(
    id = UUID.randomUUID().toString(),
    author = getRandomAlphaNumericString(16),
    width = randomUInt(1024),
    height = randomUInt(1024),
    url = getRandomAlphaNumericString(32),
    downloadUrl = getRandomAlphaNumericString(64)
)

fun randomImageDetailDtos(maxSize: Int = 8) = ArrayList<ImageDetailDto>().apply {
    repeat(randomUInt(maxSize)) {
        add(randomImageDetailDto())
    }
}
