package cn.wthee.material

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.coordinatorlayout.widget.CoordinatorLayout
import cn.wthee.material.PhotoFragment.Companion.handler
import cn.wthee.material.databinding.LayoutBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class BottomSheetDialogFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = LayoutBottomSheetBinding.inflate(inflater, container, false)
        binding.toolbar.setOnClickListener {
            handler.sendEmptyMessage(1)
        }
        return binding.root
    }

    override fun setupDialog(dialog: Dialog, style: Int) {
        val v: View = LayoutInflater.from(activity).inflate(R.layout.layout_bottom_sheet, null)
        dialog.setContentView(v)

        val params = (v.parent as View).layoutParams as CoordinatorLayout.LayoutParams
        val behavior = params.behavior as BottomSheetBehavior<View>
        behavior.peekHeight = 700
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED

    }

}