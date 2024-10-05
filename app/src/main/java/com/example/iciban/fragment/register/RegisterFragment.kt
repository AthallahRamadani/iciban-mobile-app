package com.example.iciban.fragment.register

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.iciban.R
import com.example.iciban.data.ResultState
import com.example.iciban.databinding.FragmentRegisterBinding
import com.example.iciban.utils.getErrorMessage
import com.example.iciban.utils.showSnackbar
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegisterViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initView()
    }

    private fun initView() {
        initViewBtnValid()
        binding.navLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_login)
        }

        binding.btnRegister.setOnClickListener {
            val username = binding.etUsername.text.toString()
            val email = binding.etEmail.text.toString()
            val pass = binding.etPassword.text.toString()
            viewModel.makeRegister(username, email, pass)
        }
    }

    private fun initViewBtnValid() {
        binding.btnRegister.isEnabled = false
        binding.etEmail.doOnTextChanged { it, _, _, _ ->
            val text = it.toString()
            binding.emailLayout.error =
                if (!isEmailValid(text) && text.isNotEmpty()) getString(R.string.email_invalid) else null
            binding.btnRegister.isEnabled = true
            updateButtonState()
        }

        binding.etPassword.doOnTextChanged { it, _, _, _ ->
            val text = it.toString()
            binding.passwordLayout.error =
                if (!isPasswordValid(text) && text.isNotEmpty()) getString(R.string.password_invalid) else null
            binding.btnRegister.isEnabled = true
            updateButtonState()
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 8
    }

    private fun updateButtonState() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val isEmailValid = isEmailValid(email)
        val isPasswordValid = isPasswordValid(password)
        binding.btnRegister.isEnabled = isEmailValid && isPasswordValid
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect { state ->
                    if (state != null) {
                        when (state) {
                            is ResultState.Error -> {
                                binding.root.showSnackbar(state.error.getErrorMessage())
                                binding.progressBar.visibility = View.INVISIBLE
                                binding.btnRegister.visibility = View.VISIBLE
                            }

                            is ResultState.Loading -> {
                                binding.progressBar.visibility = View.VISIBLE
                                binding.btnRegister.visibility = View.INVISIBLE
                            }

                            is ResultState.Succes -> {
                                findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
                            }
                        }
                    }
                }
            }
        }
    }
}