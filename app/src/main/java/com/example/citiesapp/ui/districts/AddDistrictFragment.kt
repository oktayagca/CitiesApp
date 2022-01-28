package com.example.citiesapp.ui.districts

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.citiesapp.R
import com.example.citiesapp.data.entities.CreateDiscrictRequest
import com.example.citiesapp.databinding.FragmentAddDistrictBinding
import com.example.citiesapp.ui.MainActivity
import com.example.citiesapp.ui.base.BaseFragment
import com.example.citiesapp.utils.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AddDistrictFragment : BaseFragment<FragmentAddDistrictBinding>() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private val viewModel: AddDistrictViewModel by viewModels()
    var selectedBitmap: Bitmap? = null
    var selectedImage: Uri? = null
    private var cityId: Int? = null
    private val args: AddDistrictFragmentArgs by navArgs()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable) {
            validate()
        }
    }
    override fun getViewBinding(): FragmentAddDistrictBinding {
        return FragmentAddDistrictBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerLauncher()
    }

    override fun initViews() {
        super.initViews()
        cityId = args.cityId
        (requireActivity() as MainActivity).hideAppBar()
        binding.apply {
            c.setOnClickListener {
                checkSelfPermission(
                    context = requireContext(),
                    activity = requireActivity(),
                    requireView(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    "Gallery"
                )
            }
            button.setOnClickListener {
                if (validate()) {
                    viewModel.createDistrict(
                        CreateDiscrictRequest(
                            cityId,
                            descriptionTextField.editText!!.text.toString(),
                            null,
                            nameTextField.editText!!.text.toString()
                        )
                    ).observe(viewLifecycleOwner,{response->
                        when(response.status){
                            Resource.Status.LOADING->{
                                binding.spinner.show()
                            }
                            Resource.Status.SUCCESS->{
                                binding.spinner.gone()
                                requireContext().showAlertDialog("İşlem Başarılı","Başarılı bir şekilde kayıt edildi.","Tamam",null,{},{})
                            }
                            Resource.Status.ERROR->{
                                binding.spinner.gone()
                                requireContext().showError(response.code!!,response.message!!)
                            }
                        }
                    })
                }else{
                    requireContext().showToastMessage("Lütfen tüm alanları doldurun.")
                }
            }
            nameTextField.editText!!.addTextChangedListener(textWatcher)
            descriptionTextField.editText!!.addTextChangedListener(textWatcher)
        }
    }

    private fun validate(): Boolean {
        return if(!binding.descriptionTextField.editText!!.text.isNullOrEmpty() && !binding.nameTextField.editText!!.text.isNullOrEmpty()){
            binding.button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.green))
            true
        }else{
            binding.button.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.greyLight))
            false
        }
    }

    private fun checkSelfPermission(
        context: Context,
        activity: Activity,
        view: View,
        permission: String,
        permissionName: String
    ) {
        val snackBar =
            Snackbar.make(view, "Permission needed for $permissionName", Snackbar.LENGTH_INDEFINITE)
        if (ContextCompat.checkSelfPermission(
                context,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                //rational
                snackBar.setAction("Give Permission") {
                    //request permission
                    permissionLauncher.launch(permission)
                }
                snackBar.show()
            } else {
                //request permission
                permissionLauncher.launch(permission)
            }
        } else {
            val intentToGallery =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            activityResultLauncher.launch(intentToGallery)
        }
    }

    private fun registerLauncher() {
        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == RESULT_OK) {
                    val intentFromResult = result.data
                    if (intentFromResult != null) {
                        val imageData = intentFromResult.data
                        //imageView.setImageURI(imageData)
                        if (imageData != null) {
                            selectedImage = imageData
                            try {
                                if (Build.VERSION.SDK_INT >= 28) {
                                    val source = ImageDecoder.createSource(
                                        requireActivity().contentResolver,
                                        imageData
                                    )
                                    selectedBitmap = ImageDecoder.decodeBitmap(source)
                                    binding.imageView.setImageBitmap(selectedBitmap)
                                } else {
                                    selectedBitmap = MediaStore.Images.Media.getBitmap(
                                        requireActivity().contentResolver,
                                        imageData
                                    )
                                    binding.imageView.setImageBitmap(selectedBitmap)
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        }
                    }
                }
            }

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    //permission granted
                    val intentToGallery =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    activityResultLauncher.launch(intentToGallery)
                } else {
                    //permission denied
                    Toast.makeText(requireContext(), "Permission Needed", Toast.LENGTH_LONG).show()
                }
            }
    }

    override fun removeObject() {
        super.removeObject()
        binding.apply {
            nameTextField.editText!!.removeTextChangedListener(textWatcher)
            descriptionTextField.editText!!.removeTextChangedListener(textWatcher)
        }
    }

}