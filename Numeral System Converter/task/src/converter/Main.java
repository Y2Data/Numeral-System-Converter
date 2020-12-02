package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String sourceBase = null;
        String sourceNum = null;
        String newBase = null;
        while (true) {
            try {
                sourceBase = sc.nextLine();
                sourceNum = sc.nextLine();
                newBase = sc.nextLine();
            } catch (Exception e) {
                System.out.println("Error");
                e.printStackTrace();
                break;
            }

            if (!inputIsOK(sourceBase, sourceNum,newBase))
                break;


            if (isInteger(sourceNum)) {
                System.out.println(handleIntPart(sourceNum, sourceBase, newBase));
                break;
            } else {
                String intPart = handleIntPart(divideIntFrac(sourceNum)[0], sourceBase, newBase);
                String fracPart = handleFracPart(divideIntFrac(sourceNum)[1], sourceBase, newBase);
                System.out.println(combineIntFrac(intPart, fracPart));
                break;
            }
        }


    }

    private static boolean inputIsOK (String a, String b, String c) {
        try {
            if (a == null | b == null | c == null) {
                System.out.println("Error! Missing a number or two here");
                return false;
            }
            int d = Integer.parseInt(a);
            int f = Integer.parseInt(c);
            if ((d < 1 | d > 36 | f < 1 | f > 36)) {
                System.out.println("Error! Base can't be greater than 36 nor lower than 1");
                return false;
            } else
                return true;
        } catch (NullPointerException | NumberFormatException e) {
            System.out.println("Error! It's not a number!!");
            return false;
        }
    }
    private static String handleFracPart(String fracPart, String sourceBase, String newBase) {
        String decimalFrac = fracToDecimal(sourceBase, fracPart);
        return decimalFracToNewBase(newBase, decimalFrac);
    }

    private static String combineIntFrac(String intPart, String fracPart) {
        return intPart + "." + fracPart;
    }

    private static String handleIntPart(String intPart, String sourceBase, String newBase) {
        String decimalInt = intToDecimal(sourceBase, intPart);
        return decimalIntToNewBase(newBase, decimalInt);
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch (NullPointerException | NumberFormatException e) {
            return false;
        }
        return true;
    }

    private static String[] divideIntFrac(String sourceNum) {
        String[] result = new String[2];
        if (!isInteger(sourceNum)) {
            int point = sourceNum.indexOf(".");
            result[0] = sourceNum.substring(0, point);
            result[1] = sourceNum.substring(point+1);
            return result;
        } else {
            result[0] = sourceNum;
            result[1] = "0";
            return result;
        }

    }

    private static String baseOneToDecimal(String sourceNum) {
        return String.valueOf(sourceNum.length());
    }

    private static String decimalToBaseOne(String sourceNum) {
        return String.valueOf(Integer.parseInt("1".repeat(Math.max(0, Integer.parseInt(sourceNum)))));
    }

    private static String intToDecimal(String sourceBase, String sourceNum){
        if (Integer.parseInt(sourceBase) == 10)
            return sourceNum;
        else if (Integer.parseInt(sourceBase) == 1)
            return baseOneToDecimal(sourceNum);
        else
            return String.valueOf(Integer.parseInt(sourceNum, Integer.parseInt(sourceBase)));
    }

    private static String decimalIntToNewBase(String newBase, String decimalNum) {
        return Integer.parseInt(newBase) == 1 ? decimalToBaseOne(decimalNum) : Integer.toString(Integer.parseInt(decimalNum), Integer.parseInt(newBase));
    }

    private static String decimalFracToNewBase(String newBase, String fracNum) {
        int digits = 5;
        var sb = new StringBuilder();
        if (!fracNum.equals("")) {
            for (int i = 1; i <= digits; i++) {
                fracNum = "0." + fracNum;
                double num = Double.parseDouble(fracNum);
                String newBaseNum = String.valueOf(num * Integer.parseInt(newBase));
                String t = divideIntFrac(newBaseNum)[0];
                String result = String.valueOf(Integer.toString(Integer.parseInt(t), Integer.parseInt(newBase)));
                sb.append(result);
                fracNum = divideIntFrac(newBaseNum)[1];
            }
        }
        return sb.toString();
    }


    private static String fracToDecimal(String sourceBase, String fracNum) {
        int base = Integer.parseInt(sourceBase);
        if (Integer.parseInt(sourceBase) == 10) {
            return fracNum;
        } else {
            char[] temp = fracNum.toCharArray();
            double result = 0.0;
            for (int i = 0; i < temp.length; i++) {
                int a = Integer.parseInt(String.valueOf(temp[i]), base);
                result += a / (double) (base * Math.pow(base, i));
            }
            String out = String.valueOf(result);
            return divideIntFrac(out)[1];
        }
    }
}
