package test.com.github.fj.android.util

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
import java.io.File
import java.io.FileNotFoundException

@Throws(FileNotFoundException::class)
fun getClasspathResource(resourceName: String): File {
    return NS.getClasspathResource(resourceName)
}

/**
 * Dummy namespace implementation for classpath execution context.
 */
private object NS {
    fun getClasspathResource(name: String): File {
        val path = "/$name"
        val resource = NS::class.java.getResource(path) ?: throw exceptionWith(name)

        return File(resource.file).apply {
            if (!exists()) {
                throw exceptionWith(name)
            }
        }
    }

    private fun exceptionWith(name: String) = FileNotFoundException(
        "No classpath resource '$name' is found. Check target file is relatively placed under 'src/test/resources'."
    )
}
