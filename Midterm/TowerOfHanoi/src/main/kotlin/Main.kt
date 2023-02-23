import java.util.*

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