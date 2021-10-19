package io.homeassistant.companion.android.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import io.homeassistant.companion.android.R
import io.homeassistant.companion.android.common.data.integration.Entity
import io.homeassistant.companion.android.viewHolders.ButtonViewHolder
import io.homeassistant.companion.android.viewHolders.EntityButtonViewHolder
import io.homeassistant.companion.android.viewHolders.HeaderViewHolder
import io.homeassistant.companion.android.viewHolders.LoadingViewHolder
import kotlin.collections.HashMap
import kotlin.math.max

class HomeListAdapter() : RecyclerView.Adapter<ViewHolder>() {

    lateinit var onSceneClicked: (Entity<Any>) -> Unit
    lateinit var onButtonClicked: (String) -> Unit

    val scenes = arrayListOf<Entity<Any>>()
    val scripts = arrayListOf<Entity<Any>>()
    val lights = arrayListOf<Entity<Any>>()
    val covers = arrayListOf<Entity<Any>>()
    val dataList = mutableListOf<Entity<Any>>()
    var hashMap : HashMap<Int, Any>
            = HashMap<Int, Any> ()

    companion object {
        private const val TYPE_SCENE = 1 // Used for scenes and scripts
        private const val TYPE_HEADER = 2
        private const val TYPE_LOADING = 3
        private const val TYPE_BUTTON = 4

        const val BUTTON_ID_LOGOUT: String = "logout"

        private const val TAG = "HomeListAdapter"
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return when (viewType) {
            TYPE_SCENE -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.listitem_entity_button, parent, false)
                EntityButtonViewHolder(view, onSceneClicked)
            }
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.listitem_header, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_BUTTON -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.listitem_button, parent, false)
                ButtonViewHolder(view, onButtonClicked)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.listitem_loading, parent, false)
                LoadingViewHolder(view)
            }
        }

    }


    // Replace the contents of a view (invoked by the layout manager)
//    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
//
//        // Get element from your dataset at this position and replace the
//        // contents of the view with that element
//        viewHolder.itemViewType.
//    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        createDataList()



        for ((key, value) in hashMap) {
           if(key.equals(TYPE_HEADER) ){
               if (holder is HeaderViewHolder){
                   holder.headerTextView.setText(R.string.scenes)
               }

           }else if (key.equals(TYPE_BUTTON)){
               if (holder is EntityButtonViewHolder) {
                   holder.entity = value as Entity<Any>

               }



           }
        }

//        try {
//            if (holder is EntityButtonViewHolder) {
//                if (position < scenes.size + 1) {
//                    holder.entity = scenes[position - 1]
//                } else if (position > scenes.size + 1 + scripts.size + 1) {
//                    holder.entity = lights[position - 3 - scenes.size - scripts.size]
//                } else
//                    holder.entity = scripts[position - 2 - scenes.size]
//            } else if (holder is HeaderViewHolder) {
//                when (position) {
//                    0 -> holder.headerTextView.setText(R.string.scenes)
//                    scenes.size + 1 -> holder.headerTextView.setText(R.string.scripts)
//                    scenes.size + scripts.size + 2 -> holder.headerTextView.setText(R.string.lights)
//                    else -> holder.headerTextView.setText(R.string.other)
//                }
//            } else if (holder is ButtonViewHolder) {
//                holder.txtName.setText(R.string.logout)
//                holder.id = BUTTON_ID_LOGOUT
//                holder.color = R.color.colorWarning
//            }
//        } catch (e: Exception) {
//            Log.e(TAG, "Unable to add entities to list", e)
//        }
    }


    fun createDataList(){
        if (scenes.isNotEmpty()){
            hashMap.put(TYPE_HEADER, R.string.scene)
            for(dataEntiry in scenes){
                hashMap.put( TYPE_BUTTON, dataEntiry)
            }
        }
        if (scripts.isNotEmpty()){
            hashMap.put(TYPE_HEADER, R.string.scripts)
            for(dataEntiry in scripts) {
                hashMap.put(TYPE_BUTTON, dataEntiry)
            }
        }
        if (lights.isNotEmpty()){
            hashMap.put(TYPE_HEADER, R.string.lights)
            for(dataEntiry in lights) {
                hashMap.put(TYPE_BUTTON, dataEntiry)
            }
        }




    }

    override fun getItemCount() = max(scenes.size + scripts.size + lights.size + 5, 7)

//    override fun getItemViewType(position: Int): Int {
//        /*
//        Current layout contains of three sections:
//        # Scenes
//        - scene 1
//        - scene 2
//        - etc
//        # Scripts
//        - script 1
//        - script 2
//        - etc
//        # Other
//        - Logout
//
//        Envisioned final layout:
//        # Scenes
//        - 3 favorite scenes
//        - More scenes button
//        # Devices
//        - 3 favorite devices
//        - More devices button
//        # Scripts
//        - 3 favorite scripts
//        - More scripts button
//        # Other
//        - Settings
//         */
//
//        return when {
//            position == 0 || position == scenes.size + 1 || position == scenes.size + scripts.size + 2 || position == itemCount - 2 -> TYPE_HEADER
//            position == itemCount - 1 -> TYPE_BUTTON
//            position < scenes.size + 1 && scenes.size > 0 -> TYPE_SCENE
//            position > scenes.size + 1 && scripts.size > 0 -> TYPE_SCENE
//            else -> TYPE_LOADING
//        }
//    }
}
