package com.example.citiesapp.ui.loginRegister

import androidx.core.text.HtmlCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.citiesapp.R
import com.example.citiesapp.data.entities.AuthRequest
import com.example.citiesapp.databinding.FragmentLoginRegisterBinding
import com.example.citiesapp.ui.MainActivity
import com.example.citiesapp.ui.MainActivitySharedViewModel
import com.example.citiesapp.ui.base.BaseFragment
import com.example.citiesapp.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginRegisterFragment : BaseFragment<FragmentLoginRegisterBinding>() {

    private var isLoginPage = true

    private lateinit var viewModel:MainActivitySharedViewModel

    override fun getViewBinding(): FragmentLoginRegisterBinding {
        return FragmentLoginRegisterBinding.inflate(layoutInflater)
    }

    override fun initViews() {
        viewModel = ViewModelProvider(this).get(MainActivitySharedViewModel::class.java)
        binding.apply {
            loginOrRegisterTextView.text = HtmlCompat.fromHtml(
                getString(R.string.don_t_have_an_account_sign_up),
                HtmlCompat.FROM_HTML_MODE_LEGACY
            )
            loginOrRegisterTextView.setOnClickListener {
                changeScreen()
            }
            button.setOnClickListener {
                if (userNameTextField.editText!!.text != null && userNameTextField.editText!!.text != null) {
                    if (isLoginPage) {
                        viewModel.login(
                            AuthRequest(
                                userNameTextField.editText!!.text.toString(),
                                passwordTextField.editText!!.text.toString()
                            )
                        ).observe(viewLifecycleOwner, { response ->
                            when (response.status) {
                                Resource.Status.LOADING -> {
                                    binding.spinner.show()
                                }
                                Resource.Status.SUCCESS -> {
                                    binding.spinner.gone()
                                    requireContext().showToastMessage("Oturum başarılı bir şekilde açıldı.")
                                    goBack()

                                }
                                Resource.Status.ERROR -> {
                                    binding.spinner.gone()
                                    requireContext().showError(response.code!!, response.message!!)
                                }
                            }
                        })
                    }
                    else {
                        viewModel.register(
                            AuthRequest(
                                userNameTextField.editText!!.text.toString(),
                                passwordTextField.editText!!.text.toString()
                            )
                        ).observe(viewLifecycleOwner, { response ->
                            when (response.status) {
                                Resource.Status.LOADING -> {
                                    binding.spinner.show()
                                }
                                Resource.Status.SUCCESS -> {
                                    binding.spinner.gone()
                                    requireContext().showToastMessage("Kayıt olma işlemi başarılı.")
                                    goBack()
                                }
                                Resource.Status.ERROR -> {
                                    binding.spinner.gone()
                                    requireContext().showError(response.code!!, response.message!!)
                                }
                            }
                        })
                    }
                } else {
                    requireContext().showToastMessage("Lütfen tüm alaları doldurun.")
                }
            }
        }
        (activity as MainActivity).hideAppBar()
    }

    private fun changeScreen() {
        binding.apply {
            if (isLoginPage) {
                titleTextView.text = getString(R.string.register)
                button.text = getString(R.string.register)
                loginOrRegisterTextView.text = HtmlCompat.fromHtml(
                    getString(R.string.have_an_account_sign_in),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            } else {
                titleTextView.text = getString(R.string.login)
                button.text = getString(R.string.login)
                loginOrRegisterTextView.text = HtmlCompat.fromHtml(
                    getString(R.string.don_t_have_an_account_sign_up),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            }
            isLoginPage = !isLoginPage
        }
    }

    private fun goBack() {
        (activity as MainActivity).showAppBar()
        if (!findNavController().popBackStack()) {
            requireActivity().finish()
        } else {
            findNavController().navigateUp()
        }
    }

    override fun removeObject() {
        super.removeObject()
        (activity as MainActivity).showAppBar()
    }

    override fun onDestroy() {
        super.onDestroy()
        (activity as MainActivity).showAppBar()
    }

}