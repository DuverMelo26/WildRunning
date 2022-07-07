package com.example.wildrunning


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*
import kotlin.properties.Delegates

class LoginActivity : AppCompatActivity() {

    companion object{
        lateinit var userEmail: String
        lateinit var providerSession: String
    }

    private var email by Delegates.notNull<String>()
    private var password by Delegates.notNull<String>()
    private var repeatPassword by Delegates.notNull<String>()
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var lyTerms: LinearLayout
    private lateinit var etRepeatPassword: EditText

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        lyTerms = findViewById(R.id.lyTerms)
        lyTerms.visibility = View.INVISIBLE

        etRepeatPassword = findViewById(R.id.etRepeatPassword)
        etRepeatPassword.visibility = View.INVISIBLE

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        mAuth = FirebaseAuth.getInstance()

        manageButtonLogin()
        etEmail.doOnTextChanged { text, start, before, count -> manageButtonLogin() }
        etPassword.doOnTextChanged { text, start, before, count -> manageButtonLogin() }
    }

    public override fun onStart() {
        super.onStart()

        val currentUser = FirebaseAuth.getInstance().currentUser

        if (currentUser != null) goHome(currentUser.email.toString(), currentUser.providerId)
    }

    override fun onBackPressed() {
        var startMain = Intent(Intent.ACTION_MAIN)
        startMain.addCategory(Intent.CATEGORY_HOME)
        startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(startMain)
    }


    private fun manageButtonLogin(){
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        var tvLogin: TextView = findViewById(R.id.tvLogin)

        if (TextUtils.isEmpty(password) || !validateEmail.isEmail(email)){
            tvLogin.setBackgroundColor(ContextCompat.getColor(this, R.color.gray))
            tvLogin.isEnabled = false
        }else{
            tvLogin.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
            tvLogin.isEnabled = true
        }
    }


    fun login(view: View){
        loginUser()
    }
    private fun loginUser(){
        email = etEmail.text.toString()
        password = etPassword.text.toString()

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful) goHome(email, "email")
                else{
                    if (lyTerms.visibility == View.INVISIBLE) {
                        lyTerms.visibility = View.VISIBLE
                        etRepeatPassword.visibility = View.VISIBLE
                    }
                    else{
                        var cbAccept:CheckBox = findViewById(R.id.cbAccept)
                        if(cbAccept.isChecked) register()
                    }
                }
            }
    }
    private fun goHome(email: String, provider: String){
        userEmail = email
        providerSession = provider
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    private fun register(){
        email = etEmail.text.toString()
        password = etPassword.text.toString()
        repeatPassword = etRepeatPassword.text.toString()


        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (it.isSuccessful){
                    var dateRegister = SimpleDateFormat("dd/MM/yyyy").format(Date())
                    var dbRegister = FirebaseFirestore.getInstance()
                    dbRegister.collection("users").document(email).set(hashMapOf(
                        "user" to email,
                        "dateRegister" to dateRegister
                    ))

                    goHome(email, "email")
                }else Toast.makeText(this, "No fue posible registrar el nuevo usuario", Toast.LENGTH_SHORT).show()
            }
    }


    fun forgotPassword(view: View){
        //val intent = Intent(this, ForgotPasswordActivity::class.java)
        //startActivity(intent)
        resetPassword()
    }
    private fun resetPassword(){
        var resetEmail = etEmail.text.toString()
        if ( !TextUtils.isEmpty(resetEmail) ){
            mAuth.sendPasswordResetEmail(resetEmail)
                .addOnCompleteListener { task ->
                    if ( task.isSuccessful) Toast.makeText(this, "Se ha enviado un enlace para restablecer tu contraseña al $resetEmail", Toast.LENGTH_SHORT).show()
                    else Toast.makeText(this, "No se encontro un usuario registrado con el correo $resetEmail", Toast.LENGTH_SHORT).show()
                }
        }else Toast.makeText(this, "No se ha proporcionado un correo electronico", Toast.LENGTH_SHORT).show()
    }


    fun goTerms(view: View){
        val intent = Intent(this, TermsActivity::class.java)
        startActivity(intent)
    }
}