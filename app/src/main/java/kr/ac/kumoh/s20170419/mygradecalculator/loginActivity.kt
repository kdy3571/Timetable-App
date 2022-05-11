package kr.ac.kumoh.s20170419.mygradecalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityLoginBinding
import kr.ac.kumoh.s20170419.mygradecalculator.databinding.ActivityMainBinding

class loginActivity : AppCompatActivity() {

    private lateinit var view: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        view = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(view.root)

        view.loginButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }


}