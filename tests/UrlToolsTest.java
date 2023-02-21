package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import domain.Validation;

public class UrlToolsTest {

    /**
    * @subcontract a valid url {
    * @requires https:// or http:// followed by at least one caracter a dot at least one caracter a dot at least one caracter;
    * @ensures \result = true;
    * }
    **/

    @Test
    public void testIsValidUrlRequiresUrlhttpsColonSlashSlashwwwDotTestDotComEnsuresTrue() {
        // Arrange
        String url = "https://www.test.com";
        // Act
        boolean result = Validation.checkUrl(url);
        // Assert
        assertEquals(true, result);
    }

    /**
    * @subcontract a valid url {
    * @requires https:// or http:// followed by at least one caracter a dot at least one caracter a dot at least one caracter;
    * @ensures \result = true;
    * }
    **/

    @Test
    public void testIsValidUrlRequiresUrlhttpColonSlashSlashwwwDotTestDotComEnsuresTrue() {
        // Arrange
        String url = "http://www.test.com";
        // Act
        boolean result = Validation.checkUrl(url);
        // Assert
        assertEquals(true, result);
    }

    /**
    * @subcontract an invalid url {
    * @requires htps:// followed by at least one caracter a dot at least one caracter a dot at least one caracter;
    * @ensures \result = false;
    * }
    **/

    @Test
    public void testIsValidUrlRequiresUrlhtpsColonSlashSlashwwwDotTestDotComEnsuresTrue() {
        // Arrange
        String url = "htps://www.test.com";
        // Act
        boolean result = Validation.checkUrl(url);
        // Assert
        assertEquals(false, result);
    }

    /**
    * @subcontract an invalid url {
    * @requires https:// or http:// a dot at least one caracter a dot at least one caracter;
    * @ensures \result = false;
    * }
    **/

    @Test
    public void testIsValidUrlRequiresUrlhttpsColonSlashSlashDotTestDotComEnsuresTrue() {
        // Arrange
        String url = "https://.test.com";
        // Act
        boolean result = Validation.checkUrl(url);
        // Assert
        assertEquals(false, result);
    }    

    /**
    * @subcontract an invalid url {
    * @requires https:// or http:// followed by at least one caracter a dot at least one caracter;
    * @ensures \result = false;
    * }
    **/

    @Test
    public void testIsValidUrlRequiresUrlhttpsColonSlashSlashWwwtestDotComEnsuresTrue() {
        // Arrange
        String url = "https://wwwtest.com";
        // Act
        boolean result = Validation.checkUrl(url);
        // Assert
        assertEquals(false, result);
    } 
    
    /**
    * @subcontract an invalid url {
    * @requires https:// or http:// followed by at least one caracter a dot at least one caracter a dot;
    * @ensures \result = false;
    * }
    **/

    @Test
    public void testIsValidUrlRequiresUrlhttpsColonSlashSlashWwwDottestDotEnsuresTrue() {
        // Arrange
        String url = "https://www.test.";
        // Act
        boolean result = Validation.checkUrl(url);
        // Assert
        assertEquals(false, result);
    }  

    /**
    * @subcontract an invalid url {
    * @requires https:// or http:// at least one caracter;
    * @ensures \result = false;
    * }
    **/

    @Test
    public void testIsValidUrlRequiresUrlhttpsColonSlashSlashWwwtestcomEnsuresTrue() {
        // Arrange
        String url = "https://wwwtestcom";
        // Act
        boolean result = Validation.checkUrl(url);
        // Assert
        assertEquals(false, result);
    }  

    /**
    * @subcontract an invalid url {
    * @requires https:// or http:// at least one caracter;
    * @ensures \result = false;
    * }
    **/

    @Test
    public void testIsValidUrlRequiresUrlWwwDotTestDotcomEnsuresTrue() {
        // Arrange
        String url = "www.test.com";
        // Act
        boolean result = Validation.checkUrl(url);
        // Assert
        assertEquals(false, result);
    }
    
    /**
    * @subcontract a valid url {
    * @requires https:// or http:// followed by at least one caracter a dot at least one caracter a dot at least one caracter;
    * @ensures \result = true;
    * }
    **/

    @Test
    public void testIsValidUrlRequiresUrlhttpsColonSlashSlashwDotTDotCEnsuresTrue() {
        // Arrange
        String url = "https://w.t.c";
        // Act
        boolean result = Validation.checkUrl(url);
        // Assert
        assertEquals(true, result);
    }    

}
