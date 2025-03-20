import java.util.concurrent.CompletableFuture;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {
    public static void main(String[] args) {
        //double result = (a ^ 2 + b ^ 2) * log(c) / sqrt(d)
        Scanner input = new Scanner(System.in);

        final Double a = validInputDouble(input, "Введите число a ");
        final Double b = validInputDouble(input, "Введите число b ");
        final Double c = validInput (input,"Введите число с ");
        final Double d = validInput (input,"Введите число d ");

        CompletableFuture<Double> futureSum = CompletableFuture.supplyAsync(() -> {
            sleep(5000);
            Double res = Math.pow(a, 2) + Math.pow(b, 2);
            System.out.printf("Calculating sum of squares: %.1f\n", res);
            return res;
        });

        CompletableFuture<Double> futureLog = CompletableFuture.supplyAsync(() -> {
            sleep(15000);
            Double res = Math.log(c);
            System.out.printf("Calculating log(c): %.15f\n", res);
            return res;
        });

        CompletableFuture<Double> firstResult = futureSum.thenCombine(futureLog, (res1, res2) -> res1 * res2);

        CompletableFuture<Double> futureSqr = CompletableFuture.supplyAsync(() -> {
            sleep(10000);
            Double res = Math.sqrt(d);
            System.out.printf("Calculating sqrt(d): %.1f\n", res);
            return res;
        });

        CompletableFuture<Double> secondResult = firstResult.thenCombine(futureSqr, (res1, res2) -> res1 / res2);

        try {
            Double result = secondResult.get();
            System.out.printf("Final result of the formula: %.15f", result);
        } catch (InterruptedException | ExecutionException ex) {
            System.out.println("Возникла ошибка: " + ex.getMessage());
        }
    }

    private static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException ex) {
            System.out.println("Задача прервана: " + ex.getMessage());
        }
    }

    private static Double validInputDouble(Scanner input, String str) {
        System.out.println(str);
        while (true) {
            if (input.hasNextDouble()) {
                return input.nextDouble();
            } else {
                System.out.println("Ошибка ввода, попробуйте ещё раз ");
                input.next();
            }
        }
    }

    private static Double validInput(Scanner input, String str) {
        System.out.println(str);
        while (true) {
            if (input.hasNextDouble()) {
                double temp = input.nextDouble();
                if (temp > 0) {
                    return temp;
                } else {
                    System.out.println("Число должно быть больше нуля, попробуйте ещё раз ");
                }
            } else {
                System.out.println("Ошибка ввода, попробуйте ещё раз ");
                input.next();
            }
        }
    }
}
