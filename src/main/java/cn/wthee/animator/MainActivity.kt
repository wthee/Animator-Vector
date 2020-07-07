package cn.wthee.animator

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var behavior: BottomSheetBehavior<View>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var isBack = true
        val btn = findViewById<ImageButton>(R.id.btn)
        btn.setOnClickListener {
            if(isBack){
                hideBottomSheet()
            }else{
                showBottomSheet()
            }
            btn.isClickable = false
            val drawable = btn.drawable
            if (drawable is AnimatedVectorDrawable){
                drawable.start()
            }
            //动画结束后，更新图标
            CoroutineScope(Dispatchers.IO).launch {
                delay(resources.getInteger(R.integer.duration).toLong())
                if(isBack){
                    btn.setImageDrawable(resources.getDrawable(R.drawable.vector_menu, null))
                }else{
                    btn.setImageDrawable(resources.getDrawable(R.drawable.vector_back, null))
                }
                isBack = !isBack
                btn.isClickable = true
            }
        }

        // The View with the BottomSheetBehavior
        val coordinatorLayout = findViewById<View>(R.id.cl)
        val bottomSheet: View = coordinatorLayout.findViewById(R.id.bottom_sheet)
        behavior = BottomSheetBehavior.from(bottomSheet)
        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }

            override fun onStateChanged(bottomSheet: View, newState: Int) {
//                TODO("Not yet implemented")
            }
        })
    }

    fun showBottomSheet() {
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    fun hideBottomSheet() {
        behavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }
}