/**
 * The Rhymestore project.
 *
 * This application is free software; you can redistribute it and/or
 * modify it under the terms of the GNU LESSER GENERAL PUBLIC
 * LICENSE as published by the Free Software Foundation under
 * version 3 of the License
 *
 * This application is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * LESSER GENERAL PUBLIC LICENSE v.3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place - Suite 330,
 * Boston, MA 02111-1307, USA.
 */

package com.rhymestore.lang;

/**
 * Parses words to identify the part that is used to conform a consonant rhyme.
 * 
 * @author Ignasi Barrera
 */
public class SpanishWordParser implements WordParser
{

	private int letra(final char c)
	{
		int i = -1;
		int ascii;
		ascii = c;
		if (ascii != -1)
		{
			switch (ascii) {
			case 97: // a
				i = 1;
				break;
			case 101: // e
				i = 2;
				break;
			case 104: // h
				i = 6;
				break;
			case 105: // i
				i = 4;
				break;
			case 111: // o
				i = 3;
				break;
			case 117: // u
				i = 5;
				break;
			case 225: // a con acento
				i = 1;
				break;
			case 233: // e con acento
				i = 2;
				break;
			case 237: // i con acento
				i = 4;
				break;
			case 243: // o con acento
				i = 3;
				break;
			case 250: // u con acento
				i = 5;
				break;
			case 252: // u con dieresis
				i = 5;
				break;
			default:
				i = 19;
				break;
			}
		}
		return i;
	}

	private String silaba(final String str)
	{
		String temp = "";
		String s = "";
		char x, y, z;
		if (str.length() < 3)
		{
			if (str.length() == 2)
			{
				x = str.charAt(0);
				y = str.charAt(1);
				if (letra(x) < 6 && letra(y) < 6)
				{
					if (hiato(x, y))
					{
						s = str.substring(0, 1);
					}
					else
					{
						s = str;
					}
				}
				else
				{
					s = str;
				}
			}
			else
			{
				s = str;
			}
		}
		else
		{
			x = str.charAt(0);
			y = str.charAt(1);
			z = str.charAt(2);
			if (letra(x) < 6)
			{ // V ? ?
				if (letra(y) < 6)
				{ // V V ?
					if (letra(z) < 6)
					{ // V V V
						if (hiato(x, y))
						{
							s = str.substring(0, 1);
						}
						else
						{
							if (hiato(y, z))
							{
								s = str.substring(0, 2);
							}
							else
							{
								s = str.substring(0, 3);
							}
						}
					}
					else
					{ // V V C
						if (hiato(x, y))
						{
							s = str.substring(0, 1);
						}
						else
						{
							s = str.substring(0, 2);
						}
					}
				}
				else
				{ // V C ?
					if (letra(z) < 6)
					{ // V C V
						if (letra(y) == 6)
						{ // V H C
							if (hiato(x, z))
							{
								s = str.substring(0, 1);
							}
							else
							{
								s = str.substring(0, 3);
							}
						}
						else
						{
							s = str.substring(0, 1);
						}
					}
					else
					{ // V C C
						if (consonantes1(y, z))
						{
							s = str.substring(0, 1);
						}
						else
						{
							s = str.substring(0, 2);
						}
					}
				}
			}
			else
			{ // C ??
				if (letra(y) < 6)
				{ // C V ?
					if (letra(z) < 6)
					{ // C V V
						temp = str.substring(0, 3);
						if (temp.equals("que") || temp.equals("qui")
								|| temp.equals("gue") || temp.equals("gui"))
						{
							s = str.substring(0, 3);
						}
						else
						{
							if (hiato(y, z))
							{
								s = str.substring(0, 2);
							}
							else
							{
								s = str.substring(0, 3);
							}
						}
					}
					else
					{ // C V C
						s = str.substring(0, 2);
					}
				}
				else
				{ // C C ?
					if (letra(z) < 6)
					{ // C C V
						if (consonantes1(x, y))
						{
							s = str.substring(0, 3);
						}
						else
						{
							s = str.substring(0, 1);
						}
					}
					else
					{ // C C C
						if (consonantes1(y, z))
						{
							s = str.substring(0, 1);
						}
						else
						{
							s = str.substring(0, 1);
						}
					}
				}
			}
		}
		return s;
	}

	private String silabaRest(final String str)
	{
		String s2;
		s2 = silaba(str);
		return str.substring(s2.length());
	}

	private boolean hiato(final char v, final char v2)
	{ // Estable si hay separacion
		boolean cer = false;
		if (letra(v) < 4)
		{ // VA + ?
			if (letra(v2) < 4)
			{
				cer = true;
			}
			else
			{ // VA+ VC
				if (v2 == 237 || v2 == 250) // i o u con acento
				{
					cer = true;
				}
				else
				{
					cer = false;
				}
			}
		}
		else
		{ // VC + ?
			if (letra(v2) < 4)
			{ // VC + VA
				if (v == 237 || v == 250) // i o u con acento
				{
					cer = true;
				}
				else
				{
					cer = false;
				}
			}
			else
			{// VC + VC
				if (v == v2)
				{
					cer = true;
				}
				else
				{
					cer = false;
				}
			}
		}
		return cer;
	}

	private boolean consonantes1(final char a, final char b)
	{
		boolean cer;
		cer = false;
		if (a == 'b' || a == 'c' || a == 'd' || a == 'f' || a == 'g'
				|| a == 'p' || a == 'r' || a == 't')
		{
			if (b == 'r')
			{
				cer = true;
			}
		}
		if (a == 'b' || a == 'c' || a == 'f' || a == 'g' || a == 'p'
				|| a == 't' || a == 'l' || a == 'k')
		{
			if (b == 'l')
			{
				cer = true;
			}
		}
		if (b == 'h')
		{
			if (a == 'c')
			{
				cer = true;
			}
		}
		return cer;
	}

	private boolean strConsonantes(final String str)
	{
		boolean cer = false;
		int i;
		byte noConsonante = 0;
		char c[] = str.toCharArray();
		for (i = 0; i < str.length() && noConsonante == 0; i++)
		{
			if (letra(c[i]) < 6)
			{
				noConsonante++;
			}
		}
		if (noConsonante == 0)
		{
			cer = true;
		}
		return cer;
	}

	private boolean strVVstr(final String s1, final String s2)
	{ // Estable si hay union
		boolean cer;
		char c1, c2;
		c1 = s1.charAt(s1.length() - 1);
		c2 = s2.charAt(0);
		cer = false;
		if (letra(c1) < 6 && letra(c2) < 6)
		{
			if (hiato(c1, c2))
			{
				cer = false;
			}
			else
			{
				cer = true;
			}
		}
		return cer;
	}

	private String[] silabas(String cadena)
	{
		String temp;
		String s = "";
		int i, k;
		k = cadena.length();
		temp = cadena;

		for (i = 0; i < k; i++)
		{
			temp = silaba(cadena);
			if (i == 0)
			{
				s = s + temp;
			}
			else
			{
				if (strConsonantes(temp))
				{
					s = s + temp;
				}
				else
				{
					if (strVVstr(s, temp))
					{
						s = s + temp;
					}
					else
					{
						if (strConsonantes(s))
						{
							s = s + temp;
						}
						else
						{
							s = s + "-" + temp;
						}
					}
				}
			}
			i = i + temp.length() - 1;
			cadena = silabaRest(cadena);
		}
		return s.split("-");
	}

	private String rhymePart(final String word)
	{
		if (word.length() == 0)
		{
			return "";
		}

		String[] syllables = silabas(word.toLowerCase());

		// Monosilabo
		if (syllables.length == 1)
		{
			int lastVocal = lastVocalIndex(syllables[0]);
			return syllables[0].substring(lastVocal);
		}

		// Palabra aguda
		if (aguda(syllables))
		{
			int lastVocal = lastVocalIndex(syllables[syllables.length - 1]);
			return syllables[syllables.length - 1].substring(lastVocal);
		}

		// Palabra llana
		if (llana(syllables))
		{
			int lastVocal = lastVocalIndex(syllables[syllables.length - 2]);
			return syllables[syllables.length - 2].substring(lastVocal)
					+ syllables[syllables.length - 1];
		}

		// Esdrujula
		String parte = "";
		boolean found = false;
		for (String silaba : syllables)
		{
			if (found)
			{
				parte += silaba;
			}
			else if (acento(silaba))
			{
				found = true;
				parte = silaba.substring(lastVocalIndex(silaba));
			}
		}

		return parte;
	}

	private static final boolean isVocal(final char letter)
	{
		switch (letter) {
		case 'a':
		case 'e':
		case 'i':
		case 'o':
		case 'u':
		case 225: // a con acento
		case 233: // e con acento
		case 237: // i con acento
		case 243: // o con acento
		case 250: // u con acento
			return true;
		default:
			return false;
		}
	}

	private static final boolean acento(final char letter)
	{
		switch (letter) {
		case 225: // a con acento
		case 233: // e con acento
		case 237: // i con acento
		case 243: // o con acento
		case 250: // u con acento
			return true;
		default:
			return false;
		}
	}

	private static final boolean acento(final String word)
	{
		char[] letters = word.toCharArray();
		for (char letter : letters)
		{
			if (isVocal(letter) && acento(letter))
			{
				return true;
			}
		}
		return false;
	}

	private static int lastVocalIndex(final String syllable)
	{
		char[] letters = syllable.toCharArray();
		for (int i = letters.length - 1; i >= 0; i--)
		{
			if (isVocal(letters[i]))
			{
				return i;
			}
		}

		throw new IllegalArgumentException(
				"It is impossible to have a syllable without vowels");
	}

	private static boolean aguda(final String[] silabas)
	{
		String silaba = silabas[silabas.length - 1];
		char last = silaba.charAt(silaba.length() - 1);

		// Si termina en vocal acentuada => aguda
		if (acento(last))
		{
			return true;
		}

		// si termina en vocal 'n' o 's' y tiene acento => aguda
		char lastVocal = silaba.charAt(lastVocalIndex(silaba));
		if ((last == 'n' || last == 's' || isVocal(last)) && acento(lastVocal))
		{
			return true;
		}

		if (last == 'n' || last == 's' || isVocal(last))
		{
			return false;
		}

		if (last != 'n' && last != 's')
		{
			for (String s : silabas)
			{
				if (acento(s))
				{
					return false;
				}
			}

			return true;
		}

		return false;
	}

	private static boolean llana(final String[] silabas)
	{
		String silaba = silabas[silabas.length - 2];
		int vocalIndex = lastVocalIndex(silaba);
		char vocal = silaba.charAt(vocalIndex);

		if (!aguda(silabas))
		{
			if (acento(vocal))
			{
				return true;
			}
			else
			{
				for (String s : silabas)
				{
					if (acento(s))
					{
						return false;
					}
				}

				return true;
			}
		}

		return false;
	}

	private static boolean esdrujula(final String[] silabas)
	{
		int i = 0;
		for (i = 0; i < silabas.length; i++)
		{
			if (acento(silabas[i]))
			{
				break;
			}
		}

		return i == silabas.length - 3;
	}

	/**
	 * Removes the trailing punctuation from the given string
	 * 
	 * @param str The String to parse.
	 * @return The String without the trailing punctuation
	 */
	private String removeTrailingPunctuation(final String str)
	{
		if (str.length() == 0)
		{
			return str;
		}

		char[] chars = str.toCharArray();

		int i = chars.length - 1;
		while (i >= 0)
		{
			if (isLetter(chars[i]))
			{
				break;
			}
			i--;
		}

		// variable 'i' holds the last letter index
		return str.substring(0, i + 1);
	}

	@Override
	public StressType stressType(final String word)
	{
		String[] silabas = silabas(removeTrailingPunctuation(word));

		if (silabas.length == 1)
		{
			return StressType.LAST;
		}
		else if (esdrujula(silabas))
		{
			return StressType.THIRD_LAST;
		}
		else if (aguda(silabas))
		{
			return StressType.LAST;
		}
		else if (llana(silabas))
		{
			return StressType.SECOND_LAST;
		}
		else
		{
			return StressType.FOURTH_LAST;
		}
	}

	@Override
	public boolean rhyme(final String word1, final String word2)
	{
		String rhyme1 = phoneticRhymePart(word1);
		String rhyme2 = phoneticRhymePart(word2);

		return rhyme1.equalsIgnoreCase(rhyme2);
	}

	@Override
	public String phoneticRhymePart(final String word)
	{
		String withoutPunctuation = removeTrailingPunctuation(word);
		String rhymePart = rhymePart(withoutPunctuation);

		StringBuilder result = new StringBuilder();
		char[] letters = rhymePart.toCharArray();

		for (int i = 0; i < letters.length; i++)
		{
			switch (letters[i]) {
			// Vocales
			case 225: // a con acento
				result.append('a');
				break;
			case 233: // e con acento
				result.append('e');
				break;
			case 237: // i con acento
				result.append('i');
				break;
			case 243: // o con acento
				result.append('o');
				break;
			case 250: // u con acento
				result.append('u');
				break;
			case 252: // u con dieresis
				result.append('u');
				break;

			// Consonantes
			case 'b':
				result.append('v');
				break;
			case 'y':
				result.append("ll");
				break;

			// h => añadirla solo si es una 'ch'
			case 'h':
				if (i > 0 && letters[i - 1] == 'c')
				{
					result.append('h');
				}
				break;

			// g => transformarla en 'j' si va antes de 'e' o 'i'
			case 'g':
				if (i + 1 < letters.length
						&& (letters[i + 1] == 'e' || letters[i + 1] == 'i'))
				{
					result.append('j');
				}
				else
				{
					result.append('g');
				}
				break;

			// Otros
			default:
				result.append(letters[i]);
				break;
			}
		}

		return result.toString();
	}

	@Override
	public boolean isLetter(final char letter)
	{
		boolean isLetter = letter >= 97 && letter <= 122; // a-z
		isLetter = isLetter || letter >= 65 && letter <= 90; // A-Z

		if (isLetter)
		{
			return true;
		}

		// others: check extended ascii codes specific letters

		switch (letter) {
		case 193: // A con acento
		case 201: // E con acento
		case 205: // I con acento
		case 211: // O con acento
		case 218: // U con acento
		case 220: // U con dieresis
		case 225: // a con acento
		case 233: // e con acento
		case 237: // i con acento
		case 243: // o con acento
		case 250: // u con acento
		case 252: // u con dieresis
			return true;
		default:
			return false;
		}
	}

}
