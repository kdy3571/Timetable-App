package kr.ac.kumoh.s20170419.mygradecalculator

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ListDialog(context: Context): AppCompatActivity() {
    private val dlg = Dialog(context)   //부모 액티비티의 context 가 들어감
    private lateinit var textView : TextView
    private lateinit var btndelete : Button
    fun dialog(weekdata:weekstate, type: String){
        dlg.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dlg.setContentView(R.layout.dialog_layout_list)
        dlg.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dlg.window?.setGravity(Gravity.BOTTOM)
        dlg.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (type == "리스트"){
            dlg.setContentView(R.layout.dialog_layout_list)
            textView = dlg.findViewById(R.id.subjectname)
            textView.text = weekdata.name
            textView = dlg.findViewById(R.id.professor)
            textView.text = weekdata.professor
            textView = dlg.findViewById(R.id.room)
            textView.text = weekdata.room
            btndelete = dlg.findViewById(R.id.delete_button)
        }

        btndelete.setOnClickListener {
            onClickedListener.onClicked("리스트")
            dlg.dismiss()
        }

        dlg.show()
    }
    interface ButtonClickListener {
        fun onClicked(data : String)
    }

    private lateinit var onClickedListener: ButtonClickListener

    fun setOnClickedListener(listener: ButtonClickListener) {
        onClickedListener = listener
    }
}