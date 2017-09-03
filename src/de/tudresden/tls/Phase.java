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

import java.util.LinkedList;

public class Phase {
	LinkedList<Signalgeber> sg = new LinkedList<Signalgeber>();
	public LinkedList<Signalgeber> getSignalgeber() {
		return sg;
	}
	public void putSignalgeber(Signalgeber s) {
		sg.add(s);
	}
	public int sg_in_phase_vorhanden(Signalgeber s) {
		int v=0;
		if (sg.contains(s)) {
			v=1;
		}
		return v;
	}
}
