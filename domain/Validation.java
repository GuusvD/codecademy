package domain;

import java.util.Calendar;

//Class that performs certain validations to check if a given input was correct
public abstract class Validation {

    //Method that checks a given email
    public static boolean checkEmail(String email){
        //regex checks if the email starts with one or more letters followed by a "@" one or more letters a "." and at the end one or more letters
        String regex = "^[a-zA-z]{1,}[@][a-zA-Z]{1,}[.][a-zA-Z]{1,}";
        if(email.matches(regex)){
            System.out.println("Your email is correct");
            return true;
        }
        System.out.println("Your email format is incorrect");
        return false;
    }

    //Method that checks a given date
    public static boolean checkDate(int day, int month, int year){
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);

        //checks if the day is between 1 and 31 after that is checks if the month contains 31 days after that it checks if the year is between now and this year minus 120 years
        if((day <= 31 && day > 0) && (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) && currentYear >= year && year > currentYear-120){
            System.out.println("The given date is correct");
            return true;
        //if the first statemt isn't true it checks if the day is between 1 and 30 after that is checks if the month contains 30 days after that it checks if the year is between now and this year minus 120 years
        } else if ((day <= 30 && day > 0) && (month == 4 || month == 6 || month == 9 || month == 11)  && currentYear >= year && year > currentYear-120) {
            System.out.println("The given date is correct");
            return true;
        //if the first and second statemt aren't true it checks if the day is between 1 and 28 after that is checks if the month is febuary (the second month) after that it checks if the year is between now and this year minus 120 years
        } else if ((day <= 28 && day > 0) && (month == 2 && (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0)) && currentYear >= year && year > currentYear-120)){
            System.out.println("The given date is correct");
            return true;
        } else if(day >= 1 && day <= 29 && (month == 2 && (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0))) && currentYear >= year && year > currentYear-120) {
            return true;
        } 

        System.out.println("The given date in incorrect");
        return false;
       
    }

    //Method that checks a given postal code
    public static boolean checkPostalCode(String pc){
        //regex checks if the first digit is between the 1 and 9 and if the next three digits are between the 0 and 9 followed by a space and ends with two capital letters
        String regex = "[1-9][0-9]{3}[ ][A-Z]{2}";

        if(pc.trim().matches(regex)){
            System.out.println("The postal code is correct");
            return true;
        }

        System.out.println("The postal code is incorrect");
        throw new IllegalArgumentException();
        //return false;

    }

    //Method that checks a given url
    public static boolean checkUrl(String url){
        //Regex checks if the url starts with https:// or with http:// that it checks if there are one or more caraters followed by a "." then another set of caraters another "." and if there are 1 or more caraters at the end
        String regex = "^(https://|http://)[a-zA-Z]{1,}[.][a-zA-Z]{1,}[.][a-zA-Z]{1,}";

        if(url.matches(regex)){
            System.out.println("The URL is correctly formatted");
            return true;
        }

        System.out.println("The URL isn't correctly formatted");
        return false;
    }
  
    //Method that checks a given grade
    public static boolean checkGrade(int grade){
        //checks if the given grad is greater or equal to 1 and less or equal to 10
        if(grade >= 1 && grade <= 10){
            System.out.println("The given grade is correct");
            return true;
        }
        System.out.println("The grade must be between 1-10");
        return false;
    }

    // Method that checks if a field is empty
    public static boolean fieldIsEmpty(String text){
        //checks if the textfield is empty
        if(text.isEmpty()){   
            return true; 
        }
        return false;
    }

    //Method that checks a given percentage
    public static boolean percentage(int percentage){
        String per = percentage + "";
        String regex = "^([0-9]|([1-9][0-9])|100)$";
        //check if the percentage matches the regex (between 0 and 100)
        if(per.matches(regex)){
            System.out.println("The percentage is right");
            return true;
        }
    
        System.out.println("The percentage must be between 0-100");
        return false;
    }
}
