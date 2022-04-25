package example.com

import academy.learnprogramming.models.Barang
import academy.learnprogramming.models.BarangCart
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView


private var allInCart = ArrayList<Barang>()

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val barang = Barang("General Care","USG Gel","5 Liter",
        "140000","160000","30","PCS","12345")

        allInCart.add(barang)

        println("uraa"+allInCart)
        print(allInCart[0].hargaJual)

        findViewById<TextView>(R.id.hargajual).text = allInCart[0].hargaJual


    }
}