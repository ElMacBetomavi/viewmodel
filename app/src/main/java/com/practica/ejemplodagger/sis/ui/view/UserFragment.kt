package com.practica.ejemplodagger.sis.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.practica.ejemplodagger.R
import com.practica.ejemplodagger.databinding.FragmentUserBinding
import java.util.*


class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.findViewById<MaterialToolbar>(R.id.topAppBar)?.title = "Login"
        activity?.findViewById<FloatingActionButton>(R.id.add_categoria)!!.visibility = View.GONE
        auth = Firebase.auth
    }

//    override fun onStart() {
//        super.onStart()
//        val currentUser = auth.currentUser
//        if(currentUser != null){
//            refresh()
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding){

            saveUserBtn.setOnClickListener{
                createAccount()
            }
            signInBtn.setOnClickListener{
                signIn()
            }
            signOutBtn.setOnClickListener{
                signOut()
            }
        }

    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if(currentUser != null){
            reload();
        }
    }

    private fun createAccount() {
        val email = binding.emailField.text.toString() // email address format
        val password = binding.passwordField.text.toString() // at least 6 characters
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(
                requireActivity()
            ) { task ->
                var message=""
                if (task.isSuccessful) {
                     message  = "success createUserWithEmailAndPassword"
                    val user = auth.currentUser
                    updateUI(user)
                    getToken()
                } else {
                    updateUI(null)
                     message = "fail createUserWithEmailAndPassword"
                }
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }.addOnFailureListener(OnFailureListener { e ->
                e.printStackTrace()
            })
    }

    private fun signIn() {
        val email = binding.emailField.text.toString() // email address format
        val password = binding.passwordField.text.toString() // at least 6 characters
        Log.d(TAG, "signIn:$email")
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    getToken()
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(context, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }


    private fun signOut(){
        auth.signOut()
        updateUI(null)
    }

    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateUI(auth.currentUser)
                Toast.makeText(context, "Reload successful!", Toast.LENGTH_SHORT).show()
            } else {
                Log.e(TAG, "reload", task.exception)
                Toast.makeText(context, "Failed to reload user.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            binding.title.text = "logeado"
            binding.info.text = user.email
            binding.info.visibility = View.VISIBLE
            binding.signInBtn.visibility = View.GONE
            binding.saveUserBtn.visibility = View.GONE
            binding.passwordField.visibility = View.GONE
            binding.emailField.visibility = View.GONE
            binding.signOutBtn.visibility = View.VISIBLE
            binding.emailContainer.visibility = View.GONE
            binding.passwordContainer.visibility = View.GONE
        } else {
            binding.title.text = "crear cuenta"
            binding.saveUserBtn.visibility = View.VISIBLE
            binding.signInBtn.visibility = View.VISIBLE
            binding.passwordField.visibility = View.VISIBLE
            binding.emailField.visibility = View.VISIBLE
            binding.signOutBtn.visibility = View.GONE
            binding.emailContainer.visibility = View.VISIBLE
            binding.passwordContainer.visibility = View.VISIBLE
            binding.info.visibility = View.GONE
        }
    }

    fun getToken(){
        val mUser = FirebaseAuth.getInstance().currentUser
        mUser!!.getIdToken(true)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                     token = task.result.token!!
                    println("token $token")
                    // Send token to your backend via HTTPS

                } else {
                    // Handle error -> task.getException();
                }
            }


    }

    companion object {
        private const val TAG = "EmailPassword"
        private const val RC_MULTI_FACTOR = 9005
        private lateinit var token: String
    }

}