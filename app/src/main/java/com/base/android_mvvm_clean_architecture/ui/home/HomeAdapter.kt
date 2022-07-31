package com.base.android_mvvm_clean_architecture.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.base.android_mvvm_clean_architecture.databinding.ItemLocationListBinding
import kr.enjoyworks.room.common.GenericDiffUtil
import com.base.android_mvvm_clean_architecture.extension.inflater
import com.base.android_mvvm_clean_architecture.model.LocationEntity

class HomeAdapter : ListAdapter<LocationEntity, HomeAdapter.LocationVH>(GenericDiffUtil<LocationEntity>(
    areItemsTheSameCallback = { old, new -> old.woeid == new.woeid },
    areContentsTheSameCallback = { old, new -> old == new }
)) {

    override fun onBindViewHolder(holder: LocationVH, position: Int) {
        holder.bindData(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationVH {
        val binding = ItemLocationListBinding.inflate(parent.inflater, parent, false)
        return LocationVH(binding)
    }

    class LocationVH(var bind: ItemLocationListBinding) : RecyclerView.ViewHolder(bind.root) {

        fun bindData(location: LocationEntity) = with(bind) {
            tvPlace.text = location.title
            tvLocationType.text = location.locationType
            tvLatLong.text = location.lattLong
        }
    }
}
