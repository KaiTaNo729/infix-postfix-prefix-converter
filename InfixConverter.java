import java.util.Scanner;
import java.util.Stack;

/**
 * Infix Expression Converter
 * Converts infix expressions to Postfix and Prefix notation.
 *
 * Author  : KaiTaNo
 * Course  : ICT202 — Data Structures & Algorithms
 * Version : 1.0
 */
public class InfixConverter {

    // ─────────────────────────────────────────────────────────────────────────
    // Operator Precedence
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Returns the precedence level of an operator.
     * Higher value = higher precedence.
     *
     * @param ch the operator character
     * @return precedence level (1, 2, or 3), or -1 if not an operator
     */
    static int precedence(char ch) {
        switch (ch) {
            case '+':
            case '-': return 1;
            case '*':
            case '/': return 2;
            case '^': return 3;
        }
        return -1;
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Infix → Postfix
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Converts an infix expression to postfix notation
     * using the Shunting Yard algorithm.
     *
     * Example: A+B*C  →  ABC*+
     *
     * @param exp the infix expression string
     * @return the postfix expression string
     */
    static String infixToPostfix(String exp) {
        StringBuilder result = new StringBuilder();
        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < exp.length(); i++) {
            char c = exp.charAt(i);

            // Skip spaces
            if (c == ' ') continue;

            if (Character.isLetterOrDigit(c)) {
                // Operand — add directly to result
                result.append(c);

            } else if (c == '(') {
                // Left parenthesis — push to stack
                stack.push(c);

            } else if (c == ')') {
                // Right parenthesis — pop until matching '('
                while (!stack.isEmpty() && stack.peek() != '(') {
                    result.append(stack.pop());
                }
                if (!stack.isEmpty()) stack.pop(); // remove '('

            } else {
                // Operator — pop higher/equal precedence operators first
                while (!stack.isEmpty() && precedence(c) <= precedence(stack.peek())) {
                    result.append(stack.pop());
                }
                stack.push(c);
            }
        }

        // Pop remaining operators from stack
        while (!stack.isEmpty()) {
            result.append(stack.pop());
        }

        return result.toString();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Infix → Prefix
    // ─────────────────────────────────────────────────────────────────────────

    /**
     * Converts an infix expression to prefix notation.
     * Steps:
     *   1. Reverse the expression
     *   2. Swap '(' and ')'
     *   3. Apply infixToPostfix on the modified expression
     *   4. Reverse the postfix result
     *
     * Example: A+B*C  →  +A*BC
     *
     * @param exp the infix expression string
     * @return the prefix expression string
     */
    static String infixToPrefix(String exp) {
        // Step 1: Reverse the expression
        StringBuilder input = new StringBuilder(exp).reverse();

        // Step 2: Swap parentheses
        for (int i = 0; i < input.length(); i++) {
            if      (input.charAt(i) == '(') input.setCharAt(i, ')');
            else if (input.charAt(i) == ')') input.setCharAt(i, '(');
        }

        // Step 3 & 4: Get postfix then reverse
        String postfix = infixToPostfix(input.toString());
        return new StringBuilder(postfix).reverse().toString();
    }

    // ─────────────────────────────────────────────────────────────────────────
    // Main — Menu Driver
    // ─────────────────────────────────────────────────────────────────────────

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===================================");
            System.out.println("       INFIX EXPRESSION CONVERTER  ");
            System.out.println("===================================");
            System.out.println("  1. Convert to Postfix");
            System.out.println("  2. Convert to Prefix");
            System.out.println("  3. Exit");
            System.out.println("-----------------------------------");
            System.out.print("  Enter your choice: ");

            choice = sc.nextInt();
            sc.nextLine(); // clear newline from buffer

            if (choice == 1 || choice == 2) {
                System.out.print("  Enter Infix Expression: ");
                String infix = sc.nextLine();

                if (choice == 1) {
                    System.out.println("  ► Postfix : " + infixToPostfix(infix));
                } else {
                    System.out.println("  ► Prefix  : " + infixToPrefix(infix));
                }

            } else if (choice == 3) {
                System.out.println("\n  Exiting program. Goodbye!");

            } else {
                System.out.println("  ✗ Invalid choice! Please try again.");
            }

        } while (choice != 3);

        sc.close();
    }
}
