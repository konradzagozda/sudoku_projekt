package komponentowe.zadanie2;

import java.util.ArrayList;

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
                return this;
            }
        }
        return this;
    }

}
