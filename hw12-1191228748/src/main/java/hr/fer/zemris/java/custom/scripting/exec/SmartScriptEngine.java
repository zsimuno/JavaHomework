package hr.fer.zemris.java.custom.scripting.exec;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.INodeVisitor;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.webserver.RequestContext;

/**
 * Class that executes {@code SmartScript} programs from programs document tree.
 * 
 * @author Zvonimir Šimunović
 *
 */
public class SmartScriptEngine {

	/** Root node of the tree parsed form the program. */
	private DocumentNode documentNode;
	/** Context of the request. */
	private RequestContext requestContext;
	/** Multistack. */
	private ObjectMultistack multistack = new ObjectMultistack();

	/** Visitor that executes the nodes. */
	private INodeVisitor visitor = new INodeVisitor() {

		// TODO razlikovati Integer, Double i String
		@Override
		public void visitTextNode(TextNode node) {
			try {
				requestContext.write(node.getText());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void visitForLoopNode(ForLoopNode node) {

			if (!(node.getVariable() instanceof ElementVariable)
					|| !(node.getStartExpression() instanceof ElementConstantInteger)
					|| !(node.getStepExpression() == null || node.getStepExpression() instanceof ElementConstantInteger)
					|| !(node.getEndExpression() instanceof ElementConstantInteger)) {
				// TODO error
				// TODO moze li biti double?
			}

			String var = ((ElementVariable) node.getVariable()).getName();
			Integer start = ((ElementConstantInteger) node.getStartExpression()).getValue();
			Integer step = node.getStepExpression() == null ? null
					: ((ElementConstantInteger) node.getStepExpression()).getValue();
			Integer end = ((ElementConstantInteger) node.getEndExpression()).getValue();
			multistack.push(var, new ValueWrapper(start));
			if (start > end)
				return;

			Integer value;
			do {
				acceptChildren(node);

				value = (Integer) multistack.pop(var).getValue();
				if (step != null) {
					value += step;
					multistack.push(var, new ValueWrapper(value));
				}
			} while (value <= end); // TODO if it is still less than or equal to final

			multistack.pop(var);

		}

		@Override
		public void visitEchoNode(EchoNode node) {
			Element[] elems = node.getElements();
			Stack<Object> stack = new Stack<>();
			for (int i = 0; i < elems.length; i++) {
				Element el = elems[i];

				if (el instanceof ElementVariable) {
					stack.push(multistack.peek(((ElementVariable) el).getName()).getValue());

				} else if (el instanceof ElementConstantInteger) {
					stack.push(((ElementConstantInteger) el).getValue());

				} else if (el instanceof ElementConstantDouble) {
					stack.push(((ElementConstantDouble) el).getValue());

				} else if (el instanceof ElementString) {
					stack.push(((ElementString) el).getValue());

				} else if (el instanceof ElementFunction) {
					function(((ElementFunction) el).getName(), stack);

				} else if (el instanceof ElementOperator) {
					operator(((ElementOperator) el).getSymbol(), stack);

				} else {
					// TODO error?
				}
			}

			if (stack.isEmpty())
				return;

			List<Object> list = new ArrayList<>();
			while (!stack.isEmpty()) {
				list.add(stack.pop());
			}

			for (int j = list.size() - 1; j >= 0; j--) {
				try {
					requestContext.write(list.get(j).toString());
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		/**
		 * TODO
		 * 
		 * @param function
		 * @param stack
		 */
		private void function(String function, Stack<Object> stack) {

			// TODO Provjere instanci?
			switch (function) {
			case "sin": { // TODO koji kut?
				Object x = stack.pop();
				Double number;
				if (x instanceof Number) {
					number = ((Number) x).doubleValue();
				} else {
					number = Double.parseDouble(x.toString());
				}
				stack.push(Math.sin(number * Math.PI / 180.0));
			}
				break;
			case "decfmt": {
				Object f = stack.pop();
				Object x = stack.pop();
				DecimalFormat df = new DecimalFormat(f.toString());
				stack.push(df.format(x));
			}
				break;
			case "dup":
				stack.push(stack.peek());
				break;
			case "swap": {
				Object a = stack.pop();
				Object b = stack.pop();
				stack.push(a);
				stack.push(b);
			}
				break;
			case "setMimeType":
				requestContext.setMimeType(stack.pop().toString());
				break;
			case "paramGet":
				paramGet(requestContext::getParameter, stack);
				break;
			case "pparamGet":
				paramGet(requestContext::getPersistentParameter, stack);
				break;
			case "tparamGet":
				paramGet(requestContext::getTemporaryParameter, stack);
				break;
			case "tparamSet":
				paramSet(requestContext::setTemporaryParameter, stack);
				break;
			case "pparamSet":
				paramSet(requestContext::setPersistentParameter, stack);
				break;
			case "pparamDel":
				paramDelete(requestContext::removePersistentParameter, stack);
				break;
			case "tparamDel":
				paramDelete(requestContext::removeTemporaryParameter, stack);
				break;
			default:
				// TODO error?
				break;
			}

		}

		/**
		 * Gets parameter using given {@code getter} (takes name from stack) and then
		 * pushes it to the {@code stack}. If name is not present in parameters then it
		 * pushes a default value given in the stack.
		 * 
		 * @param getter getter with which we get the parameter.
		 * @param stack  stack of values.
		 */
		private void paramGet(UnaryOperator<String> getter, Stack<Object> stack) {
			String defValue = stack.pop().toString();
			String name = stack.pop().toString();
			String value = getter.apply(name);
			stack.push(value == null ? defValue : value);
		}

		/**
		 * Sets parameter using given {@code Setter} (takes name and value from stack).
		 * 
		 * @param setter setter with which we set the parameter.
		 * @param stack  stack of values.
		 */
		private void paramSet(BiConsumer<String, String> setter, Stack<Object> stack) {
			String name = stack.pop().toString();
			String value = stack.pop().toString();
			setter.accept(name, value);
		}

		/**
		 * Deletes parameter using given {@code delConsumer} (takes name from stack).
		 * 
		 * @param delConsumer consumer with which we delete the parameter.
		 * @param stack       stack of values.
		 */
		private void paramDelete(Consumer<String> delConsumer, Stack<Object> stack) {
			String name = stack.pop().toString();
			delConsumer.accept(name);
		}

		/**
		 * Executes operator.
		 * 
		 * @param operator operator to be executed.
		 * @param stack    helper stack that contains constants.
		 */
		private void operator(Character operator, Stack<Object> stack) {
			// TODO provjera instance?
			double v1 = toNumber(stack.pop());
			double v2 = toNumber(stack.pop());

			// TODO implement
			switch (operator) {
			case '+':
				stack.push(v1 + v2);
				break;
			case '-':
				stack.push(v1 - v2);

				break;
			case '*':
				stack.push(v1 * v2);

				break;
			case '/':
				stack.push(v1 / v2);

				break;

			default:
				break;
			}
		}

		@Override
		public void visitDocumentNode(DocumentNode node) {
			acceptChildren(node);
			// TODO ima li ovo smisla?
			try {
				requestContext.write("\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/**
		 * @param object
		 * @return
		 */
		public double toNumber(Object object) {
			if (object instanceof Number) {
				return ((Number) object).doubleValue();
			} else {
				return Double.parseDouble(object.toString());
			}
		}

		/**
		 * Calls "accept" method on all the children of the given node.
		 * 
		 * @param node parent node.
		 */
		private void acceptChildren(Node node) {
			for (int i = 0, n = node.numberOfChildren(); i < n; ++i) {
				node.getChild(i).accept(this);
			}

		}
	};

	/**
	 * Constructor.
	 * 
	 * @param documentNode   Root node of the tree parsed form the program.
	 * @param requestContext Context of the request.
	 */
	public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
		this.documentNode = documentNode;
		this.requestContext = requestContext;
	}

	/**
	 * Executes the program given in the constructor via the root document node.
	 */
	public void execute() {
		documentNode.accept(visitor);
	}

}
