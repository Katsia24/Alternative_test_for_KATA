import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    Scanner in = new Scanner(System.in);
        System.out.println("Input:");
        System.out.println("Output:\n" + calc(in.nextLine().trim()));
        in.close();
}
    public static String calc (String input){
        String[] elements = new String[3];
        if(input.indexOf(" + ") != -1){
            elements[0] = input.substring(0, input.lastIndexOf("+"));
            elements[1] = input.substring(input.indexOf("+"), input.lastIndexOf("+")+1);
            elements[2] = input.substring(input.lastIndexOf("+")+1);
        }else {
            if(input.indexOf(" - ") != -1){
                elements[0] = input.substring(0, input.lastIndexOf("-"));
                elements[1] = input.substring(input.indexOf("- "), input.lastIndexOf("-")+1);
                elements[2] = input.substring(input.lastIndexOf("-")+1);
            }else {
                if(input.indexOf(" * ") != -1){
                    elements[0] = input.substring(0, input.lastIndexOf("*"));
                    elements[1] = input.substring(input.indexOf("*"), input.lastIndexOf("*")+1);
                    elements[2] = input.substring(input.lastIndexOf("*")+1).trim();
                }else {
                    if(input.indexOf(" / ") != -1){
                        elements[0] = input.substring(0, input.lastIndexOf("/"));
                        elements[1] = input.substring(input.indexOf("/"), input.lastIndexOf("/")+1);
                        elements[2] = input.substring(input.lastIndexOf("/")+1).trim();
                    }else {
                        try {
                            throw new IOException();
                        } catch (IOException e) {
                            return "throws Exception //т.к. формат математической операции не удовлетворяет заданию - два операнда и один оператор (+, -, /, *)";
                        }
                    }
                }
            }
        }

        Element element = new Element(elements[0], elements[2], elements[1]);
        try {
            return element.calculation();
        } catch (IOException e) {
            return "throws Exception //т.к. формат математической операции не удовлетворяет заданию";
        }
    }
}
class Element {
    String a;
    String b;
    String el;

    public Element(String a, String b, String el) {
        this.a = a;
        this.b = b;
        this.el = el;
    }
    String calculation() throws IOException {
        String result = "";
        int n2 = 0;
        if(a.indexOf("\"") == -1){
                throw new IOException(); //нету кавычек у первого элемента
        }
        a = a.substring(a.indexOf("\"")+1, a.lastIndexOf("\""));
        if(a.length() > 10 || b.length() > 10){
            throw new IOException(); //Строка не длинее 10 символов
        }
        if(b.indexOf("\"") == -1){
            int bInt = 0;
            try {
                bInt = Integer.parseInt(b);
            }catch (NumberFormatException exception) {
                throw new IOException(); //Первый элемент строка, а второй не строка и не число
            }
            if(!(bInt >= 1 && bInt <= 10)){
                throw new IOException(); //Ввод только от 1 до 10
            }
            switch (el) {
                case ("*"):
                    for(int i = 0; i < bInt; i++){
                        result = result + a;
                    }
                    if(result.length() > 40){
                        result = result.substring(0,40) + "...";
                    }
                    break;
                case ("/"):
                    result = a.substring(0,a.length() / bInt);
                    break;
                default:
                    throw new IOException(); //Не найден элемент * /
            }
            return "\"" + result + "\"";
        }else {
            b = b.substring(b.indexOf("\"")+1, b.lastIndexOf("\""));
        }
        switch (el) {
            case ("+"):
                result = a + b;
                break;
            case ("-"):
                if(a.indexOf(b) == -1){
                    result = a;
                }else {
                    result = a.substring(a.indexOf(a), a.indexOf(b));
                }
                break;
            default:
                throw new IOException(); //Не найден элемент + -
        }
        return "\"" + result + "\"";
    }
}