package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import domain.Validation;

public class FieldIsEmptyToolsTest {

    /**
    * @subcontract name is filled in {
    *   @requires name = "Jort";
    *   @signals \result = false;
    * }
    */

    @Test 
    public void testValidatefieldIsEmptyRequiresNameIsJortEnsuresFalse()  {
       //Arrange
       String name = "Jort"; 
       //Act
       boolean result = Validation.fieldIsEmpty(name);
       //Assert
       assertEquals(false, result);
    }

    /**
    * @subcontract null Name {
    *   @requires Name is null;
    *   @signals (NullPointerException) name == null;
    * }
    */

    @Test (expected=NullPointerException.class)
    public void testValidatefieldIsEmptyRequiresNameIsNullEnsuresNullPointerException()  {
       //Arrange
       String name = null; 
       //Act
       boolean result = Validation.fieldIsEmpty(name);
    }

    /**
    * @subcontract gender combobox is filled in {
    *   @requires gender combobox = "M";
    *   @signals \result = false;
    * }
    */

    @Test
    public void testValidatefieldIsEmptyRequiresGenderMEnsuresFalse()  {
       //Arrange
       String gender = "M"; 
       //Act
       boolean result = Validation.fieldIsEmpty(gender);
       //Assert
       assertEquals(false, result);
    }

    /**
    * @subcontract gender combobox isn't filled in {
    *   @requires gender combobox isEmpty();
    *   @signals (NullPointerException) gender == null;
    * }
    */

    @Test (expected=NullPointerException.class)
    public void testValidatefieldIsEmptyRequiresGenderIsNullEnsuresNullPointerException()  {
       //Arrange
       String gender = null; 
       //Act
       boolean result = Validation.fieldIsEmpty(gender);
    }

    /**
    * @subcontract Street is filled in {
    *   @requires Street is Brabantplein;
    *   @signals \result = false;
    * }
    */

    @Test
    public void testValidatefieldIsEmptyRequiresStreetIsBrabantpleinEnsuresFalse()  {
       //Arrange
       String street = "Brabantplein"; 
       //Act
       boolean result = Validation.fieldIsEmpty(street);
       //Assert
       assertEquals(false, result);
    }   
    
    /**
    * @subcontract Street isn't filled in {
    *   @requires Street is null;
    *   @signals  (NullPointerException) Street == null;
    * }
    */

    @Test (expected=NullPointerException.class)
    public void testValidatefieldIsEmptyRequiresHouseNumberIsNullEnsuresNullPointerException()  {
       //Arrange
       String street = null; 
       //Act
       boolean result = Validation.fieldIsEmpty(street);
    }

    /**
    * @subcontract houseNumber is filled in {
    *   @requires houseNumber is 8;
    *   @signals \result = false;
    * }
    */

    @Test
    public void testValidatefieldIsEmptyRequiresHouseNumber8EnsuresFalse()  {
       //Arrange
       String houseNumber = "8"; 
       //Act
       boolean result = Validation.fieldIsEmpty(houseNumber);
       //Assert
       assertEquals(false, result);
    }

    /**
    * @subcontract houseNumber isn't filled in {
    *   @requires HouseNumber is null;
    *   @signals (NullPointerException) houseNumber == null;
    * }
    */

    @Test (expected=NullPointerException.class)
    public void testValidatefieldIsEmptyRequiresStreetIsNullEnsuresFalse()  {
       //Arrange
       String houseNumber = null; 
       //Act
       boolean result = Validation.fieldIsEmpty(houseNumber);
    }

    /**
    * @subcontract Residence is filled in {
    *   @requires residence is Breda;
    *   @signals \result = false;
    * }
    */

    @Test
    public void testValidatefieldIsEmptyRequiresResidenceBredaEnsuresFalse()  {
       //Arrange
       String residence = "8"; 
       //Act
       boolean result = Validation.fieldIsEmpty(residence);
       //Assert
       assertEquals(false, result);
    }

    /**
    * @subcontract Residence isn't filled in {
    *   @requires Residence is null;
    *   @signals (NullPointerException) Residence == null;
    * }
    */

    @Test (expected=NullPointerException.class)
    public void testValidatefieldIsEmptyRequiresResidenceIsNullEnsuresFalse()  {
       //Arrange
       String residence = null; 
       //Act
       boolean result = Validation.fieldIsEmpty(residence);
    }    

    /**
    * @subcontract Country is filled in {
    *   @requires Country is The Netherlands;
    *   @signals \result = false;
    * }
    */

    @Test
    public void testValidatefieldIsEmptyCountryResidenceBredaEnsuresFalse()  {
       //Arrange
       String country = "The Netherlands"; 
       //Act
       boolean result = Validation.fieldIsEmpty(country);
       //Assert
       assertEquals(false, result);
    }

    /**
    * @subcontract Country isn't filled in {
    *   @requires Country is null;
    *   @signals (NullPointerException) Country == null;
    * }
    */

    @Test (expected=NullPointerException.class)
    public void testValidatefieldIsEmptyRequiresCountryIsNullEnsuresFalse()  {
       //Arrange
       String country = null; 
       //Act
       boolean result = Validation.fieldIsEmpty(country);
    }
}
