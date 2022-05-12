package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView

class Dialog(context: Context) {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var btnOK : Button
    private lateinit var btnCancel : Button
    fun dialog(){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_layout)
        dlg.setCancelable(false)

        btnOK = dlg.findViewById(R.id.ok)
        btnCancel = dlg.findViewById(R.id.cancel)
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