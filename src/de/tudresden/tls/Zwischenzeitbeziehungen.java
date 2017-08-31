/* 
#    Copyright 2017 Willi Schmidt
# 
#
#    This program is free software: you can redistribute it and/or modify
#    it under the terms of the GNU General Public License as published by
#    the Free Software Foundation, either version 3 of the License, or
#    (at your option) any later version.
#
#    This program is distributed in the hope that it will be useful,
#    but WITHOUT ANY WARRANTY; without even the implied warranty of
#    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#    GNU General Public License for more details.
#
#    You should have received a copy of the GNU General Public License
#    along with this program.  If not, see <http://www.gnu.org/licenses/>.

#################################################################################################### 
*/
package de.tudresden.tls;


public class Zwischenzeitbeziehungen {
	
	private Signalgeber einfahrend;
	private Signalgeber ausfahrend;
	private int verriegelung;
	private int zwischenzeit=-994;
	
	public Signalgeber getEinfahrend() {
		return einfahrend;
	}
	public void setEinfahrend(Signalgeber einfahrend) {
		this.einfahrend = einfahrend;
	}
	public Signalgeber getAusfahrend() {
		return ausfahrend;
	}
	public void setAusfahrend(Signalgeber ausfahrend) {
		this.ausfahrend = ausfahrend;
	}
	public int getVerriegelung() {
		return verriegelung;
	}
	public void setVerriegelung(int verriegelung) {
		this.verriegelung = verriegelung;
	}
	public int getZwischenzeit() {
		return zwischenzeit;
	}
	public void setZwischenzeit(int zwischenzeit) {
		this.zwischenzeit = zwischenzeit;
	}
	
}
