import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author cristian
 * pruebas hechas por javier vargas y sebastian goenaga
 */
public class InMemoryPersistenceTest {

	@Test
	public void saveNewAndLoadTest() throws CinemaPersistenceException {
		InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

		String functionDate = "2018-12-18 15:30";
		List<CinemaFunction> functions = new ArrayList<>();
		CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
		CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
		functions.add(funct1);
		functions.add(funct2);
		Cinema c = new Cinema("Movies Bogotá", functions);
		ipct.saveCinema(c);

		assertNotNull("Loading a previously stored cinema returned null.", ipct.getCinema(c.getName()));
		assertEquals("Loading a previously stored cinema returned a different cinema.",
				ipct.getCinema(c.getName()), c);
	}

	@Test
	public void saveExistingCinemaTest() {
		InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

		String functionDate = "2018-12-18 15:30";
		List<CinemaFunction> functions = new ArrayList<>();
		CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
		CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
		functions.add(funct1);
		functions.add(funct2);
		Cinema c = new Cinema("Movies Bogotá", functions);

		try {
			ipct.saveCinema(c);
		} catch (CinemaPersistenceException ex) {
			fail("Cinema persistence failed inserting the first cinema.");
		}

		List<CinemaFunction> functions2 = new ArrayList<>();
		CinemaFunction funct12 = new CinemaFunction(new Movie("SuperHeroes Movie 3", "Action"), functionDate);
		CinemaFunction funct22 = new CinemaFunction(new Movie("The Night 3", "Horror"), functionDate);
		functions.add(funct12);
		functions.add(funct22);
		Cinema c2 = new Cinema("Movies Bogotá", functions2);
		try {
			ipct.saveCinema(c2);
			fail("An exception was expected after saving a second cinema with the same name");
		} catch (CinemaPersistenceException ex) {

		}

	}

	@Test
	public void getCinemaByNameTest() {

		InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

		String functionDate = "2018-12-18 15:30";
		List<CinemaFunction> functions = new ArrayList<>();
		CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
		CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
		functions.add(funct1);
		functions.add(funct2);
		String name = "Movies Bogotá";
		Cinema c = new Cinema(name, functions);

		try {
			ipct.saveCinema(c);
		} catch (CinemaPersistenceException e1) {
			fail();
		}

		try {
			assertEquals(ipct.getCinema(name), c);
		} catch (CinemaPersistenceException e) {
			fail();
		}

	}

	@Test
	public void buyTicketTest() {

		InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();

		String functionDate = "2018-12-18 15:30";
		List<CinemaFunction> functions = new ArrayList<>();
		String movieName = "SuperHeroes Movie 2";
		CinemaFunction funct1 = new CinemaFunction(new Movie(movieName, "Action"), functionDate);
		functions.add(funct1);
		String name = "Movies Bogotá";
		Cinema c = new Cinema(name, functions);

		try {
			ipct.saveCinema(c);
		} catch (CinemaPersistenceException e1) {
			fail("The given cinema already exists");
		}
		boolean expected = false;
		for (CinemaFunction function : c.getFunctions()) {
			if (function.getMovie().getName().equals(movieName) && function.getDate().equals(functionDate)) {
				// We wating for the seat is free or not
				expected = function.getSeats().get(0).get(0);
				break;
			}
		}

		try {
			boolean response = ipct.buyTicket(0, 0, name, functionDate, movieName);
			assertEquals(response, expected);
			
		} catch (CinemaException e1) {
			
		}
		try {
			ipct.buyTicket(0, 0, name, functionDate, movieName);
			fail("An exeption was expected after buying the same seat");
		} catch (CinemaException e) {
		}

	}
	
	@Test
	public void getFunctionsbyCinemaAndDateTest() {
		
		InMemoryCinemaPersistence ipct = new InMemoryCinemaPersistence();
		
		String functionDate = "2018-12-18 15:30";
		List<CinemaFunction> functions = new ArrayList<>();
		CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie 2", "Action"), functionDate);
		CinemaFunction funct2 = new CinemaFunction(new Movie("The Night 2", "Horror"), functionDate);
		functions.add(funct1);
		functions.add(funct2);
		String name = "Movies Bogotá";
		Cinema cine = new Cinema(name, functions);
		
		try {
			ipct.saveCinema(cine);
		} catch (CinemaPersistenceException e) {
			fail("The given cinema already exists");
		}
		
		List<CinemaFunction> fun = new ArrayList<>();
		
		for (CinemaFunction function : cine.getFunctions()) {
			if (function.getDate().equals(functionDate)) {
				fun.add(function);
			}
		}
		
		Set<CinemaFunction> set = new HashSet<>(fun);
		
		assertEquals(set, new HashSet<>(ipct.getFunctionsbyCinemaAndDate(name, functionDate)));	
		
	}

}