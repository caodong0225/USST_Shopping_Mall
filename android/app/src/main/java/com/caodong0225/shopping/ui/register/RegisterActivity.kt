package com.caodong0225.shopping.ui.register

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.caodong0225.shopping.R
import com.caodong0225.shopping.model.UsersRegisterRequest
import com.caodong0225.shopping.repository.UsersRepository
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    private val usersRepository = UsersRepository()  // 实例化 AuthRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // 获取输入框和按钮的引用
        val usernameEditText: TextInputEditText = findViewById(R.id.username_editext)
        val nicknameEditText: TextInputEditText = findViewById(R.id.nickname_editext)
        val passwordEditText: TextInputEditText = findViewById(R.id.password_editext)
        val registerButton: MaterialButton = findViewById(R.id.login)

        // 设置注册按钮点击事件
        registerButton.setOnClickListener {
            // 获取输入框内容
            val username = usernameEditText.text.toString()
            val nickname = nicknameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // 检查输入是否为空
            if (username.isBlank() || nickname.isBlank() || password.isBlank()) {
                Toast.makeText(this, "请填写所有必填项！", Toast.LENGTH_SHORT).show()
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    val response = usersRepository.registerUser(
                        UsersRegisterRequest(
                            username,
                            nickname,
                            password
                        )
                    )
                    if(response?.code == 200) {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterActivity, "注册成功", Toast.LENGTH_SHORT).show()
                            // 跳转到主页面
                            finish()
                        }
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@RegisterActivity, "注册失败", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

                // 示例：可以在这里添加保存数据或跳转逻辑
                // saveUserData(username, nickname, password)
                // finish() // 关闭注册页面返回上一个页面
            }
        }
    }
}
