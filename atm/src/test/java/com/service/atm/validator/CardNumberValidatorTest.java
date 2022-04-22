package com.service.atm.validator;

public class CardNumberValidatorTest {

    private CardNumberValidator cardNumberValidator;

    @Before
    public void setUp() throws Exception {
        cardNumberValidator = new CardNumberValidator();
    }

    @Test
    public void testIsValidCreditCardNumber() {
        long number = 4388576018402626L;
        boolean result = cardNumberValidator.isValidCreditCardNumber(number);
        assertTrue(result);
    }

    @Test
    public void testIsValidCreditCardNumberInvalidSize() {
        long number = 12345678901234567L;
        boolean result = cardNumberValidator.isValidCreditCardNumber(number);
        assertFalse(result);
    }
}

public class CardNumberValidatorTest {

    private CardNumberValidator cardNumberValidator;

    @Before
    public void setUp() throws Exception {
        cardNumberValidator = new CardNumberValidator();
    }

    @Test
    public void testGetSize() {
        long number = 123456789;
        int expected = 9;
        int actual = cardNumberValidator.getSize(number);
        assertEquals(expected, actual);
    }

    @Test
    public void testGetSizeWithZero() {
        assertEquals(0, cardNumberValidator.getSize(0));
    }
}

public class CardNumberValidatorTest {

    private CardNumberValidator cardNumberValidator;

    @Before
    public void setUp() throws Exception {
        cardNumberValidator = new CardNumberValidator();
    }

    @Test
    public void testPrefixMatched() {
        assertTrue(cardNumberValidator.prefixMatched(4388576018402626L, 4));
    }

    @Test
    public void testPrefixMatchedNumberOfDigitsInDIsLessThanK() {
        long number = 123456789;
        int d = 123;
        boolean result = cardNumberValidator.prefixMatched(number, d);
        assertTrue(result);
    }
}

public class CardNumberValidatorTest {

    private CardNumberValidator cardNumberValidator;

    @Before
    public void setUp() throws Exception {
        cardNumberValidator = new CardNumberValidator();
    }

    @Test
    public void testSumOfOddPlace() {
        int[] arr = {1, 3, 5, 7, 9};
        assertEquals(25, cardNumberValidator.sumDigits(arr));
    }

    @Test
    public void testSumOfOddPlaceWithZero() {
        assertEquals(0, cardNumberValidator.sumOfOddPlace(0));
    }
}

public class CardNumberValidatorTest {

    private CardNumberValidator cardNumberValidator;

    @Before
    public void setUp() throws Exception {
        cardNumberValidator = new CardNumberValidator();
    }

    @Test
    public void testGetDigit() {
        assertEquals(1, cardNumberValidator.getDigit(1));
        assertEquals(2, cardNumberValidator.getDigit(12));
        assertEquals(3, cardNumberValidator.getDigit(123));
    }

    @Test
    public void testGetDigitWithZero() {
        assertEquals(0, cardNumberValidator.getDigit(0));
    }
}