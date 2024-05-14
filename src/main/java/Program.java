import java.util.Random;
import java.util.Scanner;

public class Program {

    private static final Scanner scanner = new Scanner(System.in);
    private static final Random random = new Random();
    private static int WIN_COUNT;
    private static final char DOT_HUMAN = 'X';
    private static final char DOT_AI = '0';
    private static final char DOT_EMPTY = '*';
    private static int fieldSizeX;
    private static int fieldSizeY;
    private static char[][] field;

    public static void main(String[] args) {
        while (true) {
            initialize(); //заполнение массива *
            printField(); //вывод игрового поля
            while (true) {
                humanTurn(); //изменение * на х (ход игрока)
                printField(); //вывод игрового поля
                if (checkState(DOT_HUMAN, "Вы победили!"))
                    break;
                aiTurn();
                printField();
                if (checkState(DOT_AI, "Победил компьютер!"))
                    break;
            }
            System.out.println("Желаете сыграть еще раз? (Y - да): ");
            if (!scanner.next().equalsIgnoreCase("Y"))

                break;
        }
    }

    /**
     * Инициализация объектов игры
     */
    static void initialize() {
        System.out.println("Выберите ширину игрового поля");
        fieldSizeX = scanner.nextInt();
        System.out.println("Выберите высоту игрового поля");
        fieldSizeY = scanner.nextInt();
        int max = fieldSizeX;
        if (max > fieldSizeY)
            max = fieldSizeY;

        System.out.println("Сколько фишек подряд будут составлять выйгрышную комбинацию? Выберите от 3 до " + max);
        WIN_COUNT = scanner.nextInt();
        field = new char[fieldSizeX][fieldSizeY];
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                field[x][y] = DOT_EMPTY;
            }
        }
    }

    /**
     * Печать текущего состояния игрового поля
     */
    static void printField() {
        System.out.print("+");
        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print("-" + (x + 1));
        }
        System.out.println("-");

        for (int x = 0; x < fieldSizeX; x++) {
            System.out.print(x + 1 + "|");
            for (int y = 0; y < fieldSizeY; y++) {
                System.out.print(field[x][y] + "|");
            }
            System.out.println();
        }

        for (int x = 0; x < fieldSizeX * 2 + 2; x++) {
            System.out.print("-");

        }
        System.out.println();
    }

    static void humanTurn() {
        int x;
        int y;
        do {
            System.out.println("Введите координаты хода Х и Y\n( от 1 до 3) через пробел: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;

        }
        while (!(isCellValid(x, y)) || !isCellEmpty(x, y));
        field[x][y] = DOT_HUMAN;
    }

    /**
     * Проверка, является ли ячейка игрового поля пустой
     *
     * @param x
     * @param y
     * @return
     */
    static boolean isCellEmpty(int x, int y) {
        return field[x][y] == DOT_EMPTY;
    }

    /**
     * Проверка валидности координат хода
     *
     * @param x
     * @param y
     * @return
     */
    static boolean isCellValid(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    /**
     * Ход игрока (компьютера)
     */
    static void aiTurn() {
        int x;
        int y;
        do {
            x = random.nextInt(fieldSizeX);
            y = random.nextInt(fieldSizeY);
        }
        while (!isCellEmpty(x, y));
        field[x][y] = DOT_AI;
    }

    /**
     * Проверка на ничью
     *
     * @return
     */
    static boolean checkDraw() {
        for (int x = 0; x < fieldSizeX; x++) {
            for (int y = 0; y < fieldSizeY; y++) {
                if (isCellEmpty(x, y)) return false;
            }

        }
        return true;
    }

    /**
     * Метод проверки победы
     *
     * @param dot фишка игрока
     * @return
     */
    static boolean checkWin(char dot) {
        //Проверка победы по горизонтали
        if (field[0][0] == dot && field[0][1] == dot && field[0][2] == dot) return true;
        if (field[1][0] == dot && field[1][1] == dot && field[1][2] == dot) return true;
        if (field[2][0] == dot && field[2][1] == dot && field[2][2] == dot) return true;

        //Проверка победы по вертикали
        if (field[0][0] == dot && field[1][0] == dot && field[2][0] == dot) return true;
        if (field[0][1] == dot && field[1][1] == dot && field[2][1] == dot) return true;
        if (field[0][2] == dot && field[1][2] == dot && field[2][2] == dot) return true;

        //Проверка побуды по диагонали
        if (field[0][0] == dot && field[1][1] == dot && field[2][2] == dot) return true;
        if (field[0][2] == dot && field[1][1] == dot && field[2][0] == dot) return true;

        return false;
    }

    static boolean checkWin2(char dot) {
        for (int x = 0; x <= fieldSizeX - WIN_COUNT; x++) {
            for (int y = 0; y <= fieldSizeY - 1; y++) {
                if (field[x][y] == dot && field[x + 1][y] == dot && field[x + 2][y] == dot) return true;
            }

        }
        for (int x = 0; x <= fieldSizeX - 1; x++) {
            for (int y = 0; y <= fieldSizeY - WIN_COUNT; y++) {
                if (field[x][y] == dot && field[x][y + 1] == dot && field[x][y + 2] == dot) return true;
            }

        }
        for (int x = 0; x <= fieldSizeX - WIN_COUNT; x++) {
            for (int y = 0; y <= fieldSizeY - WIN_COUNT; y++) {
                if (field[x][y] == dot && field[x + 1][y + 1] == dot && field[x + 2][y + 2] == dot) return true;
            }

        }
        for (int x = 0; x <= fieldSizeX - WIN_COUNT; x++) {
            for (int y = WIN_COUNT - 1; y <= fieldSizeY - 1; y++) {
                if (field[x][y] == dot && field[x + 1][y - 1] == dot && field[x + 2][y - 2] == dot) return true;
            }

        }
        return false;
    }

    /**
     * Проверка состояния игры
     *
     * @param dot фишка игрока
     * @param s   победный слоган
     * @return
     */
    static boolean checkState(char dot, String s) {
        if (checkWin2(dot)) {
            System.out.println(s);
            return true;
        } else if (checkDraw()) {
            System.out.println("Ничья!");
            return true;
        }
        return false; // Игра продолжается

    }
}
