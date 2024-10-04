package com.example.iciban.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.iciban.R
import com.example.iciban.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding?= null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater,container,false)
        binding.btnRegister.setOnClickListener{
            findNavController().navigate(R.id.action_login_to_homeFragment)
        }
        binding.navLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registerFragment_to_login)
        }
        return binding.root
        }


}