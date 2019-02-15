/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.persistence.impl;

import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.model.Movie;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

/**
 *
 * @author cristian
 */
@Component("cps")
public class InMemoryCinemaPersistence implements CinemaPersitence {

	private final Map<String, Cinema> cinemas = new HashMap<>();

	public InMemoryCinemaPersistence() {
		// load stub data
		String functionDate = "2018-12-18 15:30";
		List<CinemaFunction> functions = new ArrayList<>();
		CinemaFunction funct1 = new CinemaFunction(new Movie("SuperHeroes Movie", "Action",30), functionDate);
		CinemaFunction funct2 = new CinemaFunction(new Movie("The Night", "Horror",25), functionDate);
		functions.add(funct1);
		functions.add(funct2);
		Cinema c = new Cinema("cinemaX", functions);
		cinemas.put("cinemaX", c);
	}

	@Override
	public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {
		Cinema cine = cinemas.get(cinema);
		for (CinemaFunction funtion : cine.getFunctions()) {

			if (funtion.getMovie().getName().equals(movieName) && funtion.getDate().equals(date)) {

				funtion.buyTicket(row, col);

			}

		}

	}

	@Override
	public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) {
		Cinema cine = cinemas.get(cinema);
		List<CinemaFunction> fun = new ArrayList<>();
		for (CinemaFunction funtion : cine.getFunctions()) {
			if (funtion.getDate().equals(date)) {
				fun.add(funtion);

			}
		}
		return fun;

	}

	@Override
	public void saveCinema(Cinema c) throws CinemaPersistenceException {
		if (cinemas.containsKey(c.getName())) {
			throw new CinemaPersistenceException("The given cinema already exists: " + c.getName());
		} else {
			cinemas.put(c.getName(), c);
		}
	}

	@Override
	public Cinema getCinema(String name) throws CinemaPersistenceException {
		return cinemas.get(name);
	}

	@Override
	public Set<Cinema> getAllCinemas() throws CinemaPersistenceException {
		// TODO Auto-generated method stub
		Set<Cinema> allCinemas = new HashSet<Cinema>();
		for (Map.Entry<String, Cinema> entry : cinemas.entrySet()) {
			allCinemas.add(entry.getValue());
		}
		return allCinemas;
	}

	@Override
	public List<CinemaFunction> getFunctionsbyGen(String cinema,String gen) {
		Cinema cine = cinemas.get(cinema);
		List<CinemaFunction> fun = new ArrayList<>();
		for (CinemaFunction funtion : cine.getFunctions()) {
			if (funtion.getGen().equals(gen)) {
				fun.add(funtion);

			}
		}
		return fun;
	}


	@Override
	public List<CinemaFunction> getFunctionsbySeats(String cinName, String seats) {
		// TODO Auto-generated method stub
		Cinema cine = cinemas.get(cinName);
		List<CinemaFunction> fun = new ArrayList<>();
		for (CinemaFunction funtion : cine.getFunctions()) {
			System.out.println(funtion.getNumSeats());
			if (funtion.getNumSeats()>=Integer.parseInt(seats)) {
				fun.add(funtion);

			}
		}
		return fun;
	}

}
