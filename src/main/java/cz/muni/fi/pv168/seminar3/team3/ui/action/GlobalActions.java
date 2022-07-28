package cz.muni.fi.pv168.seminar3.team3.ui.action;

import javax.swing.Action;
import java.util.Objects;

/**
 * Class for representing all global actions supported in application
 *
 * @author Vojtech Sykora
 * @since milestone-1
 *
 *
 */
public final class GlobalActions {

    private final Action addAction;
    private final Action deleteAction;
    private final Action editAction;
    private final Action detailAction;
    private final Action invoiceAction;

    private GlobalActions(Action addAction, Action deleteAction, Action editAction,
                          Action detailAction, Action invoiceAction) {
        this.addAction = Objects.requireNonNull(addAction);
        this.deleteAction = Objects.requireNonNull(deleteAction);
        this.editAction = Objects.requireNonNull(editAction);
        this.detailAction = Objects.requireNonNull(detailAction);
        this.invoiceAction = Objects.requireNonNull(invoiceAction);
    }

    public static EntityActionsBuilder builder() {
        return new EntityActionsBuilder();
    }

    public Action getAddAction() {
        return this.addAction;
    }

    public Action getDeleteAction() {
        return this.deleteAction;
    }

    public Action getEditAction() {
        return this.editAction;
    }

    public Action getDetailAction() {
        return this.detailAction;
    }

    public Action getInvoiceAction() {
        return this.invoiceAction;
    }

    /**
     * Builder utility class for GlobalActions
     *
     */
    public static class EntityActionsBuilder {

        private Action addAction;
        private Action deleteAction;
        private Action editAction;
        private Action detailAction;
        private Action invoiceAction;

        private EntityActionsBuilder() {
        }

        public EntityActionsBuilder addAction(Action addAction) {
            this.addAction = addAction;
            return this;
        }

        public EntityActionsBuilder deleteAction(Action deleteAction) {
            this.deleteAction = deleteAction;
            return this;
        }

        public EntityActionsBuilder editAction(Action editAction) {
            this.editAction = editAction;
            return this;
        }

        public EntityActionsBuilder detailAction(Action detailAction) {
            this.detailAction = detailAction;
            return this;
        }

        public EntityActionsBuilder invoiceAction(Action invoiceAction) {
            this.invoiceAction = invoiceAction;
            return this;
        }

        public GlobalActions build() {
            return new GlobalActions(addAction, deleteAction, editAction, detailAction, invoiceAction);
        }
    }
}
