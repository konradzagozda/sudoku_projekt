package komponentowe.zadanie2;

public class SudokuRow extends SudokuStructure {

    public SudokuRow(SudokuField[] fields) {
        super(fields);
    }

    @Override
    public SudokuRow tryValue(int value) {
        for (int i = 0; i < 9; i++) {
            if (fields[i].getFieldValue() == 0) {
                fields[i].setFieldValue(value);
                return new SudokuRow(fields);
            }
        }
        return new SudokuRow(fields);
    }
}
