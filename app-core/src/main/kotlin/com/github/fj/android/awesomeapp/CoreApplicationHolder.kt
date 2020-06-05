package com.github.fj.android.awesomeapp

import com.github.fj.android.SingletonHolderTemplate
import com.github.fj.android.awesomeapp.inject.module.RetrofitApiServiceFactory

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
object CoreApplicationHolder : SingletonHolderTemplate<CoreApplication>() {
    override fun setInstance(instance: CoreApplication?) {
        super.setInstance(instance)
        if (instance != null) {
            RetrofitApiServiceFactory.init(instance)
        }
    }
}
