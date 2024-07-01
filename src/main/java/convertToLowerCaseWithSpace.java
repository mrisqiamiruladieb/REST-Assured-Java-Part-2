public class convertToLowerCaseWithSpace {

    public static void main(String[] args) {
        String inputString = "successDeleteUser";
        String outputString = convertToLowerCaseWithSpace(inputString);
        System.out.println(outputString); // Output: "success get user by id"
    }

    public static String convertToLowerCaseWithSpace(String inputString) {
        StringBuilder result = new StringBuilder();

        // Append the first character as it is
        result.append(inputString.charAt(0));

        // Iterate through the rest of the string
        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);

            // If current character is uppercase, add space and lowercase version
            if (Character.isUpperCase(currentChar)) {
                result.append(' ').append(Character.toLowerCase(currentChar));
            } else {
                result.append(currentChar);
            }
        }

        return result.toString();
    }
}
