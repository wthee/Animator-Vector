package cn.wthee.material

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cn.wthee.material.dummy.DummyContent

/**
 * A fragment representing a list of Items.
 */
class PhotoFragment : Fragment() {

    private var columnCount = 2


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_photo_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = PhotoAdapter(DummyContent.ITEMS, parentFragmentManager)
            }
        }

        //创建一个Handler
        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    1 -> {
                        Toast.makeText(requireContext(), "success", Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                    }
                }
            }
        }
        return view
    }

    companion object {
        lateinit var handler: Handler
    }
}