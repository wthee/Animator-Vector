package cn.wthee.animator

import android.graphics.drawable.AnimatedVectorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var isBack = true
        val btn = findViewById<ImageButton>(R.id.btn)
        btn.setOnClickListener {
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
    }
}