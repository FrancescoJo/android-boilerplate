package com.github.fj.android.awesomeapp.ui

import android.os.Bundle
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.fj.android.awesomeapp.R
import com.github.fj.android.awesomeapp.lifecycle.Dagger2ViewModelRxActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
class MainActivity : Dagger2ViewModelRxActivity<MainActivityViewModel>() {
    override val viewModelType = MainActivityViewModel::class

    private lateinit var photoListAdapter: PhotoListAdapter

    private var forceRefresh = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vgPhotoList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = PhotoListAdapter().also {
                this@MainActivity.photoListAdapter = it
            }
        }
    }

    override fun onStart() {
        super.onStart()

        load()
    }

    private fun load() {
        viewModel.observePhotos(forceRefresh)
            .subscribe({
                photoListAdapter.add(it)
            }, {
                Snackbar.make(vgContentWrapper, R.string.err_general_error, Snackbar.LENGTH_LONG)
                    .setAction(R.string.txt_retry) { load() }
                    .show()
            })
            .until(Lifecycle.Event.ON_STOP)
    }
}
