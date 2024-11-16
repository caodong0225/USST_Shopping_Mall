package com.caodong0225.shopping.ui.users

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.caodong0225.shopping.R
import com.caodong0225.shopping.databinding.FragmentUsersBinding
import com.caodong0225.shopping.ui.register.RegisterActivity
import com.google.android.material.textfield.TextInputEditText

class UsersFragment : Fragment() {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val usersViewModel =
            ViewModelProvider(this).get(UsersViewModel::class.java)

        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // 绑定登录页面中的输入框
        val usernameEditText: TextInputEditText? = root.findViewById(R.id.username_editext)
        val passwordEditText: TextInputEditText? = root.findViewById(R.id.password_editext)

        // 示例：点击登录按钮时获取输入内容
        val loginButton: View? = root.findViewById(R.id.login)
        loginButton?.setOnClickListener {
            val username = usernameEditText?.text.toString()
            val password = passwordEditText?.text.toString()
            Toast.makeText(context, "用户名: $username\n密码: $password", Toast.LENGTH_SHORT).show()
        }

        // 注册按钮跳转到注册页面
        val registerButton: View? = root.findViewById(R.id.register_page)
        registerButton?.setOnClickListener {
            val intent = Intent(context, RegisterActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}