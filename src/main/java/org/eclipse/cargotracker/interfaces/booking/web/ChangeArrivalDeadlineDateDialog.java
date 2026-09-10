package org.eclipse.cargotracker.interfaces.booking.web;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ManagedBean(name = "changeArrivalDeadlineDateDialog")
@SessionScoped
public class ChangeArrivalDeadlineDateDialog implements Serializable {

    private static final long serialVersionUID = 1L;

    public void showDialog(String trackingId) {
        Map<String, Object> options = new HashMap<String, Object>();
        options.put("modal", true);
        options.put("draggable", true);
        options.put("resizable", false);
        options.put("contentWidth", 410);
        options.put("contentHeight", 280);

        Map<String, List<String>> params = new HashMap<String, List<String>>();
        List<String> values = new ArrayList<String>();
        values.add(trackingId);
        params.put("trackingId", values);

        PrimeFaces.current().dialog().openDynamic(
                "/admin/dialogs/changeArrivalDeadlineDate.xhtml",
                options,
                params);
    }

    public void handleReturn(SelectEvent event) {  }

    public void cancel() {
        if (isDynamicDialogRequest()) {
            PrimeFaces.current().dialog().closeDynamic("");
        }
    }

    private boolean isDynamicDialogRequest() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context != null
                && context.getExternalContext().getRequestParameterMap()
                .containsKey("pfdlgcid");
    }
}
