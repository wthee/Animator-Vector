package cn.wthee.material

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.view.View
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import cn.wthee.material.databinding.ActivityMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    private var isBack = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //toolbar 设置
        binding.toolbar.apply {
            iconLeft.setOnClickListener {
                iconLeft.isClickable = false
                val drawable = iconLeft.drawable
                if (drawable is AnimatedVectorDrawable){
                    drawable.start()
                }
                //动画结束后，更新图标
                CoroutineScope(Dispatchers.IO).launch {
                    delay(resources.getInteger(R.integer.duration).toLong())
                    if(isBack){
                        iconLeft.setImageDrawable(resources.getDrawable(R.drawable.vector_menu, null))
                    }else{
                        iconLeft.setImageDrawable(resources.getDrawable(R.drawable.vector_back, null))
                    }
                    isBack = !isBack
                    iconLeft.isClickable = true
                }
            }

            iconRight.setOnClickListener {
//                bottomSheetDialog.show()
//                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                }
        }



    }


}