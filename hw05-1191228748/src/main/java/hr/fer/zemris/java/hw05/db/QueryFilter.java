/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

import java.util.List;

import hr.fer.zemris.java.hw05.db.IFilter;

/**
 * @author Zvonimir Šimunović
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * @param list
	 */
	public QueryFilter(List<ConditionalExpression> list) {

	}

	@Override
	public boolean accepts(StudentRecord record) {
		// TODO Auto-generated method stub
		return false;
	}

}
