package example.com

import academy.learnprogramming.models.Barang
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import example.com.utils.ValidNumber
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


private var allInCart = ArrayList<Barang>()

class MainActivity : AppCompatActivity() {
    private var editText:EditText?=null
    private var textView:TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val barang = Barang(
            "General Care", "USG Gel", "5 Liter",
            "140000", "160000", "30", "PCS", "12345"
        )

        allInCart.add(barang)

        println("uraa" + allInCart)
        print(allInCart[0].hargaJual)

        val oriHjual = allInCart[0].hargaJual.toString()
        val afterHjual = ValidNumber().deciformat(oriHjual)
        findViewById<TextView>(R.id.hargajual).text = afterHjual

        val oriStok = allInCart[0].stok.toString()
        val afterStok = ValidNumber().deciformat(oriStok)
        findViewById<TextView>(R.id.Stok).text = afterStok

        findViewById<TextView>(R.id.qty).text = "1"

        //var hJualListener = findViewById<TextView>(R.id.hargajual)
        //hJualListener.addTextChangedListener(onTextChangedListener())

        textView = findViewById<View>(R.id.hargajual) as TextView
        textView!!.addTextChangedListener(txtChangedListener())

        editText =findViewById<View>(R.id.txt_number) as EditText
        editText!!.addTextChangedListener(onTextChangedListener())
        editText!!.append(afterHjual)

    }

    private fun txtChangedListener(): TextWatcher? {
        return object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                textView!!.removeTextChangedListener(this)
                try{
                    var originalString = p0.toString()
                    if (originalString.contains(".")) {
                        originalString = originalString.replace(".", "")
                    }
                    val longval : Long = originalString.toLong()
                    val formatter = NumberFormat.getInstance(Locale("in","ID")) as DecimalFormat
                    formatter.applyPattern("#,###,###,###")
                    val formattedString = formatter.format(longval)

                    //editText!!.setText(formattedString)
                    //editText!!.setSelection(editText!!.text.length)
                    textView!!.setText(formattedString)
                    textView!!.selectionEnd

                } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
            }
            }

        }

    }


    private fun onTextChangedListener(): TextWatcher {
            return object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(s: Editable?) {
                     editText!!.removeTextChangedListener(this)
                    //textView!!.removeTextChangedListener(this)
                    try{
                        var originalString = s.toString()
                        if (originalString.contains(".")) {
                            originalString = originalString.replace(".", "")
                             }

//                        val konversiLong = originalString.toLong()
//                        val dcFormat = DecimalFormat("#,###")
//                        val hasilDeci = dcFormat.format(konversiLong).toString().replace(',','.')
                        //editText!!.setText(hasilDeci)

                        val longval : Long = originalString.toLong()
                        val formatter = NumberFormat.getInstance(Locale("in","ID")) as DecimalFormat
                        formatter.applyPattern("#,###,###,###")
                        val formattedString = formatter.format(longval)
                       editText!!.setText(formattedString)
                       editText!!.setSelection(editText!!.text.length)
                        //val hJualListener= findViewById<TextView>(R.id.hargajual)
                        //hJualListener.text = formattedString
                        //textView!!.setText(formattedString)
                        //textView!!.selectionEnd

                    }catch (nfe: NumberFormatException) {
                        nfe.printStackTrace()
                    }
                    editText!!.addTextChangedListener(this)
//                    val hJualListener= findViewById<TextView>(R.id.hargajual)
//                    hJualListener.addTextChangedListener(this)
                    //textView!!.addTextChangedListener(this)
                }

            }

        }


}