/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.cinema.model;

/**
 *
 * @author cristian
 */
public class Movie {
	private String name;
	private String gen;
	private int seats;

	public Movie() {
	}

	public Movie(String name, String gen) {
		this.name = name;
		this.gen = gen;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenre() {
		return gen;
	}

	public void setGenre(String gen) {
		this.gen = gen;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}
}
