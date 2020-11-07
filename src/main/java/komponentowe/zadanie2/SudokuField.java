package komponentowe.zadanie2;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
        return new ToStringBuilder(this)
                .append("value", value)
                .toString();
    }
}
