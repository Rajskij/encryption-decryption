package encryptdecrypt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String mode = "enc";
        String data = "";
        String in = "";
        String out = "";
        String alg = "shift";
        int key = 0;

        for (int i = 0; i < args.length - 1; i++) {
            if (args[i].contains("-mode")) {
                mode = args[i + 1];
            } else if (args[i].contains("-key")) {
                key = Integer.parseInt(args[i + 1]);
            } else if (args[i].contains("-data")) {
                data = args[i + 1].contains("-") ? " " : args[i + 1];
            } else if (args[i].contains("-in")) {
                in = args[i + 1].contains("-") ? " " : args[i + 1];
            } else if (args[i].contains("-out")) {
                out = args[i + 1].contains("-") ? " " : args[i + 1];
            } else if (args[i].contains("-alg")) {
                alg = args[i + 1].contains("-") ? " " : args[i + 1];
            }
        }
        if (data.length() < 1 && in.length() > 1) {
            try (Scanner sc = new Scanner(new File(in));) {
                data = sc.nextLine();
            } catch (FileNotFoundException e) {
                System.out.println("No file found: ");
            }
        }
        String[] dataArr = data.split("");

        AlgoFactory algoFactory = new AlgoFactory();

        System.out.println(algoFactory.algo(alg, mode, key, dataArr));

        try (FileWriter writer = new FileWriter(new File(out))) {
            if (out.length() > 1) {
                writer.write(algoFactory.algo(alg, mode, key, dataArr));
                writer.close();
            } else {
                System.out.println(algoFactory.algo(alg, mode, key, dataArr));
            }
        } catch (IOException e) {
            System.out.println(out + " " + e.getMessage());
        }
    }
}

class AlgoFactory {
    public String algo (String alg, String mode, int key, String[] data) {
        switch (alg) {
            case "unicode": return new Unicode().encAndDec(mode, key, data);
            case "shift": return new Shift().encAndDec(mode, key, data);
            default: return null;
        }
    }
}

abstract class Algorithms {
    public abstract String encAndDec(String mode, int key, String[] data);
}

class Unicode extends Algorithms {
    @Override
    public String encAndDec(String mode, int key, String[] data) {
        StringBuilder encrypt = new StringBuilder(new String());
        for (int i = 0; i < data.length; i++) {
            int index = mode.equals("dec") ? data[i].charAt(0) - key : data[i].charAt(0) + key;
            encrypt.append((char) index);
        }
        return encrypt.toString();
    }
}

class Shift extends Algorithms {
    @Override
    public String encAndDec(String mode, int key, String[] data) {
        StringBuilder encrypt = new StringBuilder(new String());
        String abc = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < data.length; i++) {
            int index = abc.indexOf(data[i].toLowerCase());
            int n = mode.equals("enc") ? (index + key) % abc.length()
                    : (26 + index - key) % 26;
            if(data[i].matches("\\W")) {
                encrypt.append(data[i]);
            } else if (abc.toUpperCase().contains(data[i])) {
                encrypt.append(abc.toUpperCase().charAt(n));
            } else {
                encrypt.append(abc.charAt(n));
            }
        }
        return encrypt.toString();
    }
}
