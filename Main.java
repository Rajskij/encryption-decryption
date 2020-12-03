package encryptdecrypt;

import java.io.*;
import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        String mode = "enc";
        String data = "";
        int key = 0;
        String in = "";
        String out = "";

        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].contains("-mode")) {
                mode = args[i + 1];
            } else if (args[i].contains("-key")) {
                try {
                    key = Integer.parseInt(args[i + 1]);
                } catch (NumberFormatException e) {
                    key = 0;
                }
            } else if (args[i].contains("-data")) {
                data = args[i + 1].contains("-") ? " " : args[i + 1];
            } else if (args[i].contains("-in")) {
                in = args[i + 1].contains("-") ? " " : args[i + 1];
            } else if (args[i].contains("-out")) {
                out = args[i + 1].contains("-") ? " " : args[i + 1];
            }
            if (data.length() < 1 && in.length() > 1) {
                try (Scanner sc = new Scanner(new File(in));) {
                    data = sc.nextLine();
                } catch (FileNotFoundException e) {
                    System.out.println("No file found: ");
                }
            }
        }
        String[] dataArr = data.split("");
        try (FileWriter writer = new FileWriter(new File(out))) {
            if (out.length() > 1) {
                writer.write(encryption(mode, key, dataArr));
                writer.close();
            } else {
                System.out.println(encryption(mode, key, dataArr));
            }
        } catch (IOException e) {
            System.out.println(out + " " + e.getMessage());
        }
    }

    public static String encryption(String mode, int key, String[] data) {
        StringBuilder encrypt = new StringBuilder(new String());
        for (int i = 0; i < data.length; i++) {
            int index = mode.equals("dec") ? data[i].charAt(0) - key : data[i].charAt(0) + key;
            encrypt.append((char) index);
        }
        return encrypt.toString();
    }
}
