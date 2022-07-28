package cz.muni.fi.pv168.seminar3.team3.ui.i18n;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * Class for internationalization
 *
 *
 * @author Vojtěch Sýkora
 * @since milestone-2
 *
 */
public final class I18N {

    private final ResourceBundle bundle;
    private final String prefix;

    public I18N(Class<?> clazz) {
        var packagePath = clazz.getPackageName().replace(".", "/") + '/';
        bundle = ResourceBundle.getBundle(packagePath + "i18n");
        prefix = clazz.getSimpleName() + ".";
    }

    public String getString(String key) {
        return bundle.getString(prefix + key);
    }

    public <E extends Enum<E>> String getString(E key) {
        return bundle.getString(key.getClass().getSimpleName() + "." + key.name());
    }
}
