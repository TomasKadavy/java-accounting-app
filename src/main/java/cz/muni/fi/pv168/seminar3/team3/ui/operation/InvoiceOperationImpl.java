package cz.muni.fi.pv168.seminar3.team3.ui.operation;

import cz.muni.fi.pv168.seminar3.team3.model.Invoice;
import cz.muni.fi.pv168.seminar3.team3.model.Project;
import cz.muni.fi.pv168.seminar3.team3.ui.error.ErrorDialog;
import cz.muni.fi.pv168.seminar3.team3.ui.i18n.I18N;

import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Invoice operation implementation
 *
 * @author Vojtěch Sýkora
 * @param <E> entity class
 */
public class InvoiceOperationImpl<E> extends AbstractOperation implements InvoiceOperation {

    private final EditSupport<E> editSupport;
    private static final I18N I18N = new I18N(InvoiceOperation.class);

    public InvoiceOperationImpl(EditSupport<E> editSupport, String description) {
        super(description);
        this.editSupport = editSupport;
        this.editSupport.addListSelectionListener(e -> fireActionEnabledChange());
    }

    @Override
    public boolean isEnabled() {
        return editSupport.getSelectedRowCount() > 0;
    }

    @Override
    public void createInvoice() {
        var selectedRows = editSupport.getSelectedRows();
        List<Project> projects = new ArrayList<>();
        selectedRows.forEach(index -> projects.add((Project) editSupport.getEditableModel().getEntity(index)));

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle(I18N.getString("fileChooserLabel"));

        if (fileChooser.showDialog(new JButton(), I18N.getString("createButton")) == JFileChooser.APPROVE_OPTION) {
            String path = fileChooser.getSelectedFile().getAbsolutePath();
            try { File file = new File( path + "\\" + I18N.getString("fileSuffix"));
                FileWriter fileWriter = new FileWriter(file);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                Invoice invoice = new Invoice(projects);
                invoice.writeInvoice(printWriter);
                printWriter.close();
            } catch (IOException exception) {
                ErrorDialog.show(I18N.getString("fileError"), exception);
            }
        }
    }

}
