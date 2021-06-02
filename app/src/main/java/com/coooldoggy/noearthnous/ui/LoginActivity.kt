package com.coooldoggy.noearthnous.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.coooldoggy.noearthnous.databinding.ActivityLoginBinding
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
class LoginActivity : AppCompatActivity() {
    private val TAG = LoginActivity::class.java.simpleName
    lateinit var viewBinding: ActivityLoginBinding
    val viewModel by viewModels<LoginViewModel>()
    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    lateinit var googleClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setResources()
    }

    private fun setResources(){
        googleClient = GoogleSignIn.getClient(this@LoginActivity, gso)

        viewBinding.signInButton.setOnClickListener {
            val signInIntent: Intent = googleClient.signInIntent
            startForResult.launch(signInIntent)
        }
    }

    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        handleSignInResult(task)
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account: GoogleSignInAccount? = completedTask.getResult(ApiException::class.java)
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