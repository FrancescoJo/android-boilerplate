package com.github.fj.android.awesomeapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.fj.android.awesomeapp.R
import com.github.fj.android.awesomeapp.model.photo.ImageDetail
import com.github.fj.android.display.dpToPx
import com.github.fj.android.util.getDimen
import com.github.fj.android.util.getString
import kotlinx.android.synthetic.main.item_photo_list.view.*

/**
 * @author Francesco Jo(nimbusob@gmail.com)
 * @since 12 - Nov - 2018
 */
class PhotoListAdapter : RecyclerView.Adapter<ItemVH>() {
    private val list = ArrayList<ImageDetail>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemVH {
        val li = LayoutInflater.from(parent.context)

        return ItemVH(li.inflate(R.layout.item_photo_list, parent, false))
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ItemVH, position: Int) {
        holder.draw(list[position])
    }

    fun add(item: ImageDetail) {
        list.add(item)
        notifyItemInserted(list.size - 1)
    }
}

class ItemVH(private val rootView: View) : RecyclerView.ViewHolder(rootView) {
    fun draw(data: ImageDetail) = with(rootView) {
        vPhotoThumb.setImageURI(
            data.thumbnailUrl(THUMB_WIDTH.dpToPx(), THUMB_HEIGHT.dpToPx())
        )
        vPhotoAuthor.text = getString(R.string.item_photo_list_author, data.author)
        vPhotoDescription.text = getString(
            R.string.item_photo_list_info, data.width, data.height, data.id
        )
    }

    companion object {
        private val THUMB_WIDTH = getDimen(R.dimen.item_photo_thumb_width)
        private val THUMB_HEIGHT = getDimen(R.dimen.item_photo_thumb_height)
    }
}
