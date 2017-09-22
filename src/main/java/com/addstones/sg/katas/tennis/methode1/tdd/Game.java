package com.addstones.sg.katas.tennis.methode1.tdd;

public class Game {

	private Player player1;
	private Player player2;
	private boolean deuce;
	private boolean tieBreak;

	public Game(Player player1, Player player2) {
		this.player1 = player1;
		this.player2 = player2;
	}

	public String getGameScore() {
		if (this.player1.getSetScore() >= 5 && this.player2.getSetScore() >= 5) {
			if (Math.abs(this.player2.getSetScore()
					- this.player1.getSetScore()) >= 2) {
				return getLeadPlayer().getName() + " won";
			} else if (this.player1.getSetScore() == this.player2.getSetScore()) {
				return "Equability";
			} else {
				return  getLeadPlayer().getName()+ " on advanced";
			}
		} else {
			return this.player1.getSetScore() + ", "
					+ this.player2.getSetScore();
		}
	}

	public Boolean deuceIsActivated() {

		if (this.player2.getPointScore() >= 40
				&& this.player1.getPointScore() >= 40) {
			this.deuce = true;
			return true;
		} else {
			return false;
		}
	}
	
	public Boolean tieBreakIsActivated() {

		if (this.player2.getSetScore() >= 6
				&& this.player1.getSetScore() >= 6) {
			this.tieBreak = true;
			return true;
		} else {
			return false;
		}
	}

	public void isAdvancedPlayerManger(Player player1, Player player2) {
		if (deuce) {
			if (player2.isAdvanced()) {
				player2.setAdvanced(false);
			} else {
				player1.setAdvanced(true);
			}
		}
	}

	public Player getLeadPlayer() {
		return (player1.getSetScore() > player2.getSetScore()) ? player1
				: player2;
	}

	public Player getPlayer1() {
		return player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

	public Player getPlayer2() {
		return player2;
	}

	public void setPlayer2(Player player2) {
		this.player2 = player2;
	}

	public boolean isDeuce() {
		return deuce;
	}

	public void setDeuce(boolean deuce) {
		this.deuce = deuce;
	}

	
	public boolean isTieBreak() {
		return tieBreak;
	}

	public void setTieBreak(boolean tieBreak) {
		this.tieBreak = tieBreak;
	}

	public void winnerLooserManage(Player winner, Player looser) {

		if (winner.isAdvanced()) {
			winner.winSet();
		} else {
			if (looser.isAdvanced()) {
				looser.setAdvanced(false);
			} else {
				winner.winBall();
			}

		}
	}

}
