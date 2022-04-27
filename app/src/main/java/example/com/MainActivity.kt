package example.com

import academy.learnprogramming.models.Barang
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.android.material.snackbar.Snackbar
import example.com.utils.ValidNumber
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*
import kotlin.collections.ArrayList


private var allInCart = ArrayList<Barang>()

class MainActivity : AppCompatActivity() {
    private var editText:EditText?=null
    private var editTextQty : EditText?=null
    //private var editDiscount : EditText?=null
    private var textView:TextView?=null
    //private val rootLayout : Layout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val  rootlayout =findViewById<View>(R.id.akarxml) //ini buat manggil snackbar

        val barang = Barang(
            "General Care", "USG Gel", "5 Liter",
            "500", "700", "30", "PCS", "12345"
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

        //yg berjalan dengan benar
        editText =findViewById<View>(R.id.txt_number) as EditText
        editText!!.addTextChangedListener(onTextChangedListener())
        editText!!.append(afterHjual)

        editTextQty = findViewById<View>(R.id.txt_Qty) as EditText
        editTextQty!!.addTextChangedListener(qtyChangedListener())
        editTextQty!!.append("1")


        val hargaJualChange = findViewById<EditText>(R.id.txt_number)
        hargaJualChange.setOnFocusChangeListener { _, focused ->
            if (focused) {
                hargaJualChange.setSelectAllOnFocus(true)
            }else if (!focused) {
                val remHargaJualhange = ValidNumber().removedot(hargaJualChange.text.toString())
                if(remHargaJualhange.toInt()<= allInCart[0].hargaModal!!.toInt() ){
//                    Toast.makeText(this, "Harga Jual Wajib di atas harga modal", Toast.LENGTH_SHORT)
//                        .show()
                    Snackbar.make(rootlayout,"Harga Jual Wajib di atas harga modal",Snackbar.LENGTH_LONG).show()
                    hargaJualChange.text.clear()

                   //hargaJualChange.append(allInCart[0].hargaJual)
//                    return@setOnFocusChangeListener
                }
            }
        }

        val jumlahBeliChange = findViewById<EditText>(R.id.txt_Qty)
        jumlahBeliChange.setOnFocusChangeListener{ _, focused ->
            if (focused){jumlahBeliChange.setSelectAllOnFocus(true)}
        }

        //discount area
        val onOffDiscount= findViewById<CheckBox>(R.id.checkBoxDisc)
        val discount = findViewById(R.id.discount) as EditText
        onOffDiscount.setOnCheckedChangeListener {buttonView, isCheked ->
            if(isCheked){
                discount!!.apply {
                    isEnabled=true
                    text.clear()
                    if (text.isEmpty()){
                    append("1000")}
                    //isFocused
                    setSelectAllOnFocus(true)
                    discount!!.addTextChangedListener(discChangedListener())
                }
//                discount.isEnabled=true
//                discount.text.clear()
//                discount.append("1000")
//                discount.isFocused
//                discount.setSelectAllOnFocus(true)
            }else{
               //discount!!.removeTextChangedListener(discChangedListener())
                //onOffDiscount.setChecked(false)
                discount.isEnabled=false
                discount.text.clear()


            }
        }
    }
    private fun resetDiskon(){
        val onOffDiscount= findViewById<CheckBox>(R.id.checkBoxDisc)
        val discount = findViewById(R.id.discount) as EditText
        discount.removeTextChangedListener(discChangedListener())
        discount.text.clear()
        onOffDiscount.setChecked(false)
        onOffDiscount.isChecked
        //discount.isEnabled=false
    }

    //diskon area
    private fun discChangedListener(): TextWatcher? {
        return object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var originalString = s.toString()
                val discount = findViewById<EditText>(R.id.discount)
                val onOffDiscount= findViewById<CheckBox>(R.id.checkBoxDisc)
                val  rootlayout =findViewById<View>(R.id.akarxml)
                if (originalString.startsWith("0")){
                    Snackbar.make(rootlayout,"Potongan harga tidak valid",Snackbar.LENGTH_LONG).show()
                    discount.text.clear()
                    //onOffDiscount.setChecked(false)
//                    if (discount.text.isEmpty()){
//                    discount.append("1000")}

                    //onOffDiscount.isChecked()
                    //dicount.text.append("1000")
                   // dicount.removeTextChangedListener(this)

                }else
                    if(originalString.isEmpty()){
                        Snackbar.make(rootlayout,"Potongan harga tidak valid",Snackbar.LENGTH_LONG).show()
                        discount.text.clear()
//                        if (discount.text.isEmpty()){
                        discount.text.append("1000")
                        //onOffDiscount.setChecked(false)
                        //onOffDiscount.isChecked
                    }
            }

            override fun afterTextChanged(s: Editable?){
                val discount = findViewById<EditText>(R.id.discount)
                discount!!.removeTextChangedListener(this)
            //textView!!.removeTextChangedListener(this)
            try{
                var originalString = s.toString()
                if (originalString.contains(".")) {
                    originalString = originalString.replace(".", "")
                }

                val longval : Long = originalString.toLong()
                val formatter = NumberFormat.getInstance(Locale("in","ID")) as DecimalFormat
                formatter.applyPattern("#,###,###,###")
                val formattedString = formatter.format(longval)
                discount!!.setText(formattedString)
                discount!!.setSelection(discount!!.text.length)
                updateTotalItem()

                //val hJualListener= findViewById<TextView>(R.id.hargajual)
                //hJualListener.text = formattedString
                //textView!!.setText(formattedString)
                //textView!!.selectionEnd

            }catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
            }
                discount!!.addTextChangedListener(this)
//                    val hJualListener= findViewById<TextView>(R.id.hargajual)
//                    hJualListener.addTextChangedListener(this)
            //textView!!.addTextChangedListener(this)
        }

        }
    }


    //update total harga peritem
    private fun updateTotalItem(){
        val harga = findViewById<EditText>(R.id.txt_number).text.toString()
        val qty  = findViewById<EditText>(R.id.txt_Qty).text.toString()
        val x:Long
        val y:String
        x= ValidNumber().removedot(harga).toLong()*ValidNumber().removedot(qty).toLong()
        y=ValidNumber().deciformat(x.toString())
        findViewById<TextView>(R.id.total).text=y
    }
    //listener start


    private fun txtChangedListener(): TextWatcher? {
        return object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            @RequiresApi(Build.VERSION_CODES.Q)
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
                 //   textView!!.setText(formattedString)
                    textView!!.text = formattedString

                    //textView!!.setTextSelectHandleRight(textView!!.text.length)

                } catch (nfe: NumberFormatException) {
                nfe.printStackTrace()
            }
                textView!!.addTextChangedListener(this)
            }

        }

    }

//original one
    private fun onTextChangedListener(): TextWatcher {
            return object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    var originalString = s.toString()
                    val hargaJualChange = findViewById<EditText>(R.id.txt_number)
                    val  rootlayout =findViewById<View>(R.id.akarxml)
                    if (originalString.startsWith("0")){
                        Snackbar.make(rootlayout,"Harga Jual Wajib di atas harga modal",Snackbar.LENGTH_LONG).show()
                        hargaJualChange.text.clear()
                        hargaJualChange.append(allInCart[0].hargaJual)
                    }else
                        if(hargaJualChange.text.toString().isEmpty()){
                            Snackbar.make(rootlayout,"Harga Jual Wajib di atas harga modal",Snackbar.LENGTH_LONG).show()
                            hargaJualChange.text.clear()
                            hargaJualChange.append(allInCart[0].hargaJual)
                        }

                }

                override fun afterTextChanged(s: Editable?) {
                     editText!!.removeTextChangedListener(this)
                    //textView!!.removeTextChangedListener(this)
                    try{
                        var originalString = s.toString()
                        if (originalString.contains(".")) {
                            originalString = originalString.replace(".", "")
                             }

                       // val konversiLong = originalString.toLong()
                       //val dcFormat = DecimalFormat("#,###")
                       // val hasilDeci = dcFormat.format(konversiLong).toString().replace(',','.')
                       //editText!!.setText(hasilDeci)

                        val longval : Long = originalString.toLong()
                        val formatter = NumberFormat.getInstance(Locale("in","ID")) as DecimalFormat
                        formatter.applyPattern("#,###,###,###")
                        val formattedString = formatter.format(longval)
                        editText!!.setText(formattedString)
                        editText!!.setSelection(editText!!.text.length)
                        updateTotalItem()

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

    //ini untuk menghandle qty
    private fun qtyChangedListener(): TextWatcher? {
        return object :TextWatcher{
            override fun beforeTextChanged(s : CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                var originalString = s.toString()
                val jumlahBeliChange = findViewById<EditText>(R.id.txt_Qty)
                val  rootlayout =findViewById<View>(R.id.akarxml)
                if (originalString.startsWith("0")){
                    Snackbar.make(rootlayout,"Qty tidak boleh kurang dari 1",Snackbar.LENGTH_LONG).show()
                    jumlahBeliChange.text.clear()
                    jumlahBeliChange.append("1")
                }else
                    if(jumlahBeliChange.text.toString().isEmpty()){
                        Snackbar.make(rootlayout,"Qty tidak boleh kurang dari 1",Snackbar.LENGTH_LONG).show()
                        jumlahBeliChange.text.clear()
                        jumlahBeliChange.append("1")
                    }
            }

            override fun afterTextChanged(s: Editable?) {
                editTextQty!!.removeTextChangedListener(this)
                try{
                    var originalString = s.toString()
                    if (originalString.contains(".")) {
                        originalString = originalString.replace(".", "")
                    }
                    val longval : Long = originalString.toLong()
                    val formatter = NumberFormat.getInstance(Locale("in","ID")) as DecimalFormat
                    formatter.applyPattern("#,###,###,###")
                    val formattedString = formatter.format(longval)
                    editTextQty!!.setText(formattedString)
                    editTextQty!!.setSelection(editTextQty!!.text.length)
                    updateTotalItem()

                } catch (nfe: NumberFormatException) {
                    nfe.printStackTrace()
                }
                editTextQty!!.addTextChangedListener(this)
            }

        }

    }



}