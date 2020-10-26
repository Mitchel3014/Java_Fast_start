import java.util.Random;
import java.util.Scanner;

public class Guess_the_number {
    private static Scanner scanner = new Scanner (System.in);
    public static void main(String[] args) {
        System.out.println("Поиграем в игру 'Угадай число'. \nВаша задача угадать число!\n");

        do {
            for (int range = 10, i = 1; range <= 50; range += 10, i++) {
                System.out.println("Уровень " + i);
                if (game_cycle(range) == 1) {
                    System.out.println("Попытки закончились.\nВы проиграли!\n");
                    break;
                }
            }
            System.out.println("Повторить игру еще раз? 1 – да / 0 – нет");
        } while (scanner.nextInt() == 1);
        scanner.close();
    }

    private static int game_cycle (int range) {
        int k = 0;

        //Вариант генерации чисел №1
        int number = (int) (Math.random() * range);

        //Вариант генерации чисел №2
        //Random random = new Random();
        //int number = random.nextInt(range);

        System.out.println("Угадайте число от 0 до " + range);
        for (int j = 3; j>0; j--) {
                System.out.println("Осталось попыток: " +j+ "\nВведите число: ");
                int input_number = scanner.nextInt();
                if (input_number == number) {
                    System.out.println("Вы угадали!");
                    break;
                } else if (input_number > number) {
                    System.out.println("Загаданное число меньше!");
                } else {
                    System.out.println("Загаданное число больше!");
                }
                if (j == 1) {
                    k +=1;
                    break;
                }
        }
        return k;
    }
}
