package com.example.brainvira_task.Util;

import android.text.TextUtils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * Contains all the logic for input validation.
 */
public class Validator {

    private Validator() {
    }

    private static final Pattern VALID_US_ZIP_CODE = Pattern.compile("^[0-9]{5}$");
    private static final Pattern VALID_CANADA_ZIP_CODE = Pattern.compile("^(?!.*[DFIOQU])[A-VXY][0-9][A-Z] ?[0-9][A-Z][0-9]$");
    private static final Pattern VALIDATE_FIRST_LAST_NAME = Pattern.compile("^.{2,32}$");
    private static final Pattern VALID_FIS_NAME = Pattern.compile("^[a-zA-Z0-9]{2,32}$");
    private static final Pattern VALID_ALPHANUMERIC = Pattern.compile("^[a-zA-Z0-9 ]*$");
    private static final Pattern VALID_PHONE_NUMBER = Pattern.compile("^[0-9]{10}$");
    private static final Pattern VALID_ROUTING_NUMBER = Pattern.compile("^[0-9]{9}$");
    private static final Pattern VALID_ACCOUNT_NUMBER = Pattern.compile("^[0-9]{6,17}$");
    private static final Pattern VALID_PIN_NUMBER = Pattern.compile("^[0-9]{4}$");
    private static final Pattern CONTAINS_NUMERIC_DIGIT = Pattern.compile("(?=.*\\d)");
    private static final Pattern CONTAINS_NUMBERS_AND_OR_LETTERS = Pattern.compile("^[a-zA-Z0-9]+$");
    private static final Pattern CONTAINS_PERIOD = Pattern.compile("\\.");
    private static final int DOB_MIN_AGE_REQUIRED = 13;

    private static final Pattern VALID_LOYALTY_CARD_BEFORE_RELEASE = Pattern.compile("^[0-9]{18}$");


    /**
     * Determine if the zip code is valid.
     *
     * @param zipCode five-digit zip code.
     * @return true if valid, false otherwise
     */
    public static boolean validateUSZipCode(final String zipCode) {
        if (TextUtils.isEmpty(zipCode)) {
            return false;
        }

        return VALID_US_ZIP_CODE.matcher(zipCode).matches();
    }

    public static boolean checkValidCode(final String input) {
        if (TextUtils.isEmpty(input)) {
            return false;
        }
        return input.length() == 6 && Validator.containsNumericDigit(input);

    }



    public static boolean validateCanadaZipCode(final String zipCode) {
        if (TextUtils.isEmpty(zipCode)) {
            return false;
        }

        return VALID_CANADA_ZIP_CODE.matcher(zipCode).matches();
    }


    /**
     * Determine if the (first or last) name is valid.
     *
     * @param name user's name.
     * @return true if valid, false otherwise
     */
    public static boolean validateName(final String name) {
        return VALIDATE_FIRST_LAST_NAME.matcher(name).matches();
    }

    /**
     * Determine if the string is alpha-numeric only.
     *
     * @param string user's string.
     * @return true if alphanumeric, false otherwise
     */
    public static boolean validateIsAlphaNumeric(final String string) {
        return VALID_ALPHANUMERIC.matcher(string).matches();
    }

    /**
     * Determine if the name for FIS is valid.
     *
     * @param name user's name.
     * @return true if valid, false otherwise
     */
    public static boolean validateFISName(final String name) {
        return VALID_FIS_NAME.matcher(name).matches();
    }

    /**
     * Determine if the loyalty card number is valid.
     *
     * @param loyaltyCardNumber user's loyalty card number.
     * @return true if valid, false otherwise
     */
    public static boolean validateLoyaltyCardNumber(final String loyaltyCardNumber) {
        return VALID_LOYALTY_CARD_BEFORE_RELEASE.matcher(loyaltyCardNumber.replace(" ", "")).matches();
    }



    public static boolean validateRoutingNumber(String routingNumber) {
        return VALID_ROUTING_NUMBER.matcher(routingNumber).matches();
    }

    public static boolean validateAccountNumber(String routingNumber) {
        return VALID_ACCOUNT_NUMBER.matcher(routingNumber).matches();
    }

    public static boolean validatePinNumber(String pinNumber) {
        return VALID_PIN_NUMBER.matcher(pinNumber).matches();
    }



    /**
     * Determine whether the input contains a numeric digit.
     *
     * @param input String to test
     * @return true if the input contains a numeric digit, false otherwise
     */
    public static boolean containsNumericDigit(final String input) {
        return CONTAINS_NUMERIC_DIGIT.matcher(input).find();
    }

    /**
     * Determines whether the input contains only numbers and letters.
     *
     * @param input String to test
     * @return true if the input only contains numbers and/or letters
     */
    public static boolean isNumbersAndLettersOnly(final String input) {
        return CONTAINS_NUMBERS_AND_OR_LETTERS.matcher(input).find();
    }

    /**
     * Determine whether the input's length is less than seven characters.
     *
     * @param input String to test
     * @return true if the input's length is less than seven characters, false otherwise
     */
    public static boolean isLessThanSevenCharacters(final String input) {
        return (input.length() < 7);
    }


    /**
     * Determine whether the input's length is more than twenty characters.
     *
     * @param input String to test
     * @return true if the input's length is more than twenty characters, false otherwise
     */
    public static boolean isMoreThanTwentyCharacters(final String input) {
        return (input.length() > 20);
    }


    public static boolean validateEmail(final String email) {
        EmailRequirements requirements = new EmailRequirements();

        // Email addresses consist of a local part, the "@" symbol, and the domain. (RFC 2822, section 3.4.1)
        String[] parts = email.split("\\@");
        boolean hasCorrectNumberOfParts = requirements.general.hasCorrectNumberOfParts = parts.length == 2 && !TextUtils.isEmpty(parts[0]) && !TextUtils.isEmpty(parts[1]);
        if (!hasCorrectNumberOfParts) {
            return false;
        }
        // Extract the local and domain parts.
        String localPart = parts[0];
        String domainPart = parts[1];


        // === Domain Part === \\

        // Consists of labels separated with periods. (RFC 1035, section 2.3.4)

        // No period can start or end a domain name.
        return validateEmailDomainPart(domainPart, requirements) && validateEmailLocalPart(localPart, requirements) && validateEmailInGeneral(email, requirements);
    }

    private static boolean validateEmailDomainPart(String domainPart, EmailRequirements requirements) {
        boolean doesNotStartOrEndWithPeriod = requirements.domainPart.doesNotStartOrEndWithPeriod = domainPart.charAt(0) != '.' && domainPart.charAt(domainPart.length() - 1) != '.';
        if (!doesNotStartOrEndWithPeriod) {
            return false;
        }

        // check if domain part contains period
        if (!CONTAINS_PERIOD.matcher(domainPart).find()) {
            return false;
        }

        String[] labels = domainPart.split("\\.");

        String topLevelDomain = labels[labels.length - 1];

        // The top-level domain must be all alphabetic. (RFC 3696, section 2)
        boolean isTDLAlphabetic = requirements.domainPart.isTDLAlphabetic = Pattern.compile("^[a-zA-Z]+$").matcher(topLevelDomain).matches();
        if (!isTDLAlphabetic) {
            return false;
        }

        boolean isExceedLimitAfterPeriod = requirements.domainPart.isExceedLimitAfterPeriod = topLevelDomain.length() >= 2 && topLevelDomain.length() <= 4;
        if (!isExceedLimitAfterPeriod) {
            return false;
        }

        // No two periods in succession can be in a domain name.  (RFC 1035, section 2.3.4)
        boolean containsAtMostOnePeriodInARow = requirements.domainPart.containsAtMostOnePeriodInARow = !domainPart.contains("..");
        if (!containsAtMostOnePeriodInARow) {
            return false;
        }

        // === Labels === \\

        // Can only contain the following characters a-z, A-Z, 0-9, underscores, hyphens (FIS requirement)
        boolean hasCorrectCharacters = requirements.domainPart.labels.hasCorrectCharacters = Pattern.compile("^[a-zA-Z0-9\\.\\-]+$").matcher(domainPart).matches();
        if (!hasCorrectCharacters) {
            return false;
        }

        // A label may contain hyphens, but no two hyphens in a row.
        boolean hasAtMostOneHyphenInARow = requirements.domainPart.labels.hasAtMostOneHyphenInARow = !domainPart.contains("--");
        if (!hasAtMostOneHyphenInARow) {
            return false;
        }

        // A label must not start nor end with a hyphen. (RFC 1035, section 2.3.4)
        requirements.domainPart.labels.doesNotStartOrEndWithAHyphen = true;
        for (String label : labels) {
            if (label.charAt(0) == '-' || label.charAt(label.length() - 1) == '-') {
                requirements.domainPart.labels.doesNotStartOrEndWithAHyphen = false;
            }
        }
        return requirements.domainPart.labels.doesNotStartOrEndWithAHyphen;
    }

    private static boolean validateEmailLocalPart(String localPart, EmailRequirements requirements) {
        // === Local Part === \\

        // Can only contain the following characters a-z, A-Z, 0-9, underscores, hyphens and periods,plus, percentage, Apostrophe and Single Quote  (FIS requirement)
        boolean hasCorrectCharacters = requirements.localPart.hasCorrectCharacters = !Pattern.compile("[^a-zA-Z0-9_\\-\\.`']").matcher(localPart).find();
        if (!hasCorrectCharacters) {
            return false;
        }

        // No apostrophe can start or end the local part. Two apostrophe "`" next to each other are invalid.
        boolean doesNotStartOrEndWithApostrophe = requirements.localPart.doesNotStartOrEndWithApostrophe = localPart.charAt(0) != '`' && localPart.charAt(localPart.length() - 1) != '`';
        if (!doesNotStartOrEndWithApostrophe) {
            return false;
        }

        // No single quote can start or end the local part. Two single quote "'" next to each other are invalid.
       boolean doesNotStartOrEndWithSingleQuote = requirements.localPart.doesNotStartOrEndWithSingleQuote = localPart.charAt(0) != '\'' && localPart.charAt(localPart.length() - 1) != '\'';
        if (!doesNotStartOrEndWithSingleQuote) {
            return false;
        }

        // No periods can start or end the local part. Two periods "." next to each other are invalid. (RFC 2822, section 3.4.1)
        boolean hasNoStartingOrEndingPeriods = requirements.localPart.hasNoStartingOrEndingPeriods = localPart.charAt(0) != '.' && localPart.charAt(localPart.length() - 1) != '.';
        if (!hasNoStartingOrEndingPeriods) {
            return false;
        }

        // No Hyphen can start or end the local part. Two Hyphen "-" next to each other are invalid. (RFC 2822, section 3.4.1)
        // As per comment in KNG-2853 , we should allow user with special character at end of local part. e.g. traci12_@hotmail.com
        boolean doesNotStartOrEndWithAHyphen = requirements.localPart.doesNotStartOrEndWithAHyphen = localPart.charAt(0) != '-';
        if (!doesNotStartOrEndWithAHyphen) {
            return false;
        }

        // No Hyphen can start or end the local part. Two Hyphen "-" next to each other are invalid. (RFC 2822, section 3.4.1)
        // As per comment in KNG-2853 , we should allow user with special character at end of local part. e.g. traci12_@hotmail.com
        boolean doesNotStartOrEndWithUnderscore = requirements.localPart.doesNotStartOrEndWithUnderscore = localPart.charAt(0) != '_';
        if (!doesNotStartOrEndWithUnderscore) {
            return false;
        }

        // No Plus can start or end the local part. Two Plus "+" next to each other are invalid. (RFC 2822, section 3.4.1)
        boolean doesNotStartOrEndWithPlus = requirements.localPart.doesNotStartOrEndWithPlus = localPart.charAt(0) != '+' && localPart.charAt(localPart.length() - 1) != '+';
        if (!doesNotStartOrEndWithPlus) {
            return false;
        }

        // No Percentage can start or end the local part. Two Percentage "%" next to each other are invalid. (RFC 2822, section 3.4.1)
      boolean doesNotStartOrEndWithPercentage =  requirements.localPart.doesNotStartOrEndWithPercentage = localPart.charAt(0) != '%' && localPart.charAt(localPart.length() - 1) != '%';
        if (!doesNotStartOrEndWithPercentage) {
            return false;
        }


        // Two periods "." next to each other are invalid.  (RFC 1035, section 2.3.4)
        boolean containsAtMostOnePeriodInARow = requirements.localPart.containsAtMostOnePeriodInARow = localPart.indexOf("..") == -1;
        if (!containsAtMostOnePeriodInARow) {
            return false;
        }

        // Two Apostrophe "`" next to each other are invalid
        boolean containsAtMostOneApostropheInARow = requirements.localPart.containsAtMostOneApostropheInARow = localPart.indexOf("''") == -1;
        if (!containsAtMostOneApostropheInARow) {
            return false;
        }

        // Two Single Quote "'" next to each other are invalid
        requirements.localPart.containsAtMostOneSingleQuoteInARow = localPart.indexOf("``") == -1;
        return requirements.localPart.containsAtMostOneSingleQuoteInARow;
    }

    private static boolean validateEmailInGeneral(String email, EmailRequirements requirements) {
        // Make sure the email address has characters to test against.
       boolean isNotNullOrEmpty =  requirements.general.isNotNullOrEmpty = !TextUtils.isEmpty(email);
        if (!isNotNullOrEmpty) {
            return false;
        }
        // Email addresses are maximum 50 characters in length (FIS requirement)
        // As per KNG-1958 this is now 64 characters
        requirements.general.isUnderMaxLength = email.length() <= 64;
        return requirements.general.isUnderMaxLength;
    }

    private static class EmailRequirements {

        private General general = new General();
        private LocalPart localPart = new LocalPart();
        private DomainPart domainPart = new DomainPart();

        private static class General {

            private Boolean isNotNullOrEmpty;
            private Boolean isUnderMaxLength;
            private Boolean hasCorrectNumberOfParts;
        }

        private static class LocalPart {

            private Boolean hasCorrectCharacters;
            private Boolean hasNoStartingOrEndingPeriods;
            private Boolean containsAtMostOnePeriodInARow;
            private Boolean doesNotStartOrEndWithAHyphen;
            private Boolean doesNotStartOrEndWithUnderscore;
            private Boolean doesNotStartOrEndWithPlus;
            private Boolean doesNotStartOrEndWithPercentage;
            private Boolean doesNotStartOrEndWithApostrophe;
            private Boolean doesNotStartOrEndWithSingleQuote;
            private Boolean containsAtMostOneApostropheInARow;
            private Boolean containsAtMostOneSingleQuoteInARow;
        }

        private static class DomainPart {

            private Boolean isTDLAlphabetic;
            private Boolean doesNotStartOrEndWithPeriod;
            private Boolean containsAtMostOnePeriodInARow;
            private Boolean isExceedLimitAfterPeriod;

            private Labels labels = new Labels();

            private class Labels {
                private Boolean hasCorrectCharacters;
                private Boolean hasAtMostOneHyphenInARow;
                private Boolean doesNotStartOrEndWithAHyphen;
            }
        }
    }
}