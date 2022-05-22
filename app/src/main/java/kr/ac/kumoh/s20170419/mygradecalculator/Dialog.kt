package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_timetable_generation.*
import kotlinx.android.synthetic.main.dialog_layout.*
import kotlinx.android.synthetic.main.dialog_layout2.*

class Dialog(context: Context): AppCompatActivity() {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    private lateinit var textView : TextView
    fun dialog(name: String, type: String){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)

        if (type == "추가") {
            dlg.setContentView(R.layout.dialog_layout)
            btnOK = dlg.findViewById(R.id.ok)
            btnCancel = dlg.findViewById(R.id.cancel)
            textView = dlg.findViewById(R.id.add_Content)
            textView.text = "$name 을/를 추가하겠습니까?"
        }
        else if (type == "변경") {
            dlg.setContentView(R.layout.dialog_layout2)
            btnOK = dlg.findViewById(R.id.ok2)
            btnCancel = dlg.findViewById(R.id.cancel2)
            textView = dlg.findViewById(R.id.change_Content)
            textView.text = "$name 을/를 빼고 변경하시겠습니까?"
        }
        dlg.setCancelable(false)

        btnOK.setOnClickListener{
            onClickedListener.onClicked(1)
            dlg.dismiss()
        }
        btnCancel.setOnClickListener {
            onClickedListener.onClicked(0)
            dlg.dismiss()
        }
        dlg.show()
    }
    interface ButtonClickListener {
        fun onClicked(data : Int)
    }

    private lateinit var onClickedListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }
}