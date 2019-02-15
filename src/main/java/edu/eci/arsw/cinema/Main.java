
package edu.eci.arsw.cinema;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.services.CinemaServices;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author
 */
public class Main {

	public static void main(String a[]) throws CinemaPersistenceException, CinemaException {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		CinemaServices cs = ac.getBean(CinemaServices.class);

		List<CinemaFunction> functions = new ArrayList<>();
		Movie man = new Movie("Superman", "Action", 30);
		Movie marvel = new Movie("Capitan marvel", "Action", 20);
		Movie dragon = new Movie("Dragon 3", "Ninos", 25);
		functions.add(new CinemaFunction(man, "2019-02-14 13:00"));
		functions.add(new CinemaFunction(marvel, "2019-02-14 14:08"));
		functions.add(new CinemaFunction(marvel, "2019-02-13 16:00"));
		functions.add(new CinemaFunction(man, "2019-02-14 20:00"));
		functions.add(new CinemaFunction(man, "2019-02-14 22:15"));
		functions.add(new CinemaFunction(man, "2019-02-14 21:05"));
		functions.add(new CinemaFunction(dragon, "2019-02-14 15:20"));
		functions.add(new CinemaFunction(dragon, "2019-02-14 19:30"));

		String cinName = new String("Cinepolis");
		String movieGen = new String("Action");
		String sillas = new String("25");
		Cinema cinepolis = new Cinema(cinName, functions);

		// Registrar
		cs.addNewCinema(cinepolis);
		System.out.println("Cinepolis registrado");

		// consultar
		System.out.println("Cine encontrado: " + cs.getCinemaByName(cinName).getName());

		// encontrar

		for (CinemaFunction foundFunction : cs.getFunctionsbyCinemaAndDate(cinName, "2019-02-13 16:00")) {
			System.out.println("Cinepolis fontanar, February 14, 2019 at 16:00");
			System.out.println(foundFunction.getMovie().getName());
		}

		for (CinemaFunction foundFunction : cs.getFunctionsbyCinemaAndDate(cinName, "2019-02-14 15:20")) {
			System.out.println("Cinepolis fontanar, February 14, 2019 at 15:20");
			System.out.println(foundFunction.getMovie().getName());
		}
		for (CinemaFunction foundFunction : cs.getFunctionsbyCinemaAndDate(cinName, "2019-02-14 13:00")) {
			System.out.println("Cinepolis fontanar, February 14, 2019 at 13:00");
			System.out.println(foundFunction.getMovie().getName());
		}

		// reservas
		cs.buyTicket(2, 2, cinName, "2019-02-14 15:20", "Dragon 3");
		cs.buyTicket(4, 4, cinName, "2019-02-13 16:00", "Capitan marvel");
		cs.buyTicket(3, 3, cinName, "2019-02-13 16:00", "Superman");

		// por genero
		System.out.println("Cinepolis fontanar,  las peliculas con el genero Action son:");
		for (CinemaFunction foundFunction : cs.getFuntionbyGen(cinName, movieGen)) {

			System.out
					.println(foundFunction.getMovie().getName() + " con su fecha y hora es " + foundFunction.getDate());
		}
		// por sillas
		System.out.println("Cinepolis fontanar,  las peliculas con sillas mayor a 20");
		for (CinemaFunction foundFunction : cs.getFuntionbyNumberofSeats(cinName, sillas)) {

			System.out
					.println(foundFunction.getMovie().getName() + " con  " + foundFunction.getDate());
		}
	}

}