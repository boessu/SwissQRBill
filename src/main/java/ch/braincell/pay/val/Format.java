package ch.braincell.pay.val;

/**
 * Helper class to format strings. Basically not specific to the context here
 * but anyway.
 * 
 * @author Boessu
 *
 */
class Format {

	/**
	 * Format a string with separators in uniform distance.
	 * 
	 * @param text      Text to format with separators
	 * @param separator the separator to use.
	 * @param distance  the distance between the separators.
	 * @param forward   true for left-to-right, false for right-to-left distance.
	 * @return formatted text.
	 */
	static String format(String text, char separator, int distance, boolean forward) {
		int len = text.length();
		if (len < distance)
			return text;

		float count = (float) len / (float) distance;
		int intcount = (int) count;
		int first = (int) (forward ? 0 : Math.round(((count - (float)intcount) * (float) distance)));

		StringBuilder sb = new StringBuilder(len + intcount);

		if (first != 0) {
			sb.append(text, 0, first);
			sb.append(separator);
		}

		for (int pos = first; pos < len; pos += distance) {
			int endPos = pos + distance;
			if (endPos > len)
				endPos = len;
			sb.append(text, pos, endPos);
			if (endPos != len)
				sb.append(separator);
		}
		return sb.toString();
	}
}
