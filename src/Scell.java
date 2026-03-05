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

    // Check if the string is a formula
    public boolean isForm(String str) {

        if (str == null || str.length() < 2) return false;

        if (str.charAt(0) != '=') return false;

        String text = str.substring(1);

        int bracketCount = 0;

        for (char c : text.toCharArray()) {

            if (c == '(') bracketCount++;

            if (c == ')') bracketCount--;

            if (bracketCount < 0) return false;
        }

        return bracketCount == 0;
    }

    // Recursively compute a formula
    public double computeForm(String form) {

        if (form.charAt(0) == '=') {
            form = form.substring(1);
        }

        if (isNumber(form)) {
            return Double.parseDouble(form);
        }

        // Handle parentheses
        if (form.startsWith("(") && form.endsWith(")")) {
            return computeForm(form.substring(1, form.length() - 1));
        }

        // Addition
        if (form.contains("+")) {
            int ind = form.indexOf("+");

            double left = computeForm(form.substring(0, ind));
            double right = computeForm(form.substring(ind + 1));

            return left + right;
        }

        // Subtraction
        if (form.contains("-")) {
            int ind = form.indexOf("-");

            double left = computeForm(form.substring(0, ind));
            double right = computeForm(form.substring(ind + 1));

            return left - right;
        }

        // Multiplication
        if (form.contains("*")) {
            int ind = form.indexOf("*");

            double left = computeForm(form.substring(0, ind));
            double right = computeForm(form.substring(ind + 1));

            return left * right;
        }

        // Division
        if (form.contains("/")) {
            int ind = form.indexOf("/");

            double left = computeForm(form.substring(0, ind));
            double right = computeForm(form.substring(ind + 1));

            return left / right;
        }

        return Double.parseDouble(form);
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
}