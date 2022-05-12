import java.util.ArrayList;

class Cell {
    int value = 0;

    void increment() {
        value += 1;
        if (value > 255) value = 0;
    }

    void decrement() {
        value -= 1;
        if (value < 0) value = 255;
    }
}

class Tape {
    ArrayList<Cell> tape = new ArrayList<Cell>();
    private int pointer = 0;

    public Tape() {
        tape.add(new Cell());
    }

    void shiftRight() {
        this.pointer += 1;
        if (this.pointer == this.tape.size()) this.tape.add(new Cell());
    }
    
    void shiftLeft() throws Exception {
        this.pointer -= 1;
        if (this.pointer < 0) throw new Exception("Pointer out of range");
    }

    void increment() {
        this.tape.get(this.pointer).increment();
    }

    void decrement() {
        this.tape.get(this.pointer).decrement();
    }
}

class Interpreter {
    String code;
    Tape tape = new Tape();
    int tokenNum = 0;

    public Interpreter(String code) {
        this.code = code;
    }

    void interpret() throws Exception {
        while (tokenNum < this.code.length()) {
            switch (this.code.charAt(this.tokenNum)) {
                case '>':
                    tape.shiftRight();
                    break;
                case '<':
                    tape.shiftLeft();
                    break;
                case '+':
                    tape.increment();
                    break;
                case '-':
                    tape.decrement();
                    break;
            }
            
            tokenNum++;
        }
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
}
