package cz.muni.fi.pv168.seminar3.team3.ui.model;

import org.jdatepicker.AbstractDateModel;
import org.jdatepicker.DateModel;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Class for presenting dates in a pleasant format
 *
 * @author Adam Majzlik
 */
public class LocalDateModel extends AbstractDateModel<LocalDate> implements DateModel<LocalDate> {

    @Override
    protected Calendar toCalendar(LocalDate from) {
        return GregorianCalendar.from(from.atStartOfDay(ZoneId.systemDefault()));
    }

    @Override
    protected LocalDate fromCalendar(Calendar from) {
        return from.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
