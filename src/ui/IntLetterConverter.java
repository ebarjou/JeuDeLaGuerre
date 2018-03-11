package ui;

public class IntLetterConverter {

    /**
     * @param s a String that contain only letters
     * @return the convertion in base 10 of thoses letters.
     * Case where there is something else than letters is not handled.
     */
    static int getIntFromLetters(String s) {
        s = s.toLowerCase();
        int res = 0;
        int base = 26;
        int stage = 0;
        for (int i = s.length() - 1; i >= 0; --i) {
            char c = s.charAt(i);
            res += (Math.pow(base, stage) * ((c - 'a') + 1));
            stage++;
        }
        return res - 1;
    }

    static String getLettersFromInt(int n) {
        String res = "";
        int base = 26;
        while (n >= 0) {
            int val = n % base;
            char c = 'A';
            c += val;
            res = c + res;
            n = n / base - 1;
        }
        return res;
    }
}
