package komponentowe.zadanie2;

public class SudokuBox extends SudokuStructure {
    public SudokuBox(SudokuField[] fields) {
        super(fields);
    }

    @Override
    public SudokuBox tryValue(int value) {
        for (int i = 0; i < 9; i++) {
            if (fields[i].getFieldValue() == 0) {
                fields[i].setFieldValue(value);
                return new SudokuBox(fields);
            }
        }
        return new SudokuBox(fields);
    }


}
