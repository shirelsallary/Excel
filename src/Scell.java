public class Scell {

    private String text;

    public Scell(String text) {
        this.text = text;
    }

    public Scell() {}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    // Check if the string is a valid number
    public boolean isNumber(String num) {
        if (num == null || num.isEmpty()) return false;

        try {
            Double.parseDouble(num);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Check if the string is plain text
    public boolean isText(String text) {
        if (text == null || text.isEmpty()) return false;

        return (!isNumber(text) && text.charAt(0) != '=');
    }

    // Checks if the given string is a valid formula.
// A valid formula must start with '=' and contain a mathematically valid expression.
    public boolean isForm(String str) {

        // Formula cannot be null or too short (must contain at least '=' and something after)
        if (str == null || str.length() < 2) return false;

        // Formula must start with '='
        if (str.charAt(0) != '=') return false;

        // Remove '=' to check the actual mathematical expression
        String text = str.substring(1);

        // First check that parentheses are balanced
        if (!checkBrackets(text)) return false;

        // Then check mathematical validity (operators, numbers, etc.)
        return checkMathExpression(text);
    }


    // Checks that parentheses are balanced and appear in the correct order
    private boolean checkBrackets(String text) {

        int bracketCount = 0;

        for (char c : text.toCharArray()) {

            // Opening parenthesis increases the counter
            if (c == '(') bracketCount++;

            // Closing parenthesis decreases the counter
            if (c == ')') bracketCount--;

            // If counter becomes negative → closing bracket appeared before opening
            if (bracketCount < 0) return false;
        }

        // At the end the counter must be zero (all parentheses closed)
        return bracketCount == 0;
    }


    // Checks the mathematical correctness of the expression
// Ensures that operators are placed correctly and that illegal characters are not used
    private boolean checkMathExpression(String text) {

        boolean lastWasOperator = true; // At start we expect a number/variable, not an operator

        for (int i = 0; i < text.length(); i++) {

            char c = text.charAt(i);

            // If current character is an operator
            if ("+-*/".indexOf(c) != -1) {

                // Two operators in a row → invalid
                if (lastWasOperator) return false;

                lastWasOperator = true;
            }

            // Valid characters: digits, decimal point, letters (for cell references), parentheses
            else if (Character.isDigit(c) || c == '.' || Character.isLetter(c) || c == '(' || c == ')') {

                // If it's not parentheses we mark that we saw a value
                if (c != '(' && c != ')')
                {
                    lastWasOperator = false;
                }
            }

            // Any other character is illegal in a formula
            else {
                return false;
            }
        }

        // Expression cannot end with an operator
        return !lastWasOperator;
    }

    // Check if a character is an allowed operator
    public boolean allowedSign(char sign) {

        if (sign == '+') return true;
        if (sign == '-') return true;
        if (sign == '*') return true;
        if (sign == '/') return true;
        if (sign == '.') return true;

        return Character.isLetter(sign);
    }

    // Find the index of the first letter in a string
    public int findFirstLetterIndex(String str) {

        for (int i = 0; i < str.length(); i++) {

            if (Character.isLetter(str.charAt(i))) {
                return i;
            }
        }

        return -1;
    }

    // Recursively computes the value of a formula
    public double computeForm(String form) {

        // Remove '=' if exists
        if (form.startsWith("=")) {
            form = form.substring(1);
        }

        form = form.trim();

        // If the expression is just a number
        if (isNumber(form)) {
            return Double.parseDouble(form);
        }

        // Remove outer parentheses ONLY if they wrap the entire expression
        if (hasOuterParentheses(form)) {
            return computeForm(form.substring(1, form.length() - 1));
        }

        // Find the main operator outside parentheses
        int opIndex = findMainOperator(form);

        if (opIndex == -1) {
            throw new IllegalArgumentException("Invalid formula: " + form);
        }

        char op = form.charAt(opIndex);

        String left = form.substring(0, opIndex);
        String right = form.substring(opIndex + 1);

        double leftVal = computeForm(left);
        double rightVal = computeForm(right);

        switch (op) {
            case '+': return leftVal + rightVal;
            case '-': return leftVal - rightVal;
            case '*': return leftVal * rightVal;
            case '/': return leftVal / rightVal;
        }

        throw new IllegalArgumentException("Invalid operator");
    }


    // Checks if the parentheses at the edges wrap the whole expression
    private boolean hasOuterParentheses(String form) {

        if (!form.startsWith("(") || !form.endsWith(")")) {
            return false;
        }

        int brackets = 0;

        for (int i = 0; i < form.length(); i++) {

            char c = form.charAt(i);

            if (c == '(') brackets++;
            if (c == ')') brackets--;

            // If we closed all parentheses before the end,
            // then the outer parentheses do NOT wrap the whole expression
            if (brackets == 0 && i < form.length() - 1) {
                return false;
            }
        }

        return true;
    }


    // Finds the main operator outside parentheses
    private int findMainOperator(String form) {

        int brackets = 0;

        // First search for + or -
        for (int i = form.length() - 1; i >= 0; i--) {

            char c = form.charAt(i);

            if (c == ')') brackets++;
            else if (c == '(') brackets--;

            else if (brackets == 0 && (c == '+' || c == '-')) {
                return i;
            }
        }

        brackets = 0;

        // Then search for * or /
        for (int i = form.length() - 1; i >= 0; i--) {

            char c = form.charAt(i);

            if (c == ')') brackets++;
            else if (c == '(') brackets--;

            else if (brackets == 0 && (c == '*' || c == '/')) {
                return i;
            }
        }

        return -1;
    }
}