package org.concourseci.sdk.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    public static void validateIdentifier(String identifier) throws RuntimeException {
        Pattern pattern = Pattern.compile("^[\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}][\\p{Ll}\\p{Lt}\\p{Lm}\\p{Lo}\\d\\-_.]*");
        Matcher matcher = pattern.matcher(identifier);

        if (!matcher.matches()) {
            throw new RuntimeException("Not valid identifier");
        }
    }

}
