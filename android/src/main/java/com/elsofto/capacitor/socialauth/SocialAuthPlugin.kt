package com.elsofto.capacitor.socialauth

import android.app.Activity
import androidx.activity.result.ActivityResult
import androidx.credentials.CredentialManager
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.ActivityCallback
import com.getcapacitor.annotation.CapacitorPlugin
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

@CapacitorPlugin(name = "SocialAuth")
class SocialAuthPlugin : Plugin() {

  private lateinit var credentialManager: CredentialManager
  private lateinit var googleSignInClient: GoogleSignInClient

  private var currentCall: PluginCall? = null
  private val coroutineScope = CoroutineScope(Dispatchers.Main)

  override fun load() {
    super.load()
    credentialManager = CredentialManager.create(context)
  }

//  @PluginMethod
//  fun signIn(call: PluginCall) {
//    if (currentCall != null) {
//      call.reject("Another sign-in is in progress")
//      return
//    }
//
//    val activity = activity ?: run {
//      call.reject("Activity is not available")
//      return
//    }
//
//    val clientId = call.getString("serverClientId") ?: run {
//      call.reject("serverClientId is required")
//      return
//    }
//
//    currentCall = call
//
//    coroutineScope.launch {
//      try {
//        val googleIdOption = GetGoogleIdOption.Builder()
//          .setServerClientId(clientId)
//          .setFilterByAuthorizedAccounts(false)
//          .setAutoSelectEnabled(false)
//          .build()
//
//        val request = GetCredentialRequest.Builder()
//          .addCredentialOption(googleIdOption)
//          .build()
//
//        val result = credentialManager.getCredential(
//          context = activity,
//          request = request,
//        )
//
//        handleSignInResult(result)
//      } catch (e: GetCredentialException) {
//        handleSignInError(e)
//      } catch (e: Exception) {
//        currentCall?.reject("Unexpected error: ${e.message}")
//        currentCall = null
//      }
//    }
//  }
//
//  private fun handleSignInResult(result: GetCredentialResponse) {
//    val call = currentCall ?: return
//    try {
//      val credential = result.credential
//
//      when (credential) {
//        is CustomCredential -> {
//          if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
//            val googleIdTokenCredential = GoogleIdTokenCredential
//              .createFrom(credential.data)
//
//            val idToken = googleIdTokenCredential.idToken
//            val id = googleIdTokenCredential.id
//            val data = googleIdTokenCredential.data
//            val displayName = googleIdTokenCredential.displayName
//
//            val jsResult = JSObject().apply {
//              put("idToken", idToken)
//              put("id", id)
//              put("data", data)
//              put("displayName", displayName)
//            }
//            call.resolve(jsResult)
//          } else {
//            call.reject("Unsupported credential type")
//          }
//        }
//
//        else -> {
//          call.reject("Unsupported credential type")
//        }
//      }
//    } catch (e: Exception) {
//      call.reject("Failed to process credential: ${e.message}")
//    } finally {
//      currentCall = null
//    }
//  }

//  private fun handleSignInError(e: GetCredentialException) {
//    val call = currentCall ?: return
//    call.reject("Sign-in failed: ${e.message}")
//    currentCall = null
//  }

  @PluginMethod
  fun signIn(call: PluginCall) {
    if (currentCall != null) {
      call.reject("Another sign-in is in progress")
      return
    }

    val activity = activity ?: run {
      call.reject("Activity is not available")
      return
    }

    val clientId = call.getString("serverClientId") ?: run {
      call.reject("serverClientId is required")
      return
    }

    currentCall = call

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(clientId)
      .requestEmail()
      .build()

    googleSignInClient = GoogleSignIn.getClient(activity, gso)

    googleSignInClient.revokeAccess().addOnCompleteListener {
      val signInIntent = googleSignInClient.signInIntent
      startActivityForResult(call, signInIntent, "handleSignInResult")

      currentCall = null;
    }
  }

  @ActivityCallback
  private fun handleSignInResult(call: PluginCall, result: ActivityResult) {
    if (result.resultCode != Activity.RESULT_OK) {
      call.reject("Sign-in canceled")
      return
    }

    try {
      val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
      val account = task.getResult(ApiException::class.java)

      val jsResult = JSObject().apply {
        put("idToken", account.idToken)
        put("email", account.email)
        put("id", account.id)
        put("displayName", account.displayName)
        put("givenName", account.givenName)
        put("familyName", account.familyName)
        put("photoUrl", account.photoUrl?.toString())
      }

      call.resolve(jsResult)
    } catch (e: Exception) {
      call.reject("Sign-in failed: ${e.message}")
    }
  }
}
