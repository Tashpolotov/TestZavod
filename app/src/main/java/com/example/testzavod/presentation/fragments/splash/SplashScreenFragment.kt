package com.example.testzavod.presentation.fragments.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testzavod.R
import com.example.testzavod.utils.SharedPref
import com.example.testzavod.utils.base.BaseFragment
import org.json.JSONObject
import android.util.Base64

class SplashScreenFragment : BaseFragment(R.layout.fragment_splash_screen) {

    private lateinit var sharedPref: SharedPref

    override fun initialize() {
        sharedPref = SharedPref(requireContext())

        if (isTokenValid(sharedPref.accessToken)) {
            findNavController().navigate(R.id.action_splashScreenFragment_to_mainFragment)
        } else {
                findNavController().navigate(R.id.action_splashScreenFragment_to_authFragment)
            }
    }

    private fun isTokenValid(token: String?): Boolean {
        if (token == null) return false

        val expirationTime = extractExpirationTimeFromToken(token)

        return expirationTime > System.currentTimeMillis()
    }

    private fun extractExpirationTimeFromToken(token: String): Long {
        val parts = token.split(".")
        if (parts.size < 2) return 0

        val payload = String(Base64.decode(parts[1], Base64.DEFAULT))
        val json = JSONObject(payload)
        return json.getLong("exp") * 1000
    }
}
