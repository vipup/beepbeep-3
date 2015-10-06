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

import java.util.Stack;

import ca.uqac.lif.cep.Processor;

public class EmlNumber extends EmlConstant
{
	protected Number m_number;
	
	public EmlNumber()
	{
		super();
	}
	
	public EmlNumber(Number n)
	{
		this();
		m_number = n;
	}
	
	public EmlNumber(EmlNumber n)
	{
		this();
		m_number = n.numberValue();
	}

	public Number numberValue()
	{
		return m_number.doubleValue();
	}
	
	public int intValue()
	{
		return m_number.intValue();
	}

	@Override
	public void build(Stack<Object> stack)
	{
		Object o = stack.pop();
		if (o instanceof Processor)
		{
			stack.push(o);
		}
		else
		{
			stack.push(EmlNumber.toEmlNumber(o));
		}
	}
	
	@Override
	public String toString()
	{
		if (m_number.floatValue() % 1 == 0)
		{
			// Display as integer
			return Integer.toString(m_number.intValue());
		}
		return m_number.toString();
	}
	
	@Override
	public int hashCode()
	{
		return m_number.hashCode();
	}
	
	@Override
	public boolean equals(Object o)
	{
		if (o == null || !(o instanceof EmlNumber))
		{
			return false;
		}
		return equals((EmlNumber) o);
	}
	
	protected boolean equals(EmlNumber n)
	{
		return m_number.doubleValue() == n.m_number.doubleValue();
	}
	
	/**
	 * Attempts to create an EmlNumber from the object passed as an argument
	 * @param o The object
	 * @return An EmlNumber, or null if no number could be build from
	 *   the argument
	 */
	public static EmlNumber toEmlNumber(Object o)
	{
		if (o instanceof EmlNumber)
		{
			return new EmlNumber((EmlNumber) o);
		}
		if (o instanceof Number)
		{
			return new EmlNumber((Number) o);
		}
		if (o instanceof String)
		{
			return new EmlNumber(Double.parseDouble((String) o));
		}
		if (o instanceof NamedTuple)
		{
			NamedTuple t = (NamedTuple) o;
			if (t.size() == 1)
			{
				// If we have a tuple with a single element, try to make a
				// number with that element
				for (String s : t.keySet())
				{
					EmlConstant c = t.get(s);
					if (c != null)
					{
						return EmlNumber.toEmlNumber(c);
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * Attempts to create a float from the object passed as an argument
	 * @param o The object
	 * @return The float, or 0 if no float could be produced from the argument
	 */
	public static float parseFloat(Object o)
	{
		if (o instanceof EmlNumber)
		{
			return ((EmlNumber) o).m_number.floatValue();
		}
		if (o instanceof Number)
		{
			return ((Number) o).floatValue();
		}
		if (o instanceof String)
		{
			return Float.parseFloat((String) o);
		}
		if (o instanceof NamedTuple)
		{
			NamedTuple t = (NamedTuple) o;
			if (t.size() == 1)
			{
				// If we have a tuple with a single element, try to make a
				// number with that element
				for (String s : t.keySet())
				{
					EmlConstant c = t.get(s);
					if (c != null)
					{
						return EmlNumber.parseFloat(c);
					}
				}
			}
		}
		return 0;
	}
}
