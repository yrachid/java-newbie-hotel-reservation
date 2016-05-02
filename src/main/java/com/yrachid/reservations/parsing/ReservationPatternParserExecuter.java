package com.yrachid.reservations.parsing;


import com.yrachid.reservations.data.CustomerType;
import com.yrachid.reservations.data.FileLine;
import com.yrachid.reservations.data.Reservation;
import com.yrachid.reservations.exceptions.PatternException;

import java.io.File;
import java.util.*;

public class ReservationPatternParserExecuter implements PatternParserExecuter<Reservation> {

    private PatternParser<CustomerType> customerTypePatternParser;
    private PatternParser<Collection<GregorianCalendar>> datesPatternParser;

    private Map<FileLine, Exception> errors;
    private Collection<Reservation> parsedLines;

    public ReservationPatternParserExecuter(PatternParser<CustomerType> customerTypePatternParser, PatternParser<Collection<GregorianCalendar>> datesPatternParser) {
        this.customerTypePatternParser = customerTypePatternParser;
        this.datesPatternParser = datesPatternParser;

        errors = new HashMap<>();
        parsedLines = new ArrayList<>();
    }

    @Override
    public void execute(Collection<FileLine> lines) {

        for (FileLine line: lines) {

            try {

                CustomerType customerType = customerTypePatternParser.parse(line.value);
                Collection<GregorianCalendar> dates = datesPatternParser.parse(line.value);

                Reservation reservation = new Reservation(customerType, dates);

                parsedLines.add(reservation);

            } catch (PatternException error) {
                errors.put(line, error);
            }
        }

    }

    @Override
    public Map<FileLine, Exception> getErrors() {
        return errors;
    }

    @Override
    public Collection<Reservation> getValidParses() {
        return parsedLines;
    }

}
