package com.flappybird.model;

import java.util.Date;
import java.util.List;
/**
 * Classe modelisant le mode Debug
 * Contient les infos du mode debug
 * @author AlexFlex
 *
 */
public class Debug {
	
	private boolean activ;
	private String intitule;
	private String hightScore;
	private List<String> dernierScore;
	private Date date;
	

	/**
	 * Lors de la creation du debug on initialise les variables
	 * @param hightScore
	 * @param date
	 */
	public Debug() {
		this.intitule = "DEBUG (F1)";
		hightScore = "";
		this.activ = false;
		
	}
		
	public String getHightScore() {
		return hightScore;
	}
	public void setHightScore(String hightScore) {
		this.hightScore = hightScore;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isActiv() {
		return activ;
	}

	public void setActiv(boolean activ) {
		this.activ = activ;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	
	
	/**
	 * Retourne le libelle de debug en fonction du boolean actif
	 * @return
	 */
	public String getIntituleCalcule() {
		if (isActiv()) {
			return "DEBUG ACTIVE";
		}else {		
			return "DEBUG (F1)";
		}
	}
	
	
	
	
	
	
}
