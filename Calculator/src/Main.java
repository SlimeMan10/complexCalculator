import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select an operation to do");
        System.out.println("1. e^x");
        System.out.println("2. Square Root");
        System.out.println("3. Sin(x) [Degrees]");
        System.out.println("4. Cos(x) [Degrees]");
        System.out.println("5. number of primes up to x");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");

        String operation = scanner.nextLine();
        while (!operation.equals("0")) {
            try {
                if (operation.trim().isEmpty()) {
                    throw new IllegalArgumentException("Please enter a number");
                }

                int checkNum = Integer.parseInt(operation);
                if (checkNum < 0 || checkNum > 5) {
                    throw new IllegalArgumentException("Please enter a number between 0 and 5");
                }

                System.out.print("Enter number: ");
                String number = scanner.nextLine();
                if (number.trim().isEmpty()) {
                    throw new IllegalArgumentException("Number cannot be empty");
                }

                double num = Double.parseDouble(number);
                switch (operation) {
                    case "1":
                        System.out.println("Exponent of " + number + " is " + exponent(num));
                        break;
                    case "2":
                        if (num >= 0) {
                            System.out.println("Square Root of " + number + " is " + sqrt(num));
                        } else {
                            System.out.println("Square Root of " + number + " is " + complexSqrt(-num));
                        }
                        break;
                    case "3":
                        System.out.printf("Sin(x) of " + number + " is %.4f\n", sin(num));
                        break;
                    case "4":
                        System.out.printf("Cos(x) of " + number + " is %.4f\n", cos(num));
                        break;
                    case "5":
                        System.out.println("There are " + prime(num) + " primes up to " + num);
                        break;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Please enter a valid number");
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

            System.out.println();
            System.out.print("Enter your choice: ");
            operation = scanner.nextLine();
        }
        System.out.println("Bye!");
        scanner.close();
    }

    private static double exponent(double number) {
        //SUM(x^k/k!)
        double ans = 1.0;
        double factorial = 1.0;
        for (int i = 1; i < 50; i++) {
            factorial *= i;
            ans += Math.pow(number, i) / factorial;
        }
        return ans;
    }

    private static double sqrt(double number) {
        if (number == 0) {
            return 0;
        }
        if (number < 0) {
            return Double.NaN;
        }

        double x = number;
        double eps = 1e-10;

        while (Math.abs(x*x - number) > eps) {
            x = (x + number/x) / 2;
        }
        return x;
    }

    private static String complexSqrt(double number) {
        if (number == -1) {
            return "i";
        }
        if (number == 0) {
            return "0";
        }
        return sqrt(number) + "i";
    }

    private static double sin(double number) {
        //converts to rads
        number *= (Math.PI / 180);
        double ans = number;  // First term
        double term = number;
        double x2 = number * number;  // Store x² for reuse

        for (int i = 1; i < 50; i++) {
            // Build next term based on previous term
            term *= -x2 / ((2 * i) * (2 * i + 1));
            ans += term;

            if (Math.abs(term) < 1e-15) {
                break;
            }
        }
        return ans;
    }

    //O(n)
    private static double cos(double number) {
        number *= (Math.PI / 180);
        double ans = number;
        double term = number;
        double x2 = number * number;
        for (int i = 1; i < 50; i++) {
            term *= -x2 / ((2 * i) * (2 * i - 1));
            ans += term;
            if (Math.abs(term) < 1e-15) {
                break;
            }
        }
        return ans;
    }

    ///O(n+m)
    private static int prime(double num) {
        if (num % 1 != 0) {
            throw new IllegalArgumentException("number must be a whole number");
        }
        int number = (int) num;
        if (number <= 1) {
            throw new IllegalArgumentException("number must be greater than 1");
        }
        boolean[] primes = new boolean[number + 1];
        for (int i = 2; i <= number; i++) {
            primes[i] = true;
        }
        for (int p = 2; p * p <= number; p++) {
            if (primes[p]) {
                for (int i = p * 2; i <= number; i += p) {
                    primes[i] = false;
                }
            }
        }
        int count = 0;
        for (int i = 2; i <= number; i++) {
            if (primes[i]) {
                count++;
            }
        }
        return count;
    }
}