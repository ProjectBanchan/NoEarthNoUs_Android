package com.coooldoggy.noearthnous.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.coooldoggy.noearthnous.R
import com.coooldoggy.noearthnous.databinding.FragmentLoginBinding
import com.coooldoggy.noearthnous.model.data.UserSaveRequestDto
import com.coooldoggy.noearthnous.ui.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {
    private val TAG = LoginFragment::class.java.simpleName
    private var viewBinding: FragmentLoginBinding? = null
    private val viewModel by viewModels<LoginViewModel>()

    private var googleClient: GoogleSignInClient? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context?.resources?.getString(R.string.google_key))
            .requestEmail()
            .build()

        googleClient = activity?.let { GoogleSignIn.getClient(it, gso) }

        viewBinding?.signInButton?.setOnClickListener {
            val signInIntent: Intent? = googleClient?.signInIntent
            startForResult.launch(signInIntent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        viewBinding = FragmentLoginBinding.inflate(inflater, container, false)
        return viewBinding?.root
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleSignInResult(task)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
            Log.d(TAG, "handleSignInResult $account")
            val userData = UserSaveRequestDto(
                email = account?.email ?: "",
                id = account?.id ?: "",
                imgUrl = account?.photoUrl.toString(),
                name = account?.displayName ?: ""
            )
            viewModel.sendUserToServer(userData)
        } catch (e: ApiException) {
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

}