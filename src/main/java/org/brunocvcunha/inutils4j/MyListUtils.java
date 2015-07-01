package org.brunocvcunha.inutils4j;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class MyListUtils<T> {

	public Set<T> getSet(Collection<T> collection) {
		Set<T> set = new TreeSet<T>();
		set.addAll(collection);

		return set;
	}

	public String join(Collection<T> col, String delim) {
		StringBuilder sb = new StringBuilder();
		Iterator<T> iter = col.iterator();
		if (iter.hasNext())
			sb.append(iter.next().toString());
		while (iter.hasNext()) {
			sb.append(delim);
			sb.append(iter.next().toString());
		}
		return sb.toString();
	}

}