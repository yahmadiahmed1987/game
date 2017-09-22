package com.addstones.sg.katas.tennis.methode1.tdd;


public class Player {

	private String name;
	private boolean advanced;
	private int pointScore;
	private int setScore;

	public Player(String name) {
		this.name = name;
	}

	public void winBall() {

		if (this.pointScore < 30) {
			this.pointScore = this.pointScore + 15;
		} else if (this.pointScore == 30) {
			this.pointScore = this.pointScore + 10;
		} else {
			if (this.advanced) {
				winSet();
			} else {
				this.advanced = true;
			}
		}

	}

	public void winSet() {
		this.pointScore = 0;
		this.advanced = false;
		this.setScore = this.setScore + 1;

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPointScore() {
		return pointScore;
	}

	public void setPointScore(int pointScore) {
		this.pointScore = pointScore;
	}

	public int getSetScore() {
		return setScore;
	}

	public void setSetScore(int setScore) {
		this.setScore = setScore;
	}

	public boolean isAdvanced() {
		return advanced;
	}

	public void setAdvanced(boolean advanced) {
		this.advanced = advanced;
	}
	
	

}
