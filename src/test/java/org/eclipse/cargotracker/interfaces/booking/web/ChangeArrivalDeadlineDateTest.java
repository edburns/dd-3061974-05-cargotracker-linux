package org.eclipse.cargotracker.interfaces.booking.web;

import org.eclipse.cargotracker.interfaces.booking.facade.BookingServiceFacade;
import org.eclipse.cargotracker.interfaces.booking.facade.dto.CargoRoute;
import org.eclipse.cargotracker.interfaces.booking.facade.dto.Location;
import org.eclipse.cargotracker.interfaces.booking.facade.dto.RouteCandidate;
import org.junit.Test;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ChangeArrivalDeadlineDateTest {

    private static class BookingServiceFacadeFake implements BookingServiceFacade {

        private CargoRoute cargoRoute;
        private String loadedTrackingId;
        private String changedTrackingId;
        private Date changedDeadline;
        private boolean failChangeDeadline;

        @Override
        public String bookNewCargo(String origin, String destination,
                                   Date arrivalDeadline) {
            throw new UnsupportedOperationException();
        }

        @Override
        public CargoRoute loadCargoForRouting(String trackingId) {
            loadedTrackingId = trackingId;
            return cargoRoute;
        }

        @Override
        public void assignCargoToRoute(String trackingId, RouteCandidate route) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void changeDestination(String trackingId,
                                      String destinationUnLocode) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void changeDeadline(String trackingId, Date arrivalDeadline) {
            changedTrackingId = trackingId;
            changedDeadline = arrivalDeadline;
            if (failChangeDeadline) {
                throw new IllegalStateException("Facade failure");
            }
        }

        @Override
        public List<RouteCandidate> requestPossibleRoutesForCargo(
                String trackingId) {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<Location> listShippingLocations() {
            throw new UnsupportedOperationException();
        }

        @Override
        public List<CargoRoute> listAllCargos() {
            throw new UnsupportedOperationException();
        }
    }

    private static class TestChangeArrivalDeadlineDate
            extends ChangeArrivalDeadlineDate {

        private boolean closed;

        @Override
        protected void closeDialog() {
            closed = true;
        }
    }

    @Test
    public void testLoadUsesTrackingIdAndParsesDateOnlyDeadline()
            throws Exception {
        BookingServiceFacadeFake facade = new BookingServiceFacadeFake();
        facade.cargoRoute = cargoRoute(date("08/09/2026"));
        TestChangeArrivalDeadlineDate bean = beanWithFacade(facade);
        bean.setTrackingId("DEF789");

        bean.load();

        assertEquals("DEF789", facade.loadedTrackingId);
        assertSame(facade.cargoRoute, bean.getCargo());
        assertEquals(date("08/09/2026"), bean.getArrivalDeadlineDate());
    }

    @Test
    public void testChangeArrivalDeadlineDelegatesAndClosesAfterSuccess()
            throws Exception {
        BookingServiceFacadeFake facade = new BookingServiceFacadeFake();
        TestChangeArrivalDeadlineDate bean = beanWithFacade(facade);
        Date selectedDate = date("09/10/2026");
        bean.setTrackingId("ABC123");
        bean.setArrivalDeadlineDate(selectedDate);

        bean.changeArrivalDeadline();

        assertEquals("ABC123", facade.changedTrackingId);
        assertSame(selectedDate, facade.changedDeadline);
        assertTrue(bean.closed);
    }

    @Test
    public void testMalformedDtoDeadlineFailsExplicitly() throws Exception {
        BookingServiceFacadeFake facade = new BookingServiceFacadeFake();
        facade.cargoRoute = cargoRouteWithDateText("not-a-date");
        TestChangeArrivalDeadlineDate bean = beanWithFacade(facade);
        bean.setTrackingId("BAD123");

        try {
            bean.load();
            fail("Expected malformed DTO date to fail");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("Error parsing arrival deadline date"));
            assertTrue(e.getMessage().contains("BAD123"));
        }
    }

    @Test
    public void testNullSelectedDateIsRejected() throws Exception {
        BookingServiceFacadeFake facade = new BookingServiceFacadeFake();
        TestChangeArrivalDeadlineDate bean = beanWithFacade(facade);
        bean.setTrackingId("ABC123");

        try {
            bean.changeArrivalDeadline();
            fail("Expected null selected date to fail");
        } catch (IllegalArgumentException e) {
            assertEquals("Arrival deadline date is required.", e.getMessage());
        }

        assertNull(facade.changedTrackingId);
        assertFalse(bean.closed);
    }

    @Test
    public void testFacadeFailureDoesNotCloseDialog() throws Exception {
        BookingServiceFacadeFake facade = new BookingServiceFacadeFake();
        facade.failChangeDeadline = true;
        TestChangeArrivalDeadlineDate bean = beanWithFacade(facade);
        Date selectedDate = date("10/11/2026");
        bean.setTrackingId("ABC123");
        bean.setArrivalDeadlineDate(selectedDate);

        try {
            bean.changeArrivalDeadline();
            fail("Expected facade failure to surface");
        } catch (IllegalStateException e) {
            assertEquals("Facade failure", e.getMessage());
        }

        assertEquals("ABC123", facade.changedTrackingId);
        assertSame(selectedDate, facade.changedDeadline);
        assertFalse(bean.closed);
    }

    private static TestChangeArrivalDeadlineDate beanWithFacade(
            BookingServiceFacade facade) throws Exception {
        TestChangeArrivalDeadlineDate bean = new TestChangeArrivalDeadlineDate();
        Field field = ChangeArrivalDeadlineDate.class
                .getDeclaredField("bookingServiceFacade");
        field.setAccessible(true);
        field.set(bean, facade);
        return bean;
    }

    private static CargoRoute cargoRoute(Date arrivalDeadline) {
        return new CargoRoute("DEF789", "USCHI", "FIHEL", arrivalDeadline,
                false, false, "UNKNOWN", "NOT_RECEIVED");
    }

    private static CargoRoute cargoRouteWithDateText(final String dateText) {
        return new CargoRoute("DEF789", "USCHI", "FIHEL", new Date(),
                false, false, "UNKNOWN", "NOT_RECEIVED") {
            @Override
            public String getArrivalDeadlineDate() {
                return dateText;
            }
        };
    }

    private static Date date(String date) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        format.setLenient(false);
        return format.parse(date);
    }
}
