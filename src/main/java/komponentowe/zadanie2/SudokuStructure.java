package komponentowe.zadanie2;

import java.util.ArrayList;

public abstract class SudokuStructure {
    protected SudokuField[] fields = new SudokuField[9];


    public SudokuStructure(SudokuField[] fields) {
        this.fields = fields;
    }

    public SudokuStructure() {
        for (int i = 0; i < 9; i++) {
            fields[i] = new SudokuField();
        }
    }

    /**
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


    public abstract <T extends SudokuStructure> T tryValue(int value);
}
