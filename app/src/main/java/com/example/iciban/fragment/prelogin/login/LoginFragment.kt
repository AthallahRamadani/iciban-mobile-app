package com.example.iciban.fragment.prelogin.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel : LoginViewModel by viewModel()
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initView()
        signInGoogle()
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

    private fun signInGoogle(){
        firebaseAuth = FirebaseAuth.getInstance()

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("839628494542-kb0m77hovd5tid7v6tt01f6bnngci5te.apps.googleusercontent.com")
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), googleSignInOptions)

        binding.btnGoogleSignIn.setOnClickListener {
            signIn()
        }
        val firebaseUser = firebaseAuth.currentUser
        if (firebaseUser != null) {
            navigateToHome()
        }
    }

    private fun signIn() {
        val intent = googleSignInClient.signInIntent
        startActivityForResult(intent, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val signInAccountTask: Task<GoogleSignInAccount> =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            if (signInAccountTask.isSuccessful) {
                try {
                    val googleSignInAccount = signInAccountTask.getResult(ApiException::class.java)
                    if (googleSignInAccount != null) {
                        val authCredential: AuthCredential = GoogleAuthProvider.getCredential(
                            googleSignInAccount.idToken, null
                        )
                        firebaseAuth.signInWithCredential(authCredential)
                            .addOnCompleteListener(requireActivity()) { task ->
                                if (task.isSuccessful) {
                                    navigateToHome()
                                    displayToast("Firebase authentication successful")
                                } else {
                                    displayToast("Authentication Failed: ${task.exception?.message}")
                                }
                            }
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun navigateToHome() {
        findNavController().navigate(R.id.action_login_to_homeFragment)
    }

    private fun displayToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}