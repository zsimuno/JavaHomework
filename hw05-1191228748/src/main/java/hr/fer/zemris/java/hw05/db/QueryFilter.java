/**
 * 
 */
package hr.fer.zemris.java.hw05.db;

import java.util.List;

import hr.fer.zemris.java.hw05.db.IFilter;

/**
 * Represents a filter that gets conditionals from a query and filters using
 * them.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class QueryFilter implements IFilter {

	/**
	 * List of conditional expressions
	 */
	List<ConditionalExpression> conditionalsList;

	/**
	 * Constructs a {@link QueryFilter} from the given list of
	 * {@link ConditionalExpression} objects.
	 * 
	 * @param list list of {@link ConditionalExpression} objects
	 */
	public QueryFilter(List<ConditionalExpression> list) {
		conditionalsList = list;
	}

	@Override
	public boolean accepts(StudentRecord record) {
		
		for (ConditionalExpression expr : conditionalsList) {
			
			boolean recordSatisfies = expr.getComparisonOperator().satisfied(
					expr.getFieldGetter().get(record), 
					expr.getStringLiteral() 
					);
			
			if(!recordSatisfies)
				return false;
		}
		
		return true;
		
	}

}
