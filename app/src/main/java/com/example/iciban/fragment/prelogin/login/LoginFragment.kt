package com.example.iciban.fragment.prelogin.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.iciban.R
import com.example.iciban.data.ResultState
import com.example.iciban.databinding.FragmentLoginBinding
import com.example.iciban.utils.getErrorMessage
import com.example.iciban.utils.showSnackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel : LoginViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initView()
    }
    private fun initView(){
        initViewBtnValid()
        binding.navRegister.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_registerFragment)
        }
        binding.btnLogin.setOnClickListener{
            val username = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.makeLogin(username,password)
        }
    }

    private fun initViewBtnValid(){
        binding.btnLogin.isEnabled = false
        binding.etEmail.doOnTextChanged{
            it, _, _, _ ->
            val text = it.toString()
            binding.emailLayout.error =
                if (!isUsernameValid(text) && text.isNotEmpty()) "Invalid username" else null
            binding.btnLogin.isEnabled = true
            updateButtonState()
        }
        binding.etPassword.doOnTextChanged { it, _, _, _ ->
            val text = it.toString()
            binding.passwordLayout.error =
                if (!isPasswordValid(text) && text.isNotEmpty()) getString(R.string.password_invalid) else null
            binding.btnLogin.isEnabled = true
            updateButtonState()
        }
    }

    private fun updateButtonState() {
        val username = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val isUsernameValid = isUsernameValid(username)
        val isPasswordValid = isPasswordValid(password)
        binding.btnLogin.isEnabled = isUsernameValid && isPasswordValid
    }

    private fun isUsernameValid(username: String): Boolean {
        return username.length >= 3
    }
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    private fun  observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle((Lifecycle.State.STARTED)) {
                viewModel.loginState.collect { state ->
                    if (state != null) {
                        when (state) {
                            is ResultState.Error -> {
                                binding.root.showSnackbar(state.error.getErrorMessage())
                                binding.progressBar.visibility = View.INVISIBLE
                                binding.btnLogin.visibility = View.VISIBLE
                            }

                            is ResultState.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.btnLogin.visibility = View.INVISIBLE
                            }

                            is ResultState.Succes -> {
                                findNavController().navigate(R.id.action_login_to_homeFragment)
                            }
                        }
                    }
                }
            }
        }
    }
}