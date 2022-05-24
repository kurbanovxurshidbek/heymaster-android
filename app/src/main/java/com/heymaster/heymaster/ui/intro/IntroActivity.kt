package com.heymaster.heymaster.ui.intro

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.viewpager2.widget.ViewPager2
import com.heymaster.heymaster.R
import com.heymaster.heymaster.SharedPref
import com.heymaster.heymaster.adapters.viewpagers.IntroPagerAdapter
import com.heymaster.heymaster.databinding.ActivityIntroBinding
import com.heymaster.heymaster.ui.global.BaseActivity
import com.heymaster.heymaster.ui.intro.fragment.FirstIntroFragment
import com.heymaster.heymaster.ui.intro.fragment.SecondIntroFragment
import com.heymaster.heymaster.ui.intro.fragment.ThirdIntroFragment


class IntroActivity : BaseActivity() {

    private val binding by lazy { ActivityIntroBinding.inflate(layoutInflater) }

    private lateinit var adapter: IntroPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
        //hideSystemBars()
        //hideStatusBarAndBottomBar()
        if (SharedPref(this).getSaved()){
            callLoginActivity()
        }
        adapter = IntroPagerAdapter(supportFragmentManager, lifecycle)
        setupViewPager()



        binding.btnGetStarted.setOnClickListener {
            SharedPref(this).isSaved(true)
            callLoginActivity()
        }
    }

    private fun setupViewPager() {
        adapter.addFragment(FirstIntroFragment())
        adapter.addFragment(SecondIntroFragment())
        adapter.addFragment(ThirdIntroFragment())

        binding.viewPager.adapter = adapter
        binding.viewPager.setCurrentItem(0, true)
        binding.dotsIndicator.setViewPager2(binding.viewPager)

        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when(position) {
                    0 -> {
                        binding.tvIntroTitle.text = getString(R.string.first_intro_title)
                        binding.tvIntroBody.text = getString(R.string.first_intro_body)
                        binding.btnGetStarted.visibility = View.GONE
                    }
                    1 -> {
                        binding.tvIntroTitle.text = getString(R.string.second_intro_title)
                        binding.tvIntroBody.text = getString(R.string.second_intro_body)
                        binding.btnGetStarted.visibility = View.GONE
                    }
                    2 -> {
                        binding.tvIntroTitle.text = getString(R.string.third_intro_title)
                        binding.tvIntroBody.text = getString(R.string.third_intro_body)
                        binding.btnGetStarted.visibility = View.VISIBLE
                    }
                }
            }
        })
    }

    private fun hideSystemBars() {
        val windowInsetsController = ViewCompat.getWindowInsetsController(
            window.decorView) ?: return
        // Configure the behavior of the hidden system bars
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        // Hide both the status bar and the navigation bar
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())
    }

    private fun hideStatusBarAndBottomBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            this.window.decorView.windowInsetsController!!.hide(
                android.view.WindowInsets.Type.statusBars()
            )
        } else {
            this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }

    }

    fun showStatusBarAndBottomBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.decorView.windowInsetsController!!.show(
                android.view.WindowInsets.Type.statusBars()
            )
        } else {
            window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
    }
}
