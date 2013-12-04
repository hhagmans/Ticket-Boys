package de.fh_dortmund.ticket_system.base;

import org.junit.Test;

import de.fh_dortmund.ticket_system.entity.Week;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class WeekTest {
	@Test
	public void testEquals() {
		Week kw48 = new Week(2012, 48);
		Week kw48_2 = new Week(2012, 48);
		Week kw52 = new Week(2012, 52);

		assertTrue(kw48.equals(kw48_2));
		assertTrue(kw48_2.equals(kw48));
		assertFalse(kw48.equals(kw52));
		assertFalse(kw48_2.equals(kw52));
	}

	@Test
	public void testHashCode() {
		Week kw48 = new Week(2012, 48);
		Week kw48_2 = new Week(2012, 48);
		Week kw52 = new Week(2012, 52);

		assertEquals(kw48.hashCode(), kw48_2.hashCode());
		assertNotEquals(kw48.hashCode(), kw52.hashCode());
		assertNotEquals(kw48_2.hashCode(), kw52.hashCode());
	}
}
