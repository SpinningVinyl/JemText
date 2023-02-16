package net.prsv.jemtext;

import java.util.HashMap;

public class JTUtils
{
    private static final HashMap<Character, String> fullChars = new HashMap<>();
    private static final HashMap<Character, String> htmlChars = new HashMap<>();
    private static final HashMap<Character, String> mdChars = new HashMap<>();

    static {

        // Characters to be escaped in Markdown: https://www.markdownguide.org/basic-syntax/#escaping-characters
        mdChars.put('\\', "\\\\");
        mdChars.put('`', "\\`");
        mdChars.put('*', "\\*");
        mdChars.put('_', "\\_");
        mdChars.put('{', "\\{");
        mdChars.put('}', "\\}");
        mdChars.put('[', "\\[");
        mdChars.put(']', "\\]");
        mdChars.put('<', "\\<");
        mdChars.put('>', "\\>");
        mdChars.put('(', "\\(");
        mdChars.put(')', "\\)");
        mdChars.put('#', "\\#");
        mdChars.put('+', "\\+");
        mdChars.put('-', "\\-");
        
        // HTML escaping
        htmlChars.put('\u0026', "&amp;");
        htmlChars.put('\u003C', "&lt;");
        htmlChars.put('\u003E', "&gt;");
        htmlChars.put('\u0022', "&quot;");
        htmlChars.put('\'', "&apos;");

        fullChars.put('\u0026', "&amp;");
        fullChars.put('\u003C', "&lt;");
        fullChars.put('\u003E', "&gt;");
        fullChars.put('\u0022', "&quot;");
        fullChars.put('\'', "&apos;");

        // special typographic symbols and Western/Central European letters (umlauts etc.)
        fullChars.put('\u0152', "&OElig;");
        fullChars.put('\u0153', "&oelig;");
        fullChars.put('\u0160', "&Scaron;");
        fullChars.put('\u0161', "&scaron;");
        fullChars.put('\u0178', "&Yuml;");
        fullChars.put('\u02C6', "&circ;");
        fullChars.put('\u02DC', "&tilde;");
        fullChars.put('\u2002', "&ensp;");
        fullChars.put('\u2003', "&emsp;");
        fullChars.put('\u2009', "&thinsp;");
        fullChars.put('\u200C', "&zwnj;");
        fullChars.put('\u200D', "&zwj;");
        fullChars.put('\u200E', "&lrm;");
        fullChars.put('\u200F', "&rlm;");
        fullChars.put('\u2013', "&ndash;");
        fullChars.put('\u2014', "&mdash;");
        fullChars.put('\u2018', "&lsquo;");
        fullChars.put('\u2019', "&rsquo;");
        fullChars.put('\u201A', "&sbquo;");
        fullChars.put('\u201C', "&ldquo;");
        fullChars.put('\u201D', "&rdquo;");
        fullChars.put('\u201E', "&bdquo;");
        fullChars.put('\u2020', "&dagger;");
        fullChars.put('\u2021', "&Dagger;");
        fullChars.put('\u2030', "&permil;");
        fullChars.put('\u2039', "&lsaquo;");
        fullChars.put('\u203A', "&rsaquo;");
        fullChars.put('\u20AC', "&euro;");
        fullChars.put('\u00A0', "&nbsp;");
        fullChars.put('\u00A1', "&iexcl;");
        fullChars.put('\u00A2', "&cent;");
        fullChars.put('\u00A3', "&pound;");
        fullChars.put('\u00A4', "&curren;");
        fullChars.put('\u00A5', "&yen;");
        fullChars.put('\u00A6', "&brvbar;");
        fullChars.put('\u00A7', "&sect;");
        fullChars.put('\u00A8', "&uml;");
        fullChars.put('\u00A9', "&copy;");
        fullChars.put('\u00AA', "&ordf;");
        fullChars.put('\u00AB', "&laquo;");
        fullChars.put('\u00AC', "&not;");
        fullChars.put('\u00AD', "&shy;");
        fullChars.put('\u00AE', "&reg;");
        fullChars.put('\u00AF', "&macr;");
        fullChars.put('\u00B0', "&deg;");
        fullChars.put('\u00B1', "&plusmn;");
        fullChars.put('\u00B2', "&sup2;");
        fullChars.put('\u00B3', "&sup3;");
        fullChars.put('\u00B4', "&acute;");
        fullChars.put('\u00B5', "&micro;");
        fullChars.put('\u00B6', "&para;");
        fullChars.put('\u00B7', "&middot;");
        fullChars.put('\u00B8', "&cedil;");
        fullChars.put('\u00B9', "&sup1;");
        fullChars.put('\u00BA', "&ordm;");
        fullChars.put('\u00BB', "&raquo;");
        fullChars.put('\u00BC', "&frac14;");
        fullChars.put('\u00BD', "&frac12;");
        fullChars.put('\u00BE', "&frac34;");
        fullChars.put('\u00BF', "&iquest;");
        fullChars.put('\u00C0', "&Agrave;");
        fullChars.put('\u00C1', "&Aacute;");
        fullChars.put('\u00C2', "&Acirc;");
        fullChars.put('\u00C3', "&Atilde;");
        fullChars.put('\u00C4', "&Auml;");
        fullChars.put('\u00C5', "&Aring;");
        fullChars.put('\u00C6', "&AElig;");
        fullChars.put('\u00C7', "&Ccedil;");
        fullChars.put('\u00C8', "&Egrave;");
        fullChars.put('\u00C9', "&Eacute;");
        fullChars.put('\u00CA', "&Ecirc;");
        fullChars.put('\u00CB', "&Euml;");
        fullChars.put('\u00CC', "&Igrave;");
        fullChars.put('\u00CD', "&Iacute;");
        fullChars.put('\u00CE', "&Icirc;");
        fullChars.put('\u00CF', "&Iuml;");
        fullChars.put('\u00D0', "&ETH;");
        fullChars.put('\u00D1', "&Ntilde;");
        fullChars.put('\u00D2', "&Ograve;");
        fullChars.put('\u00D3', "&Oacute;");
        fullChars.put('\u00D4', "&Ocirc;");
        fullChars.put('\u00D5', "&Otilde;");
        fullChars.put('\u00D6', "&Ouml;");
        fullChars.put('\u00D7', "&times;");
        fullChars.put('\u00D8', "&Oslash;");
        fullChars.put('\u00D9', "&Ugrave;");
        fullChars.put('\u00DA', "&Uacute;");
        fullChars.put('\u00DB', "&Ucirc;");
        fullChars.put('\u00DC', "&Uuml;");
        fullChars.put('\u00DD', "&Yacute;");
        fullChars.put('\u00DE', "&THORN;");
        fullChars.put('\u00DF', "&szlig;");
        fullChars.put('\u00E0', "&agrave;");
        fullChars.put('\u00E1', "&aacute;");
        fullChars.put('\u00E2', "&acirc;");
        fullChars.put('\u00E3', "&atilde;");
        fullChars.put('\u00E4', "&auml;");
        fullChars.put('\u00E5', "&aring;");
        fullChars.put('\u00E6', "&aelig;");
        fullChars.put('\u00E7', "&ccedil;");
        fullChars.put('\u00E8', "&egrave;");
        fullChars.put('\u00E9', "&eacute;");
        fullChars.put('\u00EA', "&ecirc;");
        fullChars.put('\u00EB', "&euml;");
        fullChars.put('\u00EC', "&igrave;");
        fullChars.put('\u00ED', "&iacute;");
        fullChars.put('\u00EE', "&icirc;");
        fullChars.put('\u00EF', "&iuml;");
        fullChars.put('\u00F0', "&eth;");
        fullChars.put('\u00F1', "&ntilde;");
        fullChars.put('\u00F2', "&ograve;");
        fullChars.put('\u00F3', "&oacute;");
        fullChars.put('\u00F4', "&ocirc;");
        fullChars.put('\u00F5', "&otilde;");
        fullChars.put('\u00F6', "&ouml;");
        fullChars.put('\u00F7', "&divide;");
        fullChars.put('\u00F8', "&oslash;");
        fullChars.put('\u00F9', "&ugrave;");
        fullChars.put('\u00FA', "&uacute;");
        fullChars.put('\u00FB', "&ucirc;");
        fullChars.put('\u00FC', "&uuml;");
        fullChars.put('\u00FD', "&yacute;");
        fullChars.put('\u00FE', "&thorn;");
        fullChars.put('\u00FF', "&yuml;");

        // Mathematical, Greek and related characters
        fullChars.put('\u0192', "&fnof;");
        fullChars.put('\u0391', "&Alpha;");
        fullChars.put('\u0392', "&Beta;");
        fullChars.put('\u0393', "&Gamma;");
        fullChars.put('\u0394', "&Delta;");
        fullChars.put('\u0395', "&Epsilon;");
        fullChars.put('\u0396', "&Zeta;");
        fullChars.put('\u0397', "&Eta;");
        fullChars.put('\u0398', "&Theta;");
        fullChars.put('\u0399', "&Iota;");
        fullChars.put('\u039A', "&Kappa;");
        fullChars.put('\u039B', "&Lambda;");
        fullChars.put('\u039C', "&Mu;");
        fullChars.put('\u039D', "&Nu;");
        fullChars.put('\u039E', "&Xi;");
        fullChars.put('\u039F', "&Omicron;");
        fullChars.put('\u03A0', "&Pi;");
        fullChars.put('\u03A1', "&Rho;");
        fullChars.put('\u03A3', "&Sigma;");
        fullChars.put('\u03A4', "&Tau;");
        fullChars.put('\u03A5', "&Upsilon;");
        fullChars.put('\u03A6', "&Phi;");
        fullChars.put('\u03A7', "&Chi;");
        fullChars.put('\u03A8', "&Psi;");
        fullChars.put('\u03A9', "&Omega;");
        fullChars.put('\u03B1', "&alpha;");
        fullChars.put('\u03B2', "&beta;");
        fullChars.put('\u03B3', "&gamma;");
        fullChars.put('\u03B4', "&delta;");
        fullChars.put('\u03B5', "&epsilon;");
        fullChars.put('\u03B6', "&zeta;");
        fullChars.put('\u03B7', "&eta;");
        fullChars.put('\u03B8', "&theta;");
        fullChars.put('\u03B9', "&iota;");
        fullChars.put('\u03BA', "&kappa;");
        fullChars.put('\u03BB', "&lambda;");
        fullChars.put('\u03BC', "&mu;");
        fullChars.put('\u03BD', "&nu;");
        fullChars.put('\u03BE', "&xi;");
        fullChars.put('\u03BF', "&omicron;");
        fullChars.put('\u03C0', "&pi;");
        fullChars.put('\u03C1', "&rho;");
        fullChars.put('\u03C2', "&sigmaf;");
        fullChars.put('\u03C3', "&sigma;");
        fullChars.put('\u03C4', "&tau;");
        fullChars.put('\u03C5', "&upsilon;");
        fullChars.put('\u03C6', "&phi;");
        fullChars.put('\u03C7', "&chi;");
        fullChars.put('\u03C8', "&psi;");
        fullChars.put('\u03C9', "&omega;");
        fullChars.put('\u03D1', "&thetasym;");
        fullChars.put('\u03D2', "&upsih;");
        fullChars.put('\u03D6', "&piv;");
        fullChars.put('\u2022', "&bull;");
        fullChars.put('\u2026', "&hellip;");
        fullChars.put('\u2032', "&prime;");
        fullChars.put('\u2033', "&Prime;");
        fullChars.put('\u203E', "&oline;");
        fullChars.put('\u2044', "&frasl;");
        fullChars.put('\u2118', "&weierp;");
        fullChars.put('\u2111', "&image;");
        fullChars.put('\u211C', "&real;");
        fullChars.put('\u2122', "&trade;");
        fullChars.put('\u2135', "&alefsym;");
        fullChars.put('\u2190', "&larr;");
        fullChars.put('\u2191', "&uarr;");
        fullChars.put('\u2192', "&rarr;");
        fullChars.put('\u2193', "&darr;");
        fullChars.put('\u2194', "&harr;");
        fullChars.put('\u21B5', "&crarr;");
        fullChars.put('\u21D0', "&lArr;");
        fullChars.put('\u21D1', "&uArr;");
        fullChars.put('\u21D2', "&rArr;");
        fullChars.put('\u21D3', "&dArr;");
        fullChars.put('\u21D4', "&hArr;");
        fullChars.put('\u2200', "&forall;");
        fullChars.put('\u2202', "&part;");
        fullChars.put('\u2203', "&exist;");
        fullChars.put('\u2205', "&empty;");
        fullChars.put('\u2207', "&nabla;");
        fullChars.put('\u2208', "&isin;");
        fullChars.put('\u2209', "&notin;");
        fullChars.put('\u220B', "&ni;");
        fullChars.put('\u220F', "&prod;");
        fullChars.put('\u2211', "&sum;");
        fullChars.put('\u2212', "&minus;");
        fullChars.put('\u2217', "&lowast;");
        fullChars.put('\u221A', "&radic;");
        fullChars.put('\u221D', "&prop;");
        fullChars.put('\u221E', "&infin;");
        fullChars.put('\u2220', "&ang;");
        fullChars.put('\u2227', "&and;");
        fullChars.put('\u2228', "&or;");
        fullChars.put('\u2229', "&cap;");
        fullChars.put('\u222A', "&cup;");
        fullChars.put('\u222B', "&int;");
        fullChars.put('\u2234', "&there4;");
        fullChars.put('\u223C', "&sim;");
        fullChars.put('\u2245', "&cong;");
        fullChars.put('\u2248', "&asymp;");
        fullChars.put('\u2260', "&ne;");
        fullChars.put('\u2261', "&equiv;");
        fullChars.put('\u2264', "&le;");
        fullChars.put('\u2265', "&ge;");
        fullChars.put('\u2282', "&sub;");
        fullChars.put('\u2283', "&sup;");
        fullChars.put('\u2284', "&nsub;");
        fullChars.put('\u2286', "&sube;");
        fullChars.put('\u2287', "&supe;");
        fullChars.put('\u2295', "&oplus;");
        fullChars.put('\u2297', "&otimes;");
        fullChars.put('\u22A5', "&perp;");
        fullChars.put('\u22C5', "&sdot;");
        fullChars.put('\u2308', "&lceil;");
        fullChars.put('\u2309', "&rceil;");
        fullChars.put('\u230A', "&lfloor;");
        fullChars.put('\u230B', "&rfloor;");
        fullChars.put('\u2329', "&lang;");
        fullChars.put('\u232A', "&rang;");
        fullChars.put('\u25CA', "&loz;");
        fullChars.put('\u2660', "&spades;");
        fullChars.put('\u2663', "&clubs;");
        fullChars.put('\u2665', "&hearts;");
        fullChars.put('\u2666', "&diams;");
    }

    public static String htmlEncode(String source) {
        return encode(source, htmlChars);
    }

    public static String fullEncode(String source) {
        return encode(source, fullChars);
    }

    public static String mdEncode(String source) {
        return encode(source, mdChars);
    }

    private static String encode(String input, HashMap<Character, String> encodingTable) {
        if (input == null) {
            return null;
        }

        if (encodingTable == null) {
            return input;
        }

        StringBuilder result = null;
        char[] charsToEncode = input.toCharArray();
        int lastMatch = -1;
        int charsSinceLastMatch = 0;

        for (int i = 0; i < charsToEncode.length; i++) {
            char currentChar = charsToEncode[i];

            if (encodingTable.containsKey(currentChar)) {
                if (null == result) {
                    result = new StringBuilder(input.length());
                }
                charsSinceLastMatch = i - (lastMatch + 1);
                if (charsSinceLastMatch > 0) {
                    result.append(charsToEncode, lastMatch + 1, charsSinceLastMatch);
                }
                result.append(encodingTable.get(currentChar));
                lastMatch = i;
            }
        }

        if (null == result) {
            return input;
        }
        else {
            charsSinceLastMatch = charsToEncode.length - (lastMatch + 1);
            if (charsSinceLastMatch > 0) {
                result.append(charsToEncode, lastMatch + 1, charsSinceLastMatch);
            }
            return result.toString();
        }
    }
}