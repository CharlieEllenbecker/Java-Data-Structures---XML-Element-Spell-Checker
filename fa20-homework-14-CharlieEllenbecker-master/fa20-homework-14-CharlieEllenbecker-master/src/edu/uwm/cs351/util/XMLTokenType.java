package edu.uwm.cs351.util;

/**
 * Enumeration of the XML token types.
 */
public enum XMLTokenType {
	 /** Represents: End of file */
	END,
     /** Represents: Error */
    ERROR,
     /** Represents: &lt;Name */
    OPEN,
     /** Represents: Name=AttrVal */
    ATTR,
     /** Represents: &gt; */
    CLOSE,
     /** Represents: /&gt; */
    ECLOSE,
     /** Represents: &lt;/Name&gt; */
    ETAG,
     /** Represents: Plain text */
    TEXT;

}