package komponentowe.zadanie2;

import komponentowe.zadanie2.SudokuBoard;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class SaveObject implements Serializable {
    private SudokuBoard original;
    private SudokuBoard current;


    public SaveObject(SudokuBoard current, SudokuBoard original) {
        this.original = original;
        this.current = current;
    }

    public SaveObject(SudokuBoard board) throws CloneNotSupportedException {
        this.original = board.clone(); // will remain untouched because its a clone
        this.current = board; // will change dynamically as user changes fields
    }

    public SudokuBoard getOriginal() {
        return original;
    }

    public SudokuBoard getCurrent() {
        return current;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("original:\n");
        builder.append(original);
        builder.append("current:\n");
        builder.append(current);
        return builder.toString();
    }
}
