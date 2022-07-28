package cz.muni.fi.pv168.seminar3.team3.ui.error;

import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

/**
 * Class for handling uncaught exception in view
 *
 * @author Vojtěch Sýkora
 * @since milestone-2
 *
 */
public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final I18N I18N = new I18N(UncaughtExceptionHandler.class);

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        ErrorDialog.show(I18N.getString("label"), e);
    }

}