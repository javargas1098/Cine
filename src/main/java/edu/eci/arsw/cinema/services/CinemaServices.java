/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.services;

import edu.eci.arsw.cinema.filter.CinemaFilter;
import edu.eci.arsw.cinema.model.Cinema;
import edu.eci.arsw.cinema.model.CinemaFunction;
import edu.eci.arsw.cinema.persistence.CinemaException;
import edu.eci.arsw.cinema.persistence.CinemaPersistenceException;
import edu.eci.arsw.cinema.persistence.CinemaPersitence;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author cristian
 */
@Service
public class CinemaServices {
	@Autowired
	CinemaPersitence cps=null;

	@Autowired
	CinemaFilter cfs;

	public void addNewCinema(Cinema c) throws CinemaPersistenceException {
		cps.saveCinema(c);
	}

	public Set<Cinema> getAllCinemas() throws CinemaPersistenceException {
		return cps.getAllCinemas();
	}

	/**
	 * 
	 * @param name cinema's name
	 * @return the cinema of the given name created by the given author
	 * @throws CinemaException
	 */
	public Cinema getCinemaByName(String name) throws CinemaException, CinemaPersistenceException {
		return cps.getCinema(name);

	}

	public void buyTicket(int row, int col, String cinema, String date, String movieName) throws CinemaException {
		cps.buyTicket(row, col, cinema, date, movieName);

	}

	public List<CinemaFunction> getFuntionbyGen(String cinema, String gen, String date)  throws CinemaException, CinemaPersistenceException {
		return cfs.getFunctionsbyGen(cinema, gen, date, cps);

	}

	public List<CinemaFunction> getFunctionsbyCinemaAndDate(String cinema, String date) {
		return cps.getFunctionsbyCinemaAndDate(cinema, date);

	}

	public List<CinemaFunction> getFuntionbyNumberofSeats(String cinName, int sillas, String date)  throws CinemaException, CinemaPersistenceException {
		// TODO Auto-generated method stub
		return cfs.getFunctionsbySeats(cinName, sillas, date, cps);
	}

}
