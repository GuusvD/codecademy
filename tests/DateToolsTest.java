package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Calendar;

import org.junit.Test;

import domain.Validation;

public class DateToolsTest {
    /**
    * @subcontract 31 days in month {
    *   @requires (month == 1 || month == 3 || month == 5 || month == 7 || 
    *             month == 8 || month == 10 || month == 12) && 1 <= day <= 31;
    *   @ensures \result = true;
    * }
    **/

     @Test
     public void testValidateDateRequiresDay31Month10Year2000EnsuresTrue()  {
        //Arrange
        int day = 31;
        int month = 10;
        int year = 2000;
        //Act
        boolean result = Validation.checkDate(day, month, year);
        //Assert
        assertEquals(true, result);
    }

    /**
    * @subcontract 30 days in month {
    *   @requires (month == 4 || month == 6 || month == 9 || month == 11) &&
    *              1 <= day <= 30;
    *   @ensures \result = true;
    * }
    **/

    @Test
    public void testValidateDateRequiresDay30Month6Year2000EnsuresTrue() {
        //Arrange
        int day = 30;
        int month = 6;
        int year = 2000;
        //Act
        boolean result = Validation.checkDate(day, month, year);
        //Assert
        assertEquals(true, result);
    }

    /** 
    * @subcontract 29 days in month {
    *   @requires month == 2 && 1 <= day <= 29 &&
    *             (year % 4 == 0 && (year % 100 != 0 || year % 400 == 0));
    *   @ensures \result = true;
    * }
    **/

    @Test
    public void testValidateDateRequiresDay29Month2Year1996EnsuresTrue() {
        //Arrange
        int day = 29;
        int month = 2;
        int year = 1996;
        //Act
        boolean result = Validation.checkDate(day, month, year);
        //Assert
        assertEquals(true, result);
    }

    /** 
    * @subcontract 28 days in month {
    *   @requires month == 2 && 1 <= day <= 28 &&
    *             (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0));
    *   @ensures \result = true;
    * }
    **/

    @Test
    public void testValidateDateRequiresDay28Month2Year1999EnsuresTrue() {
        //Arrange
        int day = 28;
        int month = 2;
        int year = 1999;
        //Act
        boolean result = Validation.checkDate(day, month, year);
        //Assert
        assertEquals(true, result);
    }

    /** 
    * @subcontract all other cases {
    *   @requires no other accepting precondition;
    *   @ensures \result = false;
    * }
    **/ 

    @Test
    public void testValidateDateRequiresDay32Month6Year2000EnsuresFalse() {
       //Arrange
       int day = 32;
       int month = 6;
       int year = 2000;
        //Act
       boolean result = Validation.checkDate(day, month, year);
        //Assert
        assertEquals(false, result);
    }

    /**
    * @subcontract negative count of days {
    *   @requires a negative number as day;
    *   @ensures \result = false;
    * }
    **/

    @Test
    public void testValidateDateRequiresDayNegativeNumberMonth6Year2000EnsuresFalse() {
        //Arrange
        int day = -1;
        int month = 6;
        int year = 2000;
        //Act
        boolean result = Validation.checkDate(day, month, year);
        //Assert
        assertEquals(false, result);
    }

    /**
    * @subcontract count of days is zero {
    *   @requires day count is zero;
    *   @ensures \result = false;
    * }
    **/

    @Test
    public void testValidateDateRequiresDay0Month6Year2000EnsuresFalse() {
        //Arrange
        int day = 0;
        int month = 6;
        int year = 2000;
        //Act
        boolean result = Validation.checkDate(day, month, year);
        //Assert
        assertEquals(false, result);
    }

    /**
    * @subcontract count of month is zero {
    *   @requires month count is zero;
    *   @ensures \result = false;
    * }
    **/

    @Test
    public void testValidateDateRequiresDay5Month0Year2000EnsuresFalse() {
        //Arrange
        int day = 5;
        int month = 0;
        int year = 2000;
        //Act
        boolean result = Validation.checkDate(day, month, year);
        //Assert
        assertEquals(false, result);
    } 
    
    /**
    * @subcontract count of month is a negative number {
    *   @requires a negative number as months;
    *   @ensures \result = false;
    * }
    **/

    @Test
    public void testValidateDateRequiresDay5MonthNegativeNumberYear2000EnsuresFalse() {
        //Arrange
        int day = 5;
        int month = -99;
        int year = 2000;
        //Act
        boolean result = Validation.checkDate(day, month, year);
        //Assert
        assertEquals(false, result);
    }
    
    /**
    * @subcontract count of month is a negative number {
    *   @requires a month is 13;
    *   @ensures \result = false;
    * }
    **/

    @Test
    public void testValidateDateRequiresDay5Month13Year2000EnsuresFalse() {
        //Arrange
        int day = 5;
        int month = 13;
        int year = 2000;
        //Act
        boolean result = Validation.checkDate(day, month, year);
        //Assert
        assertEquals(false, result);
    } 

    /**
    * @subcontract the person is born in the future {
    *   @requires a year that is higher then the currentyear;
    *   @ensures \result = false;
    * }
    **/

    @Test
    public void testValidateDateRequiresDay5Month2YearTwoHigherThenCurrenYearEnsuresFalse() {
        //Arrange
        int day = 5;
        int month = 2;
        int year = Calendar.getInstance().get(Calendar.YEAR) + 2;
        //Act
        boolean result = Validation.checkDate(day, month, year);
        //Assert
        assertEquals(false, result);
    } 
    
    /**
    * @subcontract the person is born to 150 years ago {
    *   @requires the persons birthYear is currentYear minus 150;
    *   @ensures \result = false;
    * }
    **/

    @Test
    public void testValidateDateRequiresDay5Month2YearCurrenYearminus150EnsuresFalse() {
        //Arrange
        int day = 5;
        int month = 2;
        int year = Calendar.getInstance().get(Calendar.YEAR) -150;
        //Act
        boolean result = Validation.checkDate(day, month, year);
        //Assert
        assertEquals(false, result);
    }    
}
