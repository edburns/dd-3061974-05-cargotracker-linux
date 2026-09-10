package org.eclipse.cargotracker.interfaces.booking.web;

import org.eclipse.cargotracker.interfaces.booking.facade.BookingServiceFacade;
import org.eclipse.cargotracker.interfaces.booking.facade.dto.CargoRoute;
import org.primefaces.PrimeFaces;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@ViewScoped
public class ChangeArrivalDeadlineDate implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public void load() {
        cargo = bookingServiceFacade.loadCargoForRouting(trackingId);
        try {
            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
            format.setLenient(false);
            arrivalDeadlineDate = format.parse(cargo.getArrivalDeadlineDate());
        } catch (ParseException e) {
            throw new RuntimeException("Error parsing arrival deadline date for cargo "
                    + trackingId + ": " + cargo.getArrivalDeadlineDate(), e);
        }
    }

    public void changeArrivalDeadline() {
        if (arrivalDeadlineDate == null) {
            throw new IllegalArgumentException("Arrival deadline date is required.");
        }

        bookingServiceFacade.changeDeadline(trackingId, arrivalDeadlineDate);
        closeDialog();
    }

    protected void closeDialog() {
        PrimeFaces.current().dialog().closeDynamic("DONE");
    }
}
