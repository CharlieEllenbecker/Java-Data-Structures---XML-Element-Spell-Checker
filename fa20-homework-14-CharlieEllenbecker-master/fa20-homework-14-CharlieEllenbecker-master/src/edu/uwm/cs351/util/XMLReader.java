package edu.uwm.cs351.util;

import java.io.Reader;
import java.util.Stack;

/**
 * Class to read XML into an Element.
 */
public class XMLReader {

	private XMLTokenizer _tokenizer;
	// #(
	private Stack<String> _pending = new Stack<>();
	private boolean isHTML = false;
	// #)
	// TODO: perhaps other fields
	
	/**
	 * Create an XML reader that uses the given input source.
	 * @param r source of characters, must not be null.
	 */
	public XMLReader(Reader r) {
		_tokenizer = new XMLTokenizer(r);
	}
	
	/**
	 * Return the current line number of the input source.
	 * This method is particular useful to be called when catching
	 * a parse exception.
	 * @return current line number.
	 */
	public int getLineNumber() {
		return _tokenizer.getLineNumber();
	}
	
	/**
	 * Read the source stream and return an XML element of the contents.
	 * If the stream does not appear to be XML, it returns null.
	 * If it starts as XML but then has an error, an exception is thrown.
	 * <p>
	 * If the stream's first element starts with the XML tag &lt;html&gt;,
	 * then the stream is read as HTML: not all open tags must be closed,
	 * and &lt;script&gt; and &lt;style&gt; tags are treated specially
	 * (They only include CDATA, not other XML tags.  This means that
	 * uses of "&lt;" inside these elements are not errors. )
	 * <p>
	 * This method should only be called once, as compliant XML files consist of a single element.
	 * @return element of XML successfully read if there are no parse errors.
	 * @throws XMLParseException if there is a parse error while reading XML.
	 * If the file is clearly not XML, no exception is thrown; rather null is returned.
	 */
	public Element readElement() throws XMLParseException {
		// #(
		switch (_tokenizer.next()) {
		case OPEN:
			if (_tokenizer.getCurrentName().equalsIgnoreCase("html")) {
				_tokenizer.addCDATA("script");
				_tokenizer.addCDATA("style");
				isHTML = true;
			}
			_tokenizer.saveToken();
			Element result = (Element)read();
			// System.out.println("result = " + result);
			if (_tokenizer.hasNext()) throw new XMLParseException("more than one element!");
			return result;
		default:
			break;
		}
		// #)
		// TODO: start the process and then call the recursive helper method
		return null;
	}
	
	// #(
	/**
	 * Return the next element or textual element (String) in the XML file.
	 * @param open tags currently open (so that an ETAG with one
	 * of these names can legally end a nested element.
	 * @return the next string or element
	 * @throws XMLParseException if a problem is found by the tokenizer or if 
	 * attributes are duplicated or if an ending tag does not match the start tag. 
	 */
	private Object read() throws XMLParseException {
		switch (_tokenizer.next()) {
		default: break;
		case ERROR: throw new XMLParseException(_tokenizer.getCurrentText());
		case TEXT: return _tokenizer.getCurrentText();
		case OPEN:
			Element result = new Element(_tokenizer.getCurrentName());
			while (_tokenizer.next() == XMLTokenType.ATTR) {
				if (result.setAttr(_tokenizer.getCurrentName(), _tokenizer.getCurrentText()) != null) {
					throw new XMLParseException("duplicate attribute: " + _tokenizer.getCurrentName());
				}
			}
			if (_tokenizer.current() == XMLTokenType.ECLOSE) {
				// System.out.println("ended /> for " + result.getName());
				return result;
			}
			if (_tokenizer.current() != XMLTokenType.CLOSE) {
				throw new XMLParseException(_tokenizer.getCurrentText());
			}
			_pending.push(result.getName());
			while (_tokenizer.next() != XMLTokenType.ETAG) {
				/*if (_tokenizer.current() == XMLTokenType.END) {
					throw new XMLParseException("Unexpected end of file");
				}*/
				if (_tokenizer.current() == XMLTokenType.END) {
					if (isHTML) {
						_pending.pop();
						return result;
					} else {
						throw new XMLParseException("Missing end tag for <" + result.getName() + ">");
					}
				}
				_tokenizer.saveToken();
				result.addContent(read());
			}
			_pending.pop();
			if (!_tokenizer.getCurrentName().equals(result.getName())) {
				if (isHTML) {
					// System.out.println("HTML backup for " + result.getName());
					for (String p : _pending) {
						if (_tokenizer.getCurrentName().equals(p)) {
							_tokenizer.saveToken();
							//System.out.println("implicitly ended by </" + p + ">");
							return result;
						}
					}
				}
				throw new XMLParseException("<" + result.getName() + "> ended with </" + _tokenizer.getCurrentName() + ">");
			}
			return result;
		case END: throw new XMLParseException("no XML element");
		}
		// NB: if execution reaches here, we did something wrong.
		throw new XMLParseException("internal error: what kind of token is this? " + _tokenizer);
	}
	// #)
	// TODO: recursive helper method
}
