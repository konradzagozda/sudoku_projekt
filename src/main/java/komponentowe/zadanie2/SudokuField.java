package komponentowe.zadanie2;

import java.util.Objects;

public class SudokuField {
    private int value;


    public SudokuField() {
    }

    public SudokuField(int value) {
        setFieldValue(value);
    }

    public SudokuField(SudokuField field) {
        this.value = field.getFieldValue();
    }

    public int getFieldValue() {
        return value;
    }

    public void setFieldValue(int value) {
        if (value <= 9 && value >= 0) {
            this.value = value;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SudokuField that = (SudokuField) o;
        return value == that.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
