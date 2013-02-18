package com.tarlic.TeamGen;

public class Player {
	public String firstTeam;
	public String secondTeam;
	public String region;

	public Player() {
		// TODO Auto-generated constructor stub
	}

	public Player(String firstTeam, String secondTeam, String region) {
		this.firstTeam = firstTeam;
		this.secondTeam = secondTeam;
		this.region = region;		
	}

	@Override
	public String toString() {
		return this.firstTeam + this.secondTeam;
	}
}
