package encryptdecrypt;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String[] inputTxt = sc.nextLine().split("");
        int key = sc.nextInt();
        System.out.println(encryption(inputTxt,key));
    }
    public static String encryption(String[] input, int key) {
        StringBuilder encrypt = new StringBuilder(new String());
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < input.length; i++) {
            int index = (alphabet.indexOf(input[i]) + key) % alphabet.length();

            encrypt.append((input[i].matches("[\\w]")) ?
                    alphabet.charAt(index) : input[i]);
        }
        return encrypt.toString();
    }
}