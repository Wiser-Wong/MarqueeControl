package com.wiser.marqueecontrol

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

class MainActivity : AppCompatActivity() {

    private var isPause = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<ScrollImageSwitcher>(R.id.imageSwitcher)?.setResIds(
            mutableListOf(
                R.drawable.test_story_ip,
                R.drawable.test_story_ip,
                R.drawable.test_story_ip,
                R.drawable.test_story_ip
            )
        )

        findViewById<ScrollImageSwitcher>(R.id.imageSwitcherNet)?.apply {
            val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)

            setLoadNetImageListener(object : LoadNetImageListener {
                override fun loadNetImage(url: String) {
                    Glide.with(this@apply).asDrawable().load(url).apply(options)
                        .skipMemoryCache(true)
                        .into(object : CustomTarget<Drawable>() {
                            override fun onLoadCleared(placeholder: Drawable?) {

                            }

                            override fun onResourceReady(
                                resource: Drawable,
                                transition: Transition<in Drawable>?
                            ) {
                                getImageSwitcher()?.setImageDrawable(resource)
                                resetTranslation()
                            }
                        })
                }
            })
            setUrls(
                mutableListOf(
                    "https://picx.zhimg.com/v2-3b4fc7e3a1195a081d0259246c38debc_720w.jpg?source=172ae18b",
                    "https://picx.zhimg.com/v2-3b4fc7e3a1195a081d0259246c38debc_720w.jpg?source=172ae18b",
                    "https://picx.zhimg.com/v2-3b4fc7e3a1195a081d0259246c38debc_720w.jpg?source=172ae18b",
                    "https://picx.zhimg.com/v2-3b4fc7e3a1195a081d0259246c38debc_720w.jpg?source=172ae18b"
                )
            )
            currentChildView()?.setOnClickListener {
                if (!isPause) {
                    isPause = true
                    pauseScroll()
                } else {
                    isPause = false
                    resumeScroll()
                }
            }
        }
    }
}