package example.com.utils

import java.text.DecimalFormat

/**

 * Author: Roni Reynal Fitri  on $ {DATE} $ {HOUR}: $ {MINUTE}

 * Email: biruprinting@gmail.com

 */
class ValidNumber {

    //ini untuk mengkonversi dari tabel array ke format idr number
     fun deciformat(terimaString:String):String {
        val konversiLong = terimaString.toLong()
        val dcFormat = DecimalFormat("#,###")
        val hasiDeci = dcFormat.format(konversiLong).toString().replace(',','.')
        return hasiDeci
    }
}