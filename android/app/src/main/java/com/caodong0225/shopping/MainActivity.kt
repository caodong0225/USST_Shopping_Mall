package com.caodong0225.shopping

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.caodong0225.shopping.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private val nicknameReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            updateNickname()
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_users
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        // 更新导航头中的用户昵称
        val headerView = navView.getHeaderView(0) // 获取导航头
        val textView = headerView.findViewById<TextView>(R.id.textView)
        val settings = Settings(this) // 读取全局设置
        textView.text = settings.nickname ?: "未登录" // 显示昵称或默认文本
        // 注册广播接收器
        registerReceiver(nicknameReceiver, IntentFilter("com.caodong0225.UPDATE_NICKNAME"),
            RECEIVER_NOT_EXPORTED
        )
        updateNickname() // 初始化昵称
        // 动态修改菜单项
        val menu = navView.menu
        val userMenuItem = menu.findItem(R.id.nav_users)

        if (settings.nickname!=null) { // 根据登录状态更新菜单项
            userMenuItem.title = getString(R.string.menu_logout) // 更新标题
            userMenuItem.icon = getDrawable(R.drawable.ic_menu_slideshow) // 更新图标（需准备登出图标）
            userMenuItem.setOnMenuItemClickListener {
                handleLogout() // 登出逻辑
                true
            }
        } else {
            userMenuItem.title = getString(R.string.menu_users)
            userMenuItem.icon = getDrawable(R.drawable.ic_menu_slideshow)
            userMenuItem.setOnMenuItemClickListener(null) // 恢复默认行为
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        // menuInflater.inflate(R.layout.activity_login, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun updateNickname() {
        val headerView = binding.navView.getHeaderView(0)
        val textView = headerView.findViewById<TextView>(R.id.textView)
        val settings = Settings(this)
        textView.text = settings.nickname ?: "未登录"
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(nicknameReceiver) // 避免内存泄漏
    }

    private fun handleLogout() {
        // 清除用户登录状态
        val settings = Settings(this) // 传入当前上下文
        settings.clear() // 清除用户数据
        sendBroadcast(Intent("com.caodong0225.UPDATE_NICKNAME")) // 发送广播
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}