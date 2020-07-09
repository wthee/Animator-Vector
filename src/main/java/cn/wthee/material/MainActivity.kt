package cn.wthee.material

import android.content.Context
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Build
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import cn.wthee.material.databinding.ActivityMainBinding
import com.tencent.bugly.Bugly
import com.tencent.bugly.beta.Beta
import com.tencent.bugly.beta.UpgradeInfo
import com.tencent.bugly.beta.ui.UILifecycleListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    private var isBack = false
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Bugly upgrade
        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog
        Beta.upgradeDialogLifecycleListener = object : UILifecycleListener<UpgradeInfo?> {
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onCreate(context: Context?, view: View, upgradeInfo: UpgradeInfo?) {
                Log.d("upgrade", "onCreate")
                val info = view.findViewWithTag<View>("info") as TextView
                val labelFeature = view.findViewWithTag<View>("label_feature") as TextView
                //获取更新信息
                val upgradeInfo = Beta.getUpgradeInfo()
                val oldVersionName = packageManager.getPackageInfo(packageName, 0).versionName
                val versionName = upgradeInfo.versionName.toString()
                val fileSize = String.format("%.2f", upgradeInfo.fileSize / 1024f / 1024f)
                val infoText = "$oldVersionName > $versionName   ${fileSize}M"
                val spaned = SpannableStringBuilder(infoText)
                spaned.setSpan(ForegroundColorSpan(resources.getColor(R.color.colorPrimary, null)), infoText.indexOfFirst { it == '>' } + 1, infoText.indexOfLast { it == ' ' }, SPAN_EXCLUSIVE_EXCLUSIVE)
                info.text = spaned
                labelFeature.text = SimpleDateFormat("yyyy-MM-dd").format(upgradeInfo.publishTime)
            }

            override fun onStart(context: Context?, view: View?, upgradeInfo: UpgradeInfo?) {
                Log.d("upgrade", "onStart")
            }

            override fun onResume(context: Context?, view: View?, upgradeInfo: UpgradeInfo?) {
                Log.d("upgrade", "onResume")
            }

            override fun onPause(context: Context?, view: View?, upgradeInfo: UpgradeInfo?) {
                Log.d("upgrade", "onPause")
            }

            override fun onStop(context: Context?, view: View?, upgradeInfo: UpgradeInfo?) {
                Log.d("upgrade", "onStop")
            }

            override fun onDestroy(context: Context?, view: View?, upgradeInfo: UpgradeInfo?) {
                Log.d("upgrade", "onDestory")
            }
        }

        //初始化
        Bugly.init(applicationContext, PrivateData.APP_ID, false)
        //toolbar 设置
        binding.toolbar.apply {
            iconLeft.setOnClickListener {
                //TODO Mock ANR
                try {
                    Thread.sleep(1000000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                Beta.checkAppUpgrade(true, false)
                iconLeft.isClickable = false
                val drawable = iconLeft.drawable
                if (drawable is AnimatedVectorDrawable) {
                    drawable.start()
                }
                //动画结束后，更新图标
                CoroutineScope(Dispatchers.IO).launch {
                    delay(resources.getInteger(R.integer.duration).toLong())
                    if (isBack) {
                        iconLeft.setImageDrawable(resources.getDrawable(R.drawable.vector_menu, null))
                    } else {
                        iconLeft.setImageDrawable(resources.getDrawable(R.drawable.vector_back, null))
                    }
                    isBack = !isBack
                    iconLeft.isClickable = true
                }
            }

        }
    }

}