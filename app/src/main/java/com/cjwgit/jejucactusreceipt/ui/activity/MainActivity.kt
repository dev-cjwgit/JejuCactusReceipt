package com.cjwgit.jejucactusreceipt.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.cjwgit.jejucactusreceipt.R
import com.cjwgit.jejucactusreceipt.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        // NavController 찾기
        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        // BottomNavigationView를 NavController와 설정
        navView.setupWithNavController(navController)

        requestExternalStorage()
    }

    private fun requestExternalStorage() {
        if (!Environment.isExternalStorageManager()) {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.data = Uri.parse("package:" + applicationContext.packageName)
            startActivity(intent)
        }
    }
}