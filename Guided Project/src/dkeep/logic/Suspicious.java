package dkeep.logic;

public class Suspicious extends Guard {

    /**
     * Constructs a new Suspicious Guard in the given position, represented internally by the given char
     * @param line: guard's line
     * @param column: guard's column
     * @param charc: guard's representative char
     */
    public Suspicious(int line, int column, char charc) {
        super(line, column, charc);
    }

    @Override
    public void moveChar(){

        this.reverse = super.generateBool();

        if(reverse) {
            updateCoords(reverseTraject[trajInd]);
            decInd();
        }
        else {
            super.moveChar();
        }
    }
}
