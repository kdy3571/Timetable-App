package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Dialog(context: Context): AppCompatActivity() {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    private lateinit var textView : TextView
    fun dialog(name: String, type: String){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)

        when (type) {
            "추가" -> {
                dlg.setContentView(R.layout.dialog_layout)
                btnOK = dlg.findViewById(R.id.ok)
                btnCancel = dlg.findViewById(R.id.cancel)
                textView = dlg.findViewById(R.id.add_Content)
                textView.text = "'$name'을 추가하겠습니까?"
            }
            "변경" -> {
                dlg.setContentView(R.layout.dialog_layout2)
                btnOK = dlg.findViewById(R.id.ok2)
                btnCancel = dlg.findViewById(R.id.cancel2)
                textView = dlg.findViewById(R.id.change_Content)
                textView.text = "'$name'과 시간이 겹칩니다.\n 해당 과목을 빼고 변경하시겠습니까?"
            }
            "삭제" -> {
                dlg.setContentView(R.layout.dialog_layout3)
                btnOK = dlg.findViewById(R.id.ok3)
                btnCancel = dlg.findViewById(R.id.cancel3)
                textView = dlg.findViewById(R.id.change_Content)
                textView.text = "'$name' 수업을 삭제하시겠습니까?"
            }
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