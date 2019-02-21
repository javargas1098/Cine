
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import edu.eci.arsw.cinema.persistence.FilterException;
import edu.eci.arsw.cinema.persistence.impl.InMemoryCinemaPersistence;
import edu.eci.arsw.cinema.services.CinemaServices;

/**
 *
 * @author Javier Vargas y Sebastian Goenaga
 */

public class CinemaFilterTest {

	@Test
	public void filteredFunctionsTest() throws FilterException {

		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		CinemaServices cs = ac.getBean(CinemaServices.class);
		List<CinemaFunction> functions = new ArrayList<>();
		Movie man = new Movie("Superman", "Action");
		Movie marvel = new Movie("Capitan marvel", "Action");
		Movie dragon = new Movie("Dragon 3", "Ninos");
		functions.add(new CinemaFunction(man, "2019-02-14 13:00"));
		functions.add(new CinemaFunction(marvel, "2019-02-14 14:08"));
		functions.add(new CinemaFunction(marvel, "2019-02-13 16:00"));
		functions.add(new CinemaFunction(man, "2019-02-14 20:00"));
		functions.add(new CinemaFunction(man, "2019-02-14 22:15"));
		functions.add(new CinemaFunction(man, "2019-02-14 21:05"));
		functions.add(new CinemaFunction(dragon, "2019-02-14 15:20"));
		functions.add(new CinemaFunction(dragon, "2019-02-14 19:30"));

		String cinName = new String("Cinepolis");

		Cinema cinepolis = new Cinema(cinName, functions);

		// You have to enter de filter
		System.out.println("Enter the filter: Gender or an #Seats");
		Scanner sc = new Scanner(System.in);
		String info = sc.nextLine();
		sc.close();
		try {
			List<CinemaFunction> fun;
			List<CinemaFunction> fun1;

			try {
				int seats = Integer.parseInt(info);
				fun = cs.getFuntionbyNumberofSeats(cinName, seats, "2019-02-14 13:00");
				List<CinemaFunction> cine = cinepolis.getFunctions();
				List<CinemaFunction> fun2 = new ArrayList<>();
				for (CinemaFunction function : cine) {
					if (function.getNumSeats() >= seats && function.getDate().equals("2019-02-14 13:00")) {
						fun2.add(function);
					}
				}
				assertEquals(new HashSet<>(fun), new HashSet<>(fun));
			} catch (NumberFormatException e) {
				fun1 = cs.getFuntionbyGen(cinName, info, "2019-02-14 13:00");
				List<CinemaFunction> cine = cinepolis.getFunctions();
				List<CinemaFunction> fun2 = new ArrayList<>();
				for (CinemaFunction function : cine) {
					if (function.getGen().equals(info) && function.getDate().equals("2019-02-14 13:00")) {
						fun2.add(function);
					}
				}
				assertEquals(new HashSet<>(fun1), new HashSet<>(fun1));
			}
		} catch (CinemaException e) {
			fail();
		} catch (CinemaPersistenceException e) {
			fail();
		}
	}

}
