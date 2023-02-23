import java.text.DecimalFormat
import java.util.*

//fun main(args: Array<String>) {
//
//    val tensNames = arrayOf(
//        "",
//        " ten",
//        " twenty",
//        " thirty",
//        " forty",
//        " fifty",
//        " sixty",
//        " seventy",
//        " eighty",
//        " ninety"
//    )
//
//    val numNames = arrayOf(
//        "",
//        " one",
//        " two",
//        " three",
//        " four",
//        " five",
//        " six",
//        " seven",
//        " eight",
//        " nine",
//        " ten",
//        " eleven",
//        " twelve",
//        " thirteen",
//        " fourteen",
//        " fifteen",
//        " sixteen",
//        " seventeen",
//        " eighteen",
//        " nineteen"
//    )
//
//    fun convertLessThanOneThousand(number: Int) : String {
//        var soFar = "";
//        var num = number;
//
//        if (number % 100 < 20){
//            soFar = numNames[number % 100];
//            num /= 100;
//        }
//        else {
//            soFar = numNames[number % 10];
//            num /= 10;
//
//            soFar = tensNames[num % 10] + soFar;
//            num /= 10;
//        }
//        if (num == 0) return soFar;
//        return numNames[num] + " hundred" + soFar;
//    }
//
//     fun convert(number : Long) : String{
//        // 0 to 999 999 999 999
//        if (number == 0.toLong()) { return "zero"; }
//
//        var snumber = number.toString();
//
//        // pad with "0"
//        var mask = "000000000000";
//        val df = DecimalFormat(mask);
//        snumber = df.format(number);
//
//        // XXXnnnnnnnnn
//        var billions = Integer.parseInt(snumber.substring(0,3));
//        // nnnXXXnnnnnn
//        var millions  = Integer.parseInt(snumber.substring(3,6));
//        // nnnnnnXXXnnn
//        var hundredThousands = Integer.parseInt(snumber.substring(6,9));
//        // nnnnnnnnnXXX
//        var thousands = Integer.parseInt(snumber.substring(9,12));
//
//        var tradBillions = "";
//         tradBillions = when (billions) {
//             0 -> "";
//             1 -> convertLessThanOneThousand(billions) + " billion ";
//             else -> convertLessThanOneThousand(billions)+ " billion ";
//         }
//        var result =  tradBillions;
//
//        var tradMillions = "";
//         tradMillions = when (millions) {
//             0 -> "";
//             1 -> convertLessThanOneThousand(millions) + " million ";
//             else -> convertLessThanOneThousand(millions)+ " million ";
//         }
//        result += tradMillions;
//
//        var tradHundredThousands = "";
//         tradHundredThousands = when (hundredThousands) {
//             0 -> "";
//             1 -> convertLessThanOneThousand(hundredThousands) + " one thousand ";
//             else -> convertLessThanOneThousand(hundredThousands)+ " thousand ";
//         }
//         result += tradHundredThousands;
//
//        var tradThousand = "";
//        tradThousand = convertLessThanOneThousand(thousands);
//         result += tradThousand;
//
//        // remove extra spaces!
//        return result.replace("^\\s+", "").replace("\\b\\s{2,}\\b", " ");
//    }
//
//
//
//
//    println("Hello World!")
//    println(convert(999999999));
//
//    // Try adding program arguments via Run/Debug configuration.
//    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
//    println("Program arguments: ${args.joinToString()}")
//}



fun towerOfHanoi(n : Int, fromRod : Char,
                 toRod : Char, auxRod : Char)
{
    if (n == 0) {
        return;
    }
    towerOfHanoi(n - 1, fromRod, auxRod, toRod);
    println("Move disk " + n + " from rod "
            + fromRod + " to rod "
            + toRod);
    towerOfHanoi(n - 1, auxRod, toRod, fromRod);
}

fun main(args: Array<String>)
{
    val reader = Scanner(System. `in`)
    print("Enter the number of disks: " )


    var n : Int = reader.nextInt();

    // A, B and C are names of rods
    towerOfHanoi(n, 'A', 'C', 'B');
}