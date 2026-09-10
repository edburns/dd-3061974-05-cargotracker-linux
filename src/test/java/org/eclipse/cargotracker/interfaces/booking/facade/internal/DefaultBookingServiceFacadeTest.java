package org.eclipse.cargotracker.interfaces.booking.facade.internal;

import org.eclipse.cargotracker.application.BookingService;
import org.eclipse.cargotracker.domain.model.cargo.Itinerary;
import org.eclipse.cargotracker.domain.model.cargo.TrackingId;
import org.eclipse.cargotracker.domain.model.location.UnLocode;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Container free test of the deadline change delegation done by the booking
 * service facade.
 */
public class DefaultBookingServiceFacadeTest {

    /**
     * Hand written test double recording the deadline change calls it receives.
     */
    private static class BookingServiceSpy implements BookingService {

        private final List<TrackingId> trackingIds = new ArrayList<>();
        private final List<Date> deadlines = new ArrayList<>();

        @Override
        public TrackingId bookNewCargo(UnLocode origin, UnLocode destination,
                                       Date arrivalDeadline) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Itinerary> requestPossibleRoutesForCargo(
                TrackingId trackingId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void assignCargoToRoute(Itinerary itinerary,
                                       TrackingId trackingId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void changeDestination(TrackingId trackingId, UnLocode unLocode) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void changeDeadline(TrackingId trackingId, Date deadline) {
            trackingIds.add(trackingId);
            deadlines.add(deadline);
        }
    }

    @Test
    public void testChangeDeadlineDelegatesOnceWithConvertedTrackingId()
            throws Exception {
        BookingServiceSpy bookingService = new BookingServiceSpy();
        DefaultBookingServiceFacade facade = new DefaultBookingServiceFacade();

        Field field = DefaultBookingServiceFacade.class
                .getDeclaredField("bookingService");
        field.setAccessible(true);
        field.set(facade, bookingService);

        Date arrivalDeadline = new Date();

        // The repositories are deliberately left unset, so any repository use
        // in the facade would fail this test.
        facade.changeDeadline("ABC123", arrivalDeadline);

        assertEquals(1, bookingService.trackingIds.size());
        assertEquals(new TrackingId("ABC123"),
                bookingService.trackingIds.get(0));
        assertEquals(1, bookingService.deadlines.size());
        assertSame(arrivalDeadline, bookingService.deadlines.get(0));
    }
}
