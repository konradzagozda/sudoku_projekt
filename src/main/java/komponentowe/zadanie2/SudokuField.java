package komponentowe.zadanie2;

public class SudokuField {
    private int value;

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        if (value <= 9 && value >= 1) {
            this.value = value;
        }
    }
}
