package com.example.appimpulsioneai.Utils;

public class CPFValidator {

    public static boolean isValid(String cpf) {
        if (cpf == null || cpf.length() != 11 || cpf.matches(cpf.charAt(0) + "{11}")) {
            return false;
        }

        try {
            int[] digits = new int[11];
            for (int i = 0; i < 11; i++) {
                digits[i] = Integer.parseInt(cpf.substring(i, i + 1));
            }

            for (int j = 9; j < 11; j++) {
                int sum = 0;
                for (int i = 0; i < j; i++) {
                    sum += digits[i] * ((j + 1) - i);
                }
                int checkDigit = (sum * 10) % 11;
                if (checkDigit == 10 || checkDigit == 11) {
                    checkDigit = 0;
                }
                if (checkDigit != digits[j]) {
                    return false;
                }
            }
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
