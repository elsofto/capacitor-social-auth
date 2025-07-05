package com.elsofto.capacitor.socialauth

import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import androidx.credentials.exceptions.GetCredentialException
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@CapacitorPlugin(name = "SocialAuth")
class SocialAuthPlugin : Plugin() {
    private var currentCall: PluginCall? = null
    private lateinit var credentialManager: CredentialManager
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun load() {
        super.load()
        credentialManager = CredentialManager.create(context)
    }

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
        println(clientId)
        currentCall = call

        coroutineScope.launch {
            try {
                val googleIdOption = GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(true)
                    .setServerClientId(clientId)
                    .build()

                val request = GetCredentialRequest.Builder()
                    .addCredentialOption(googleIdOption)
                    .build()

                val result = credentialManager.getCredential(
                    context = activity,
                    request = request
                )

                handleSignInResult(result)
            } catch (e: GetCredentialException) {
                handleSignInError(e)
            } catch (e: Exception) {
                currentCall?.reject("Unexpected error: ${e.message}")
                currentCall = null
            }
        }
    }

    private fun handleSignInResult(result: GetCredentialResponse) {
        val call = currentCall ?: return
        try {
            val credential = result.credential

            when (credential) {
                is CustomCredential -> {
                    if (credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                        val googleIdTokenCredential = GoogleIdTokenCredential
                            .createFrom(credential.data)

                        val idToken = googleIdTokenCredential.idToken
                        val email = googleIdTokenCredential.id
                        val displayName = googleIdTokenCredential.displayName
                        val familyName = googleIdTokenCredential.familyName
                        val profilePictureUri = googleIdTokenCredential.profilePictureUri

                        val jsResult = JSObject().apply {
                            put("idToken", idToken)
                            put("email", email)
                            put("displayName", displayName)
                            put("familyName", familyName)
                            put("profilePictureUri", profilePictureUri)
                        }
                        call.resolve(jsResult)
                    } else {
                        call.reject("Unsupported credential type")
                    }
                }

                else -> {
                    call.reject("Unsupported credential type")
                }
            }
        } catch (e: Exception) {
            call.reject("Failed to process credential: ${e.message}")
        } finally {
            currentCall = null
        }
    }

    private fun handleSignInError(e: GetCredentialException) {
        val call = currentCall ?: return
        call.reject("Sign-in failed: ${e.message}")
        currentCall = null
    }
}
