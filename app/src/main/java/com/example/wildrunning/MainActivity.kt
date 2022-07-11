package com.example.wildrunning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.wildrunning.LoginActivity.Companion.providerSession
import com.facebook.login.LoginManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun cerrarSesion(view: View){
        singOut()
    }
    private fun singOut(){
        FirebaseAuth.getInstance().signOut()

        if (providerSession == "Facebook") LoginManager.getInstance().logOut()

        Toast.makeText(this, "Se ha cerrado con exito su sesión", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, LoginActivity::class.java))
    }
}