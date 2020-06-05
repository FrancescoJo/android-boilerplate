package com.github.fj.android.awesomeapp.ui

import com.github.fj.android.annotation.AllOpen
import com.github.fj.android.awesomeapp.lifecycle.RxViewModel
import com.github.fj.android.awesomeapp.core.photo.model.ImageDetail
import com.github.fj.android.awesomeapp.core.photo.usecase.PhotoUseCase
import com.github.fj.android.rx.RecreatingPublishSubject
import com.github.fj.android.rx.onIoThread
import com.github.fj.android.rx.toAndroidAsync
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
@Singleton
@AllOpen
class MainActivityViewModel @Inject constructor(
    private val photoUc: PhotoUseCase
) : RxViewModel() {
    private val photoStream: PublishSubject<ImageDetail> by RecreatingPublishSubject()

    fun observePhotos(forceRefresh: Boolean): Observable<ImageDetail> {
        if (forceRefresh) {
            photoUc.load()
                .onIoThread()
                .relayTo(photoStream)
                .untilCleared()
        }

        return photoStream.toAndroidAsync()
    }
}
