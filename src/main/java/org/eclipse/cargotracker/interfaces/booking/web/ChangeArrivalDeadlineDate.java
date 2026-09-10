package org.eclipse.cargotracker.interfaces.booking.web;

import org.eclipse.cargotracker.interfaces.booking.facade.BookingServiceFacade;
import org.eclipse.cargotracker.interfaces.booking.facade.dto.CargoRoute;
import org.primefaces.PrimeFaces;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Named
@ViewScoped
public class ChangeArrivalDeadlineDate implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final String DATE_FORMAT = "MM/dd/yyyy";

    private String trackingId;
    private CargoRoute cargo;
    private Date arrivalDeadlineDate;

    @Inject
    private BookingServiceFacade bookingServiceFacade;

    public String getTrackingId() {
        return trackingId;
    }

    public void setTrackingId(String trackingId) {
        this.trackingId = trackingId;
    }

    public CargoRoute getCargo() {
        return cargo;
    }

    public Date getArrivalDeadlineDate() {
        return arrivalDeadlineDate;
    }

    public void setArrivalDeadlineDate(Date arrivalDeadlineDate) {
        this.arrivalDeadlineDate = arrivalDeadlineDate;
    }

    /**
     * Loads the cargo for the configured tracking ID and parses its date-only
     * deadline for editing.
     */
    public void load() {
        cargo = bookingServiceFacade.loadCargoForRouting(trackingId);
        try {
            SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT, Locale.US);
            format.setLenient(false);
            arrivalDeadlineDate = format.parse(cargo.getArrivalDeadlineDate());
        } catch (ParseException e) {
            throw new IllegalStateException("Error parsing arrival deadline date for cargo "
                    + trackingId + ": " + cargo.getArrivalDeadlineDate(), e);
        }
    }

    /**
     * Submits the selected deadline through the booking facade and closes the
     * dialog only after successful delegation.
     */
    public void changeArrivalDeadline() {
        if (arrivalDeadlineDate == null) {
            FacesMessage message = new FacesMessage("Arrival deadline date is required.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            if (handleValidationFailure(message)) {
                return;
            }
            throw new IllegalArgumentException(message.getSummary());
        }

        bookingServiceFacade.changeDeadline(trackingId, arrivalDeadlineDate);
        closeDialog();
    }

    protected boolean handleValidationFailure(FacesMessage message) {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context != null) {
            context.addMessage(null, message);
            return true;
        }
        return false;
    }

    void setBookingServiceFacade(BookingServiceFacade bookingServiceFacade) {
        this.bookingServiceFacade = bookingServiceFacade;
    }

    /**
     * Closes the PrimeFaces dynamic dialog with the successful "DONE" result.
     * Protected to allow the action method to be tested outside a JSF/PrimeFaces
     * container.
     */
    protected void closeDialog() {
        if (isDynamicDialogRequest()) {
            PrimeFaces.current().dialog().closeDynamic("DONE");
        }
    }

    private boolean isDynamicDialogRequest() {
        FacesContext context = FacesContext.getCurrentInstance();
        return context == null
                || context.getExternalContext().getRequestParameterMap()
                .containsKey("pfdlgcid");
    }
}
