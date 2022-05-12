import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;

class Cell {
    int value = 0;

    boolean isTrue() {
        return this.value > 0;
    }

    boolean isFalse() {
        return this.value == 0;
    }

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

    Cell getCell() {
        return this.tape.get(this.pointer);
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

    String readTape() {
        String acc = "";

        for (int i = 0; i < this.tape.size(); i++) {
            if (i == this.pointer) acc += " >" + Integer.toString(this.tape.get(i).value) + "< ";
            else acc += "  " + Integer.toString(this.tape.get(i).value) + "  ";
        }

        return acc;
    }
}

class StringQueue {
    String queue = "";

    void add(String input) {
        this.queue += input;
    }

    char read() {
        char readChar = this.queue.charAt(0);
        this.queue = this.queue.substring(1);
        return readChar;
    }

    boolean isEmpty() {
        return this.queue.length() == 0; 
    }
}

class Interpreter {
    String code;
    Tape tape = new Tape();
    int tokenNum = 0;
    Stack<Integer> loopStack = new Stack<Integer>();
    Scanner scanner = new Scanner(System.in);
    StringQueue stringQueue = new StringQueue();

    public Interpreter(String code) {
        this.code = code;
    }

    void close() {
        this.scanner.close();
    }

    void interpret() throws Exception {
        while (this.tokenNum < this.code.length()) {
            char character = this.code.charAt(this.tokenNum);
            switch (character) {
                case '>':
                    tape.shiftRight();
                    tokenNum++;
                    break;
                case '<':
                    tape.shiftLeft();
                    tokenNum++;
                    break;
                case '+':
                    tape.increment();
                    tokenNum++;
                    break;
                case '-':
                    tape.decrement();
                    tokenNum++;
                    break;
                case '[':
                    if (tape.getCell().isTrue()) {
                        loopStack.push(tokenNum);
                        tokenNum++;
                    }
                    else {
                        int skipCount = 1;
                        while (skipCount > 0) {
                            tokenNum++;
                            switch (this.code.charAt(this.tokenNum)) {
                                case '[':
                                    skipCount++;
                                    break;
                                case ']':
                                    skipCount--;
                                    break;
                            }
                        }
                        tokenNum++;
                    }
                    break;
                case ']':
                    if (tape.getCell().isTrue()) tokenNum = loopStack.pop();
                    else tokenNum++;
                    break;
                case ',':
                    if (stringQueue.isEmpty()) stringQueue.add(scanner.nextLine());
                    tape.getCell().value = stringQueue.read();
                    tokenNum++;
                    break;
            }
            System.out.println("(" + character + ") " + this.tape.readTape());
        }
    }
}

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Hello, World!");
    }
}
