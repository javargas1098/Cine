package edu.eci.arsw.cinema.filter.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import edu.eci.arsw.cinema.filter.CinemaFilter;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;


@Component("cfs")
public class InMemoryCinemaFilter implements CinemaFilter {
	
	public InMemoryCinemaFilter() {
	}

	@Override
	public List<CinemaFunction> getFunctionsbyGen(String cinema, String gen, String date,CinemaPersitence pelicula) throws CinemaPersistenceException {
		List<CinemaFunction> cine = pelicula.getCinema(cinema).getFunctions();
		List<CinemaFunction> fun = new ArrayList<>();
		for (CinemaFunction cin : cine) {
			if (cin.getGen().equals(gen) && cin.getDate().equals(date)) {
				fun.add(cin);

			}
		}
		return fun;
	}

	@Override
	public List<CinemaFunction> getFunctionsbySeats(String cinName, int seats, String date,CinemaPersitence pelicula) throws CinemaPersistenceException {
		// TODO Auto-generated method stub
		List<CinemaFunction> cine = pelicula.getCinema(cinName).getFunctions();
		List<CinemaFunction> fun = new ArrayList<>();
		for (CinemaFunction funtion : cine) {
//			System.out.println(funtion.getNumSeats());
//			System.out.println(seats);
			if (funtion.getNumSeats() >= seats && funtion.getDate().equals(date)) {
				fun.add(funtion);

			}
		}
		return fun;
	}

}