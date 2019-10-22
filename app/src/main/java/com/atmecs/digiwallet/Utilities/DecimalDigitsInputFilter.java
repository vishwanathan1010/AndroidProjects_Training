package com.atmecs.digiwallet.Utilities;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalDigitsInputFilter implements InputFilter {
    private final int digitsBeforeZero;
    private final int digitsAfterZero;
    private Pattern pattern;

    public DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
        this.digitsBeforeZero = digitsBeforeZero;
        this.digitsAfterZero = digitsAfterZero;
        applyPattern(digitsBeforeZero, digitsAfterZero);
    }

    /**
     *  pattern to allow two digits only after digit.
     * @param digitsBeforeZero
     * @param digitsAfterZero
     */
    private void applyPattern(int digitsBeforeZero, int digitsAfterZero) {
        pattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)|(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned destination, int destinationStart, int destinationEnd) {
        if (destination.toString().contains(".") || source.toString().contains("."))
            applyPattern(digitsBeforeZero + 2, digitsAfterZero);
        else
            applyPattern(digitsBeforeZero, digitsAfterZero);

        Matcher matcher = pattern.matcher(destination);
        if (!matcher.matches())
            return "";
        return null;
    }

}