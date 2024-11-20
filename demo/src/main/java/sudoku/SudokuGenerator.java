package sudoku;


import java.util.Random;

public class SudokuGenerator {
    private static final int SIZE = 9; // Sudoku méret
    private static final int SUBGRID_SIZE = 3; // A 3x3-as blokkok mérete
    private static final int EMPTY_CELLS = 14; // Ennyi üres hely lesz a táblán

    private int[][] board;
    private Random random = new Random();

    public SudokuGenerator() {
        board = new int[SIZE][SIZE];
        generateFullBoard();
        removeNumbers();
    }

    public int[][] getBoard() {
        return board;
    }

    // Teljes tábla generálása backtrackinggel
    private boolean generateFullBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if (board[row][col] == 0) {
                    // Random sorrendben próbálunk számokat
                    int[] numbers = generateShuffledNumbers();
                    for (int num : numbers) {
                        if (isValidPlacement(row, col, num)) {
                            board[row][col] = num;
                            if (generateFullBoard()) {
                                return true;
                            }
                            board[row][col] = 0;
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    // Véletlen sorrendben generál számokat 1-től 9-ig
    private int[] generateShuffledNumbers() {
        int[] numbers = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            numbers[i] = i + 1;
        }
        for (int i = SIZE - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = numbers[i];
            numbers[i] = numbers[j];
            numbers[j] = temp;
        }
        return numbers;
    }



    // Szám eltávolítása a rácsból
    private void removeNumbers() {
        int removed = 0;
        while (removed < EMPTY_CELLS) {
            int row = random.nextInt(SIZE);
            int col = random.nextInt(SIZE);
            if (board[row][col] != 0) {
                board[row][col] = 0;
                removed++;
            }
        }
    }

    // Ellenőrzi, hogy egy szám érvényesen helyezhető-e el egy cellába
    private boolean isValidPlacement(int row, int col, int num) {
        // Sor és oszlop ellenőrzése
        for (int i = 0; i < SIZE; i++) {
            if (board[row][i] == num || board[i][col] == num) {
                return false;
            }
        }
        // 3x3-as blokk ellenőrzése
        int startRow = row - row % SUBGRID_SIZE;
        int startCol = col - col % SUBGRID_SIZE;
        for (int i = 0; i < SUBGRID_SIZE; i++) {
            for (int j = 0; j < SUBGRID_SIZE; j++) {
                if (board[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }
        return true;
    }
}

