package komponentowe.zadanie2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;


public abstract class SudokuStructure {
    protected List<SudokuField> fields = Arrays.asList(new SudokuField[9]);


    public SudokuStructure(List<SudokuField> fields) {
        //this.fields = new /*SudokuField[9]*/ArrayList<>(9);
        for (int i = 0; i < 9; i++) {
            this.fields.set(i, new SudokuField(fields.get(i).getFieldValue()));
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
        for (SudokuField field : fields) {
            if (field.getFieldValue() != 0) {
                allFields.add(field.getFieldValue());
            }
        }

        ArrayList<Integer> noDuplicates = new ArrayList<>();
        for (Integer value : allFields) {
            if (!noDuplicates.contains(value)) {
                noDuplicates.add(value);
            }
        }
        return noDuplicates.size() == allFields.size();
    }

    public SudokuStructure tryValue(int value) {
        for (int i = 0; i < 9; i++) {
            if (fields.get(i).getFieldValue() == 0) {
                fields.get(i).setFieldValue(value);
                return this;
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
