package komponentowe.zadanie2;

import java.io.Serializable;
import java.util.Objects;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;


public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {
    private int value;


    // shallow copy but needed to be public for sudokuboard clone...
    @Override
    public SudokuField clone() throws CloneNotSupportedException {
        return (SudokuField)super.clone();
    }

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
        } else {
            throw new IllegalArgumentException("wrong value");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuField)) {
            return false;
        }

        SudokuField that = (SudokuField) o;

        return new EqualsBuilder()
                .append(value, that.value)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(value)
                .toHashCode();
    }

    @Override
    public String toString() {
        return Integer.toString(this.value);
    }

    @Override
    public int compareTo(SudokuField o) {
        Objects.requireNonNull(o);
        return Integer.compare(this.value, o.value);
    }
}
