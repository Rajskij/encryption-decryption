package encryptdecrypt;

public class Main {
    public static void main(String[] args) {
        String mode = "";
        String data = "";
        int key = 0;

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
            }
        }
        String[] dataArr = data.split("");
        System.out.println(encryption(mode, key, dataArr));
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
