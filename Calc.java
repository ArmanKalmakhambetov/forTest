import java.util.Scanner;
import java.util.TreeMap;


class Calc {


    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        System.out.print("Введите выражение: ");
        System.out.print(calc(scn.nextLine()));

    }

    public static String calc(String input) {


        Converter converter = new Converter();
        String[] actions = {"+", "-", "/", "*"};
        String[] regexActions = {" \\+ ", " - ", " / ", " \\* "};


        int actionIndex = -1;
        for (int i = 0; i < actions.length; i++) {
            if (input.contains(actions[i])) {
                actionIndex = i;
                break;
            }
        }

        try {
            String[] data = input.split(regexActions[actionIndex]);

            if (converter.isRoman(data[0]) == converter.isRoman(data[1])) {
                String total;
                int a, b;

                boolean isRoman = converter.isRoman(data[0]);
                if (isRoman) {
                    a = converter.romanToInt(data[0]);
                    b = converter.romanToInt(data[1]);
                } else {
                    a = Integer.parseInt(data[0]);
                    b = Integer.parseInt(data[1]);
                }
                // Проверка чтобы вводимые числа были от 1 до 10 и проведение операций между ними
                if (a > 0 && a <= 10 && b > 0 && b <= 10) {
                    int result;
                    switch (actions[actionIndex]) {
                        case "+":
                            result = a + b;
                            break;
                        case "-":
                            result = a - b;
                            break;
                        case "*":
                            result = a * b;
                            break;
                        default:
                            result = a / b;
                            break;
                    }
                    // Проверка если цифры римские, то перевести результат из арабских в римские
                    if (isRoman) {
                        total = converter.intToRoman(result);

                    //Иначе вывести результат арабскими цифрами
                    } else {
                        total = String.valueOf(result);
                    }
                    return total;
                } else {
                    throw new NumberFormatException("\nВведите числа от 1 до 10");
                }
            } else {
                throw new NumberFormatException("\nЧисла должны быть в одном формате");
            }

        } catch (NumberFormatException e) {
            return String.valueOf(e);
        } catch (ArrayIndexOutOfBoundsException e) {
            return "Введите выражение вида: [1 + 2] или [I + II].\n" + "Вводимые операторы должны быть: +, -, *, /.\n" + "Имя исключения: " + e;
        } catch (NullPointerException e) {
            return "Ответ не может быть меньше I.\nИмя исключения: " + e;
        }

    }

    static class Converter {
        TreeMap<Character, Integer> romanKeyMap = new TreeMap<>();
        TreeMap<Integer, String> arabianKeyMap = new TreeMap<>();

        public Converter() { // заполнение  ключей и значений для Римских и Арабских цифр
            romanKeyMap.put('I', 1);
            romanKeyMap.put('V', 5);
            romanKeyMap.put('X', 10);
            romanKeyMap.put('L', 50);
            romanKeyMap.put('C', 100);
            arabianKeyMap.put(100, "C");
            arabianKeyMap.put(90, "XC");
            arabianKeyMap.put(50, "L");
            arabianKeyMap.put(40, "XL");
            arabianKeyMap.put(10, "X");
            arabianKeyMap.put(9, "IX");
            arabianKeyMap.put(5, "V");
            arabianKeyMap.put(4, "IV");
            arabianKeyMap.put(1, "I");

        }

        public boolean isRoman(String number) { //Проверяет, является ли значиение number Римской цифрой
            return romanKeyMap.containsKey(number.charAt(0));
        }

        //15
        public String intToRoman(int number) { //Преобразовывает из Арабских цифр в Римские
            String roman = "";
            int arabianKey;
            do {
                arabianKey = arabianKeyMap.floorKey(number);
                roman += arabianKeyMap.get(arabianKey);
                number -= arabianKey;
            } while (number != 0);
            return roman;


        }

        public int romanToInt(String s) { //Преобразовывает из Римских цифр в Арабские
            int end = s.length() - 1;
            char[] arr = s.toCharArray();
            int arabian;
            int result = romanKeyMap.get(arr[end]);
            for (int i = end - 1; i >= 0; i--) {
                arabian = romanKeyMap.get(arr[i]);

                if (arabian < romanKeyMap.get(arr[i + 1])) {
                    result -= arabian;
                } else {
                    result += arabian;
                }

            }
            return result;

        }
    }
}