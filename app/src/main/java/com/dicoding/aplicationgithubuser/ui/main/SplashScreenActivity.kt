package com.dicoding.aplicationgithubuser.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.aplicationgithubuser.R

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val ivGithub: ImageView = findViewById(R.id.iv_splashGithub)

        ivGithub.alpha = 0f
        ivGithub.animate().setDuration(DURATION).alpha(1f).withEndAction {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }

    companion object {
        const val DURATION = 2000L
    }
}