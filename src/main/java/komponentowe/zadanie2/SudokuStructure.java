package komponentowe.zadanie2;

import java.util.ArrayList;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;



public abstract class SudokuStructure {
    protected SudokuField[] fields;


    public SudokuStructure(SudokuField[] fields) {
        this.fields = new SudokuField[9];
        for (int i = 0; i < 9; i++) {
            this.fields[i] = new SudokuField(fields[i].getFieldValue());
        }

    }

    /**
     * Used to verify if current state of structure is correct => meaning there are no duplicates
     * other than 0s.
     *
     * @return true if values are unique (0s are ignored)
     */
    public boolean verify() {
        ArrayList<Integer> allFields = new ArrayList<>();
        for (SudokuField field : fields
        ) {
            if (field.getFieldValue() != 0) {
                allFields.add(field.getFieldValue());
            }
        }

        ArrayList<Integer> noDuplicates = new ArrayList<>();
        for (Integer value : allFields
        ) {
            if (!noDuplicates.contains(value)) {
                noDuplicates.add(value);
            }
        }

        return noDuplicates.size() == allFields.size();
    }


    public SudokuStructure tryValue(int value) {
        for (int i = 0; i < 9; i++) {
            if (fields[i].getFieldValue() == 0) {
                fields[i].setFieldValue(value);
                break;
            }
        }
        return this;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (!(o instanceof SudokuStructure)) {
            return false;
        }

        SudokuStructure that = (SudokuStructure) o;

        return new EqualsBuilder()
                .append(fields, that.fields)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(fields)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fields", fields)
                .toString();
    }
}
