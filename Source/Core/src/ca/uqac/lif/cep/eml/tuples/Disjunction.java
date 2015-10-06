/*
    BeepBeep, an event stream processor
    Copyright (C) 2008-2015 Sylvain Hallé

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ca.uqac.lif.cep.eml.tuples;

public class Disjunction extends BinaryExpression 
{
	public Disjunction()
	{
		super();
		m_symbol = "OR";
	}

	@Override
	public EmlConstant evaluate(Object t_left, Object t_right) 
	{
		boolean n_left = EmlBoolean.parseBoolValue(t_left);
		boolean n_right = EmlBoolean.parseBoolValue(t_right);
		return EmlBoolean.toEmlBoolean(n_left || n_right);
	}
}
