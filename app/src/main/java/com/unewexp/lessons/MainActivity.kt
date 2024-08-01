package com.unewexp.lessons

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.unewexp.lessons.databinding.ActivityMainBinding
import com.unewexp.lessons.model.RestAPIService
import com.unewexp.lessons.presenter.Presenter
import kotlinx.coroutines.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @Inject lateinit var presenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        appGraph.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.button.setOnClickListener {
            getWeather()
        }

    }

    private fun getWeather(){
        val city = binding.textInputEditText.text.toString()
        this.currentFocus?.let { view ->
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }

        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            val description = presenter.getDescription(city)
            val temp = presenter.getTemp(city)
            launch(Dispatchers.Main) {
                if (description[0] == "S") {
                    binding.city.text = city
                    binding.description.text = description[1]
                    binding.temp.text = (temp[1] + " °C")
                }else{
                    showAlertDialog(description[1], binding.textInputEditText)
                }
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }


    class MyDialogFragment(private val message: String, private val v: View) : DialogFragment() {

        private fun showKeyboard(v: View) {
            val inputMethodManager = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.showSoftInput(v, 0)
        }

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle(message)
                    .setPositiveButton("Изменить название города") { dialog, id ->
                        dialog.cancel()
                        showKeyboard(v)
                    }
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }

    private fun showAlertDialog(message: String, v: View){
        val myDialogFragment = MyDialogFragment(message, v)
        val manager = supportFragmentManager
        myDialogFragment.show(manager, "myDialog")
    }

}