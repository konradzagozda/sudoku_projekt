package komponentowe.zadanie2;

import java.util.Arrays;

public class SudokuColumn extends SudokuStructure {

    public SudokuColumn(SudokuField[] fields) {
        super(fields);
    }

    @Override
    public SudokuColumn tryValue(int value) {
        for (int i = 0; i < 9; i++) {
            if (fields[i].getFieldValue() == 0) {
                fields[i].setFieldValue(value);
                return new SudokuColumn(fields);
            }
        }
        return new SudokuColumn(fields);
    }
}
