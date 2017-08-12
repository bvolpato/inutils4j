/**
 * Copyright (C) 2014 Bruno Candido Volpato da Cunha (brunocvcunha@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.brunocvcunha.inutils4j;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.swing.JFileChooser;

import org.apache.commons.codec.binary.Hex;

/**
 * String (In)utilities.
 * 
 * @author Bruno Candido Volpato da Cunha
 */
public class MyStringUtils {

  public static final int CREATE = 0;
  public static final int APPEND = 1;
  public static final int FILL = 2;
  public static final NumberFormat _ffmt = NumberFormat.getInstance();
  public static final String XLATE = "0123456789abcdef";
  public static final String RETURN = "\n";
  public static final String CARRIAGE = "\r";
  public static final String CARRIAGE_RETURN = "\r\n";
  public static String lineSeparator;

  public static Pattern HAS_LETTER_PATTERN = Pattern.compile("[a-zA-Z]");

  public static Pattern EMAIL_PATTERN = Pattern
      .compile("[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})");


  public static final Set<UnicodeBlock> JAPANESE_BLOCKS = new HashSet<UnicodeBlock>() {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    {
      add(UnicodeBlock.HIRAGANA);
      add(UnicodeBlock.KATAKANA);
      add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
    }
  };
  public static final Set<UnicodeBlock> CHINESE_BLOCKS = new HashSet<UnicodeBlock>() {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    {
      add(UnicodeBlock.CJK_COMPATIBILITY);
      add(UnicodeBlock.CJK_COMPATIBILITY_FORMS);
      add(UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS);
      add(UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT);
      add(UnicodeBlock.CJK_RADICALS_SUPPLEMENT);
      add(UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION);
      add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
      add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
      add(UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B);
      add(UnicodeBlock.KANGXI_RADICALS);
      add(UnicodeBlock.IDEOGRAPHIC_DESCRIPTION_CHARACTERS);
    }
  };

  public static final Set<UnicodeBlock> ARABIC_BLOCKS = new HashSet<UnicodeBlock>() {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    {
      add(UnicodeBlock.ARABIC);
    }
  };

  static {
    _ffmt.setMinimumIntegerDigits(1);
    _ffmt.setMinimumFractionDigits(1);
    _ffmt.setMaximumFractionDigits(2);

    lineSeparator = RETURN;
    try {
      lineSeparator = System.getProperty("line.separator");
    } catch (Exception localException) {
    }
  }

  
  public static Map<String, Character> escapeStrings = new LinkedHashMap<String, Character>();
  public static Map<String, Character> accentStrings  = new LinkedHashMap<String, Character>();

  static {
    // HTML character entity references as defined in HTML 4
    // see http://www.w3.org/TR/REC-html40/sgml/entities.html
    escapeStrings.put("&amp;", new Character('\u0026'));
    escapeStrings.put("&nbsp;", new Character('\u00A0'));
    escapeStrings.put("&iexcl;", new Character('\u00A1'));
    escapeStrings.put("&cent;", new Character('\u00A2'));
    escapeStrings.put("&pound;", new Character('\u00A3'));
    escapeStrings.put("&curren;", new Character('\u00A4'));
    escapeStrings.put("&yen;", new Character('\u00A5'));
    escapeStrings.put("&brvbar;", new Character('\u00A6'));
    escapeStrings.put("&sect;", new Character('\u00A7'));
    escapeStrings.put("&uml;", new Character('\u00A8'));
    escapeStrings.put("&copy;", new Character('\u00A9'));
    escapeStrings.put("&ordf;", new Character('\u00AA'));
    escapeStrings.put("&laquo;", new Character('\u00AB'));
    escapeStrings.put("&not;", new Character('\u00AC'));
    escapeStrings.put("&shy;", new Character('\u00AD'));
    escapeStrings.put("&reg;", new Character('\u00AE'));
    escapeStrings.put("&macr;", new Character('\u00AF'));
    escapeStrings.put("&deg;", new Character('\u00B0'));
    escapeStrings.put("&plusmn;", new Character('\u00B1'));
    escapeStrings.put("&sup2;", new Character('\u00B2'));
    escapeStrings.put("&sup3;", new Character('\u00B3'));
    escapeStrings.put("&acute;", new Character('\u00B4'));
    accentStrings.put("&acute;", new Character('\u00B4'));
    escapeStrings.put("&micro;", new Character('\u00B5'));
    escapeStrings.put("&para;", new Character('\u00B6'));
    escapeStrings.put("&middot;", new Character('\u00B7'));
    escapeStrings.put("&cedil;", new Character('\u00B8'));
    escapeStrings.put("&sup1;", new Character('\u00B9'));
    escapeStrings.put("&ordm;", new Character('\u00BA'));
    escapeStrings.put("&raquo;", new Character('\u00BB'));
    escapeStrings.put("&frac14;", new Character('\u00BC'));
    escapeStrings.put("&frac12;", new Character('\u00BD'));
    escapeStrings.put("&frac34;", new Character('\u00BE'));
    escapeStrings.put("&iquest;", new Character('\u00BF'));
    escapeStrings.put("&Agrave;", new Character('\u00C0'));
    accentStrings.put("&Agrave;", new Character('\u00C0'));
    escapeStrings.put("&Aacute;", new Character('\u00C1'));
    accentStrings.put("&Aacute;", new Character('\u00C1'));
    escapeStrings.put("&Acirc;", new Character('\u00C2'));
    accentStrings.put("&Acirc;", new Character('\u00C2'));
    escapeStrings.put("&Atilde;", new Character('\u00C3'));
    accentStrings.put("&Atilde;", new Character('\u00C3'));
    escapeStrings.put("&Auml;", new Character('\u00C4'));
    escapeStrings.put("&Aring;", new Character('\u00C5'));
    escapeStrings.put("&AElig;", new Character('\u00C6'));
    escapeStrings.put("&Ccedil;", new Character('\u00C7'));
    accentStrings.put("&Ccedil;", new Character('\u00C7'));
    escapeStrings.put("&Egrave;", new Character('\u00C8'));
    accentStrings.put("&Egrave;", new Character('\u00C8'));
    escapeStrings.put("&Eacute;", new Character('\u00C9'));
    accentStrings.put("&Eacute;", new Character('\u00C9'));
    escapeStrings.put("&Ecirc;", new Character('\u00CA'));
    accentStrings.put("&Ecirc;", new Character('\u00CA'));
    escapeStrings.put("&Euml;", new Character('\u00CB'));
    escapeStrings.put("&Igrave;", new Character('\u00CC'));
    accentStrings.put("&Igrave;", new Character('\u00CC'));
    escapeStrings.put("&Iacute;", new Character('\u00CD'));
    accentStrings.put("&Iacute;", new Character('\u00CD'));
    escapeStrings.put("&Icirc;", new Character('\u00CE'));
    accentStrings.put("&Icirc;", new Character('\u00CE'));
    escapeStrings.put("&Iuml;", new Character('\u00CF'));
    escapeStrings.put("&ETH;", new Character('\u00D0'));
    escapeStrings.put("&Ntilde;", new Character('\u00D1'));
    accentStrings.put("&Ntilde;", new Character('\u00D1'));
    escapeStrings.put("&Ograve;", new Character('\u00D2'));
    accentStrings.put("&Ograve;", new Character('\u00D2'));
    escapeStrings.put("&Oacute;", new Character('\u00D3'));
    accentStrings.put("&Oacute;", new Character('\u00D3'));
    escapeStrings.put("&Ocirc;", new Character('\u00D4'));
    accentStrings.put("&Ocirc;", new Character('\u00D4'));
    escapeStrings.put("&Otilde;", new Character('\u00D5'));
    accentStrings.put("&Otilde;", new Character('\u00D5'));
    escapeStrings.put("&Ouml;", new Character('\u00D6'));
    escapeStrings.put("&times;", new Character('\u00D7'));
    escapeStrings.put("&Oslash;", new Character('\u00D8'));
    escapeStrings.put("&Ugrave;", new Character('\u00D9'));
    accentStrings.put("&Ugrave;", new Character('\u00D9'));
    escapeStrings.put("&Uacute;", new Character('\u00DA'));
    accentStrings.put("&Uacute;", new Character('\u00DA'));
    escapeStrings.put("&Ucirc;", new Character('\u00DB'));
    accentStrings.put("&Ucirc;", new Character('\u00DB'));
    escapeStrings.put("&Uuml;", new Character('\u00DC'));
    escapeStrings.put("&Yacute;", new Character('\u00DD'));
    escapeStrings.put("&THORN;", new Character('\u00DE'));
    escapeStrings.put("&szlig;", new Character('\u00DF'));
    escapeStrings.put("&agrave;", new Character('\u00E0'));
    accentStrings.put("&agrave;", new Character('\u00E0'));
    escapeStrings.put("&aacute;", new Character('\u00E1'));
    accentStrings.put("&aacute;", new Character('\u00E1'));
    escapeStrings.put("&acirc;", new Character('\u00E2'));
    accentStrings.put("&acirc;", new Character('\u00E2'));
    escapeStrings.put("&atilde;", new Character('\u00E3'));
    accentStrings.put("&atilde;", new Character('\u00E3'));
    escapeStrings.put("&auml;", new Character('\u00E4'));
    escapeStrings.put("&aring;", new Character('\u00E5'));
    escapeStrings.put("&aelig;", new Character('\u00E6'));
    escapeStrings.put("&ccedil;", new Character('\u00E7'));
    accentStrings.put("&ccedil;", new Character('\u00E7'));
    escapeStrings.put("&egrave;", new Character('\u00E8'));
    accentStrings.put("&egrave;", new Character('\u00E8'));
    escapeStrings.put("&eacute;", new Character('\u00E9'));
    accentStrings.put("&eacute;", new Character('\u00E9'));
    escapeStrings.put("&ecirc;", new Character('\u00EA'));
    accentStrings.put("&ecirc;", new Character('\u00EA'));
    escapeStrings.put("&euml;", new Character('\u00EB'));
    escapeStrings.put("&igrave;", new Character('\u00EC'));
    accentStrings.put("&igrave;", new Character('\u00EC'));
    escapeStrings.put("&iacute;", new Character('\u00ED'));
    accentStrings.put("&iacute;", new Character('\u00ED'));
    escapeStrings.put("&icirc;", new Character('\u00EE'));
    accentStrings.put("&icirc;", new Character('\u00EE'));
    escapeStrings.put("&iuml;", new Character('\u00EF'));
    escapeStrings.put("&eth;", new Character('\u00F0'));
    escapeStrings.put("&ntilde;", new Character('\u00F1'));
    accentStrings.put("&ntilde;", new Character('\u00F1'));
    escapeStrings.put("&ograve;", new Character('\u00F2'));
    accentStrings.put("&ograve;", new Character('\u00F2'));
    escapeStrings.put("&oacute;", new Character('\u00F3'));
    accentStrings.put("&oacute;", new Character('\u00F3'));
    escapeStrings.put("&ocirc;", new Character('\u00F4'));
    accentStrings.put("&ocirc;", new Character('\u00F4'));
    escapeStrings.put("&otilde;", new Character('\u00F5'));
    accentStrings.put("&otilde;", new Character('\u00F5'));
    escapeStrings.put("&ouml;", new Character('\u00F6'));
    escapeStrings.put("&divide;", new Character('\u00F7'));
    escapeStrings.put("&oslash;", new Character('\u00F8'));
    escapeStrings.put("&ugrave;", new Character('\u00F9'));
    accentStrings.put("&ugrave;", new Character('\u00F9'));
    escapeStrings.put("&uacute;", new Character('\u00FA'));
    accentStrings.put("&uacute;", new Character('\u00FA'));
    escapeStrings.put("&ucirc;", new Character('\u00FB'));
    accentStrings.put("&ucirc;", new Character('\u00FB'));
    escapeStrings.put("&uuml;", new Character('\u00FC'));
    escapeStrings.put("&yacute;", new Character('\u00FD'));
    escapeStrings.put("&thorn;", new Character('\u00FE'));
    escapeStrings.put("&yuml;", new Character('\u00FF'));
    escapeStrings.put("&fnof;", new Character('\u0192'));
    escapeStrings.put("&Alpha;", new Character('\u0391'));
    escapeStrings.put("&Beta;", new Character('\u0392'));
    escapeStrings.put("&Gamma;", new Character('\u0393'));
    escapeStrings.put("&Delta;", new Character('\u0394'));
    escapeStrings.put("&Epsilon;", new Character('\u0395'));
    escapeStrings.put("&Zeta;", new Character('\u0396'));
    escapeStrings.put("&Eta;", new Character('\u0397'));
    escapeStrings.put("&Theta;", new Character('\u0398'));
    escapeStrings.put("&Iota;", new Character('\u0399'));
    escapeStrings.put("&Kappa;", new Character('\u039A'));
    escapeStrings.put("&Lambda;", new Character('\u039B'));
    escapeStrings.put("&Mu;", new Character('\u039C'));
    escapeStrings.put("&Nu;", new Character('\u039D'));
    escapeStrings.put("&Xi;", new Character('\u039E'));
    escapeStrings.put("&Omicron;", new Character('\u039F'));
    escapeStrings.put("&Pi;", new Character('\u03A0'));
    escapeStrings.put("&Rho;", new Character('\u03A1'));
    escapeStrings.put("&Sigma;", new Character('\u03A3'));
    escapeStrings.put("&Tau;", new Character('\u03A4'));
    escapeStrings.put("&Upsilon;", new Character('\u03A5'));
    escapeStrings.put("&Phi;", new Character('\u03A6'));
    escapeStrings.put("&Chi;", new Character('\u03A7'));
    escapeStrings.put("&Psi;", new Character('\u03A8'));
    escapeStrings.put("&Omega;", new Character('\u03A9'));
    escapeStrings.put("&alpha;", new Character('\u03B1'));
    escapeStrings.put("&beta;", new Character('\u03B2'));
    escapeStrings.put("&gamma;", new Character('\u03B3'));
    escapeStrings.put("&delta;", new Character('\u03B4'));
    escapeStrings.put("&epsilon;", new Character('\u03B5'));
    escapeStrings.put("&zeta;", new Character('\u03B6'));
    escapeStrings.put("&eta;", new Character('\u03B7'));
    escapeStrings.put("&theta;", new Character('\u03B8'));
    escapeStrings.put("&iota;", new Character('\u03B9'));
    escapeStrings.put("&kappa;", new Character('\u03BA'));
    escapeStrings.put("&lambda;", new Character('\u03BB'));
    escapeStrings.put("&mu;", new Character('\u03BC'));
    escapeStrings.put("&nu;", new Character('\u03BD'));
    escapeStrings.put("&xi;", new Character('\u03BE'));
    escapeStrings.put("&omicron;", new Character('\u03BF'));
    escapeStrings.put("&pi;", new Character('\u03C0'));
    escapeStrings.put("&rho;", new Character('\u03C1'));
    escapeStrings.put("&sigmaf;", new Character('\u03C2'));
    escapeStrings.put("&sigma;", new Character('\u03C3'));
    escapeStrings.put("&tau;", new Character('\u03C4'));
    escapeStrings.put("&upsilon;", new Character('\u03C5'));
    escapeStrings.put("&phi;", new Character('\u03C6'));
    escapeStrings.put("&chi;", new Character('\u03C7'));
    escapeStrings.put("&psi;", new Character('\u03C8'));
    escapeStrings.put("&omega;", new Character('\u03C9'));
    escapeStrings.put("&thetasym;", new Character('\u03D1'));
    escapeStrings.put("&upsih;", new Character('\u03D2'));
    escapeStrings.put("&piv;", new Character('\u03D6'));
    escapeStrings.put("&bull;", new Character('\u2022'));
    escapeStrings.put("&hellip;", new Character('\u2026'));
    escapeStrings.put("&prime;", new Character('\u2032'));
    escapeStrings.put("&Prime;", new Character('\u2033'));
    escapeStrings.put("&oline;", new Character('\u203E'));
    escapeStrings.put("&frasl;", new Character('\u2044'));
    escapeStrings.put("&weierp;", new Character('\u2118'));
    escapeStrings.put("&image;", new Character('\u2111'));
    escapeStrings.put("&real;", new Character('\u211C'));
    escapeStrings.put("&trade;", new Character('\u2122'));
    escapeStrings.put("&alefsym;", new Character('\u2135'));
    escapeStrings.put("&larr;", new Character('\u2190'));
    escapeStrings.put("&uarr;", new Character('\u2191'));
    escapeStrings.put("&rarr;", new Character('\u2192'));
    escapeStrings.put("&darr;", new Character('\u2193'));
    escapeStrings.put("&harr;", new Character('\u2194'));
    escapeStrings.put("&crarr;", new Character('\u21B5'));
    escapeStrings.put("&lArr;", new Character('\u21D0'));
    escapeStrings.put("&uArr;", new Character('\u21D1'));
    escapeStrings.put("&rArr;", new Character('\u21D2'));
    escapeStrings.put("&dArr;", new Character('\u21D3'));
    escapeStrings.put("&hArr;", new Character('\u21D4'));
    escapeStrings.put("&forall;", new Character('\u2200'));
    escapeStrings.put("&part;", new Character('\u2202'));
    escapeStrings.put("&exist;", new Character('\u2203'));
    escapeStrings.put("&empty;", new Character('\u2205'));
    escapeStrings.put("&nabla;", new Character('\u2207'));
    escapeStrings.put("&isin;", new Character('\u2208'));
    escapeStrings.put("&notin;", new Character('\u2209'));
    escapeStrings.put("&ni;", new Character('\u220B'));
    escapeStrings.put("&prod;", new Character('\u220F'));
    escapeStrings.put("&sum;", new Character('\u2211'));
    escapeStrings.put("&minus;", new Character('\u2212'));
    escapeStrings.put("&lowast;", new Character('\u2217'));
    escapeStrings.put("&radic;", new Character('\u221A'));
    escapeStrings.put("&prop;", new Character('\u221D'));
    escapeStrings.put("&infin;", new Character('\u221E'));
    escapeStrings.put("&ang;", new Character('\u2220'));
    escapeStrings.put("&and;", new Character('\u2227'));
    escapeStrings.put("&or;", new Character('\u2228'));
    escapeStrings.put("&cap;", new Character('\u2229'));
    escapeStrings.put("&cup;", new Character('\u222A'));
    escapeStrings.put("&int;", new Character('\u222B'));
    escapeStrings.put("&there4;", new Character('\u2234'));
    escapeStrings.put("&sim;", new Character('\u223C'));
    escapeStrings.put("&cong;", new Character('\u2245'));
    escapeStrings.put("&asymp;", new Character('\u2248'));
    escapeStrings.put("&ne;", new Character('\u2260'));
    escapeStrings.put("&equiv;", new Character('\u2261'));
    escapeStrings.put("&le;", new Character('\u2264'));
    escapeStrings.put("&ge;", new Character('\u2265'));
    escapeStrings.put("&sub;", new Character('\u2282'));
    escapeStrings.put("&sup;", new Character('\u2283'));
    escapeStrings.put("&nsub;", new Character('\u2284'));
    escapeStrings.put("&sube;", new Character('\u2286'));
    escapeStrings.put("&supe;", new Character('\u2287'));
    escapeStrings.put("&oplus;", new Character('\u2295'));
    escapeStrings.put("&otimes;", new Character('\u2297'));
    escapeStrings.put("&perp;", new Character('\u22A5'));
    escapeStrings.put("&sdot;", new Character('\u22C5'));
    escapeStrings.put("&lceil;", new Character('\u2308'));
    escapeStrings.put("&rceil;", new Character('\u2309'));
    escapeStrings.put("&lfloor;", new Character('\u230A'));
    escapeStrings.put("&rfloor;", new Character('\u230B'));
    escapeStrings.put("&lang;", new Character('\u2329'));
    escapeStrings.put("&rang;", new Character('\u232A'));
    escapeStrings.put("&loz;", new Character('\u25CA'));
    escapeStrings.put("&spades;", new Character('\u2660'));
    escapeStrings.put("&clubs;", new Character('\u2663'));
    escapeStrings.put("&hearts;", new Character('\u2665'));
    escapeStrings.put("&diams;", new Character('\u2666'));
    escapeStrings.put("&quot;", new Character('\u0022'));
    escapeStrings.put("&lt;", new Character('\u003C'));
    escapeStrings.put("&gt;", new Character('\u003E'));
    escapeStrings.put("&OElig;", new Character('\u0152'));
    escapeStrings.put("&oelig;", new Character('\u0153'));
    escapeStrings.put("&Scaron;", new Character('\u0160'));
    escapeStrings.put("&scaron;", new Character('\u0161'));
    escapeStrings.put("&Yuml;", new Character('\u0178'));
    escapeStrings.put("&circ;", new Character('\u02C6'));
    escapeStrings.put("&tilde;", new Character('\u02DC'));
    escapeStrings.put("&ensp;", new Character('\u2002'));
    escapeStrings.put("&emsp;", new Character('\u2003'));
    escapeStrings.put("&thinsp;", new Character('\u2009'));
    escapeStrings.put("&zwnj;", new Character('\u200C'));
    escapeStrings.put("&zwj;", new Character('\u200D'));
    escapeStrings.put("&lrm;", new Character('\u200E'));
    escapeStrings.put("&rlm;", new Character('\u200F'));
    escapeStrings.put("&ndash;", new Character('\u2013'));
    escapeStrings.put("&mdash;", new Character('\u2014'));
    escapeStrings.put("&lsquo;", new Character('\u2018'));
    escapeStrings.put("&rsquo;", new Character('\u2019'));
    escapeStrings.put("&sbquo;", new Character('\u201A'));
    escapeStrings.put("&ldquo;", new Character('\u201C'));
    escapeStrings.put("&rdquo;", new Character('\u201D'));
    escapeStrings.put("&bdquo;", new Character('\u201E'));
    escapeStrings.put("&dagger;", new Character('\u2020'));
    escapeStrings.put("&Dagger;", new Character('\u2021'));
    escapeStrings.put("&permil;", new Character('\u2030'));
    escapeStrings.put("&lsaquo;", new Character('\u2039'));
    escapeStrings.put("&rsaquo;", new Character('\u203A'));
    escapeStrings.put("&euro;", new Character('\u20AC'));
  }
  public static void main(String[] args) {
    // String[] similarity = fileSimilar(new File(""), "FluxoCx");

  }

  public static List<String> regexTest(Pattern pattern, String str) {
    Matcher match = pattern.matcher(str);
    List<String> ret = new ArrayList<String>();

    while (match.find()) {
      ret.add(match.group());
    }

    return ret;
  }

  /**
   * Gets a specific line of a text (String)
   * @param content text
   * @param line line to get
   * @return the specified line
   */
  public static String getLine(String content, int line) {
    if (content == null) {
      return null;
    }

    String[] contentSplit = content.replace("\r\n", "\n").split("\n");

    if (contentSplit.length < line) {
      return null;
    }
    return contentSplit[line - 1];
  }

  /**
   * Gets the first group of a regex 
   * @param pattern Pattern
   * @param str String to find
   * @return the matching group
   */
  public static String regexFindFirst(String pattern, String str) {
    return regexFindFirst(Pattern.compile(pattern), str, 1);
  }

  public static String regexFindFirst(String pattern, String str, int group) {
    return regexFindFirst(Pattern.compile(pattern), str, group);
  }

  public static String regexFindFirst(Pattern pattern, String str, int group) {
    Matcher match = pattern.matcher(str);

    if (match.find()) {
      return match.group(group);
    }

    return null;
  }

  public static List<String> linesThatContains(List<String> a, String[] strArr) {
    List<String> retorno = new ArrayList<String>();

    line: for (String line : a) {
      for (String str : strArr) {
        if (line.contains(str)) {
          retorno.add(line);
          continue line;
        }
      }
    }
    return retorno;
  }

  public static List<String> linesThatContains(List<String> a, String str) {
    return linesThatContains(a, new String[] {str});
  }

  public static List<String> deleteIfContains(List<String> a, String str) {
    return deleteIfContains(a, new String[] {str});
  }

  public static List<String> deleteIfContains(List<String> a, String[] strArr) {
    List<String> retorno = new ArrayList<String>();

    for (String line : a) {
      boolean contains = false;
      for (String str : strArr) {
        if (line.contains(str)) {
          contains = true;
          break;
        }
      }

      if (!contains) {
        retorno.add(line);
      }
    }
    return retorno;
  }

  public static List<String> deleteIfNotContains(List<String> a, String str) {
    List<String> retorno = new ArrayList<String>();

    for (String line : a) {
      if (line.contains(str)) {
        retorno.add(line);
      }
    }
    return retorno;
  }

  public static List<String> mixStringLists(List<String> a, List<String> b) {
    String str;
    List<String> retorno = new ArrayList<String>();

    for (Iterator<String> localIterator = a.iterator(); localIterator.hasNext();) {
      str = localIterator.next();
      retorno.add(str);
    }
    for (Iterator<String> localIterator = b.iterator(); localIterator.hasNext();) {
      str = localIterator.next();
      retorno.add(str);
    }

    return retorno;
  }

  @SuppressWarnings("unchecked")
  public static <T> Collection<T> mixLists(Collection<?> a, Collection<?> b) {
    T str;
    Collection<T> retorno = new ArrayList<T>();

    for (Iterator<?> localIterator = a.iterator(); localIterator.hasNext();) {
      str = (T) localIterator.next();
      retorno.add(str);
    }
    for (Iterator<?> localIterator = b.iterator(); localIterator.hasNext();) {
      str = (T) localIterator.next();
      retorno.add(str);
    }

    return retorno;
  }

  public static String removeAccents(String _s) {
    
    String s = new String(_s);
    s = s.replace((char) 0xE1, 'a');
    s = s.replace((char) 0xE3, 'a');

    s = s.replace((char) 0xE9, 'e');

    s = s.replace((char) 0xED, 'i');

    s = s.replace((char) 0xF3, 'o');

    s = s.replace((char) 0xFA, 'u');

    s = Normalizer.normalize(s, Normalizer.Form.NFD);
    s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

    return s;
  }

  public static List<List<String>> split(List<String> list, int count) {
    throw new UnsupportedOperationException("Not implemented yet.");
  }

  public static Set<String> fixList(FixType type, Collection<String> list) {
    Comparator<String> comparator = null;
    if (type == FixType.DELETEREPEATED) {
      comparator = new Unique();
    }
    if (type == FixType.ALPHABETICAL) {
      comparator = new Sort();
    }
    if (type == FixType.ALPHABETICALDELETEREPEATED) {
      comparator = new UniqueSort();
    }

    Set<String> retorno = new TreeSet<String>(comparator);

    for (String str : list) {
      retorno.add(str);
    }

    return retorno;
  }

  public static boolean saveToFile(int type, Collection<?> list, File file) {
    BufferedWriter out = null;
    try {
      if (type == APPEND) {
        out = new BufferedWriter(new FileWriter(file, true));
      } else {
        out = new BufferedWriter(new FileWriter(file));
      }

      for (Object str : list) {
        out.write(str + CARRIAGE_RETURN);
      }
      out.close();
      return true;
    } catch (Exception e) {
      e.printStackTrace();
      return false;
    } finally {
      try {
        if (out != null) {
          out.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static boolean saveToFile(int type, Collection<?> list, String file) {
    return saveToFile(type, list, new File(file));
  }

  public static List<String> getContentListSplit(String content, String delimiter) {
    List<String> retorno = new ArrayList<String>();

    for (String str : content.split(delimiter)) {
      retorno.add(str);
    }

    return retorno;
  }

  public static List<String> getContentListSplit(InputStream is, String delimiter)
      throws IOException {
    return getContentListSplit(getContent(is), delimiter);
  }

  public static List<String> getContentListSplit(File file, String delimiter) throws IOException {
    return getContentListSplit(getContent(file), delimiter);
  }

  /**
   * Split string content into list
   * @param content String content
   * @return list 
   */
  public static List<String> asListLines(String content) {
    List<String> retorno = new ArrayList<String>();
    content = content.replace(CARRIAGE_RETURN, RETURN);
    content = content.replace(RETURN, CARRIAGE_RETURN);
    for (String str : content.split(CARRIAGE_RETURN)) {
      retorno.add(str);
    }
    return retorno;
  }
  
  /**
   * Split string content into list, ignoring matches of the pattern
   * @param content String content
   * @param ignorePattern Pattern to ignore
   * @return list 
   */
  public static List<String> asListLinesIgnore(String content, Pattern ignorePattern) {
    List<String> retorno = new ArrayList<String>();
    content = content.replace(CARRIAGE_RETURN, RETURN);
    content = content.replace(RETURN, CARRIAGE_RETURN);
    for (String str : content.split(CARRIAGE_RETURN)) {
      if (!ignorePattern.matcher(str).matches()) {
        retorno.add(str);
      }
    }
    return retorno;
  }


  public static List<String> getContentLines(File file, String codePage) throws IOException,
      UnsupportedEncodingException {
    String content = getContent(file, codePage);
    if (content.contains(CARRIAGE_RETURN)) {
      content = content.replace(CARRIAGE_RETURN, RETURN);
    }

    List<String> mutableList = new ArrayList<String>();
    for (String str : content.split(RETURN)) {
      mutableList.add(str);
    }
    return mutableList;
  }

  public static List<String> getContentLines(InputStreamReader reader) throws IOException {
    String content = getContent(reader);
    if (content.contains(CARRIAGE_RETURN)) {
      content = content.replace(CARRIAGE_RETURN, RETURN);
    }

    return Arrays.asList(content.split(RETURN));
  }

  public static List<String> getContentLines(InputStream is) throws IOException {
    String content = getContent(is);
    if (content.contains(CARRIAGE_RETURN)) {
      content = content.replace(CARRIAGE_RETURN, RETURN);
    }

    return Arrays.asList(content.split(RETURN));
  }

  public static Map<String, String> getContentMap(File file) throws IOException {
    return getContentMap(file, ":");
  }

  public static Map<String, String> getContentMapSneaky(File file, String separator) {
    try {
      return getContentMap(file, separator);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static Map<String, String> getContentMapEscaped(File file, String separator)
      throws IOException {
    List<String> content = getContentLines(file);
    Map<String, String> map = new LinkedHashMap<String, String>();

    for (String line : content) {
      if (line.trim().length() > 0 && line.contains(separator)) {
        String[] keyValue = line.split(separator);
        if (keyValue.length < 2) {
          continue;
        }

        if (line.contains("#escapedtwodots#")) {
          map.put(keyValue[0].replace("#escapedtwodots#", ":"),
              keyValue[1].replace("#escapedtwodots#", ":"));
        } else {
          map.put(keyValue[0], keyValue[1]);
        }

      }
    }

    return map;
  }

  /**
   * Get content of a file as a Map&lt;String, String&gt;, using separator to split values
   * @param file File to get content
   * @param separator The separator
   * @return The map with the values
   * @throws IOException I/O Error
   */
  public static Map<String, String> getContentMap(File file, String separator) throws IOException {
    List<String> content = getContentLines(file);
    Map<String, String> map = new LinkedHashMap<String, String>();
      
    for (String line : content) {
      String[] spl = line.split(separator);
      if (line.trim().length() > 0) {
        map.put(spl[0], (spl.length > 1 ? spl[1] : ""));
      }
    }
    
    return map;
  }

  /**
   * Save map to file
   * @param map Map to save
   * @param file File to save
   * @throws IOException I/O error
   */
  public static void saveContentMap(Map<String, String> map, File file) throws IOException {

    FileWriter out = new FileWriter(file);
    for (String key : map.keySet()) {
      if (map.get(key) != null) {
        out.write(key.replace(":", "#escapedtwodots#") + ":"
            + map.get(key).replace(":", "#escapedtwodots#") + "\r\n");
      }
    }
    out.close();
  }

  public static List<String> getContentLinesSneaky(File file) {
    try {
      return getContentLines(file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static List<String> getContentLines(File file) throws IOException {
    return getContentLines(file, Charset.defaultCharset().displayName());
  }



  public static boolean fileHasText(File file, String text) {
    if (file.exists()) {
      InputStream in;
      try {
        in = new BufferedInputStream(new FileInputStream(file));
        return MyStreamUtils.streamHasText(in, text);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }

    }
    return false;
  }

  public static String getContent(InputStreamReader in) throws IOException {

    StringBuffer sb = new StringBuffer();
    try {

      int data = in.read();
      while (data != -1) {
        char theChar = (char) data;
        sb.append(theChar);
        data = in.read();
      }

    } catch (IOException e) {
      throw e;
    } finally {
      try {
        in.close();
      } catch (IOException e) {
        throw e;
      }
    }

    return sb.toString();
  }

  public static String getContent(InputStream in) throws IOException {
    final byte[] readBuffer = new byte[8192];

    StringBuffer sb = new StringBuffer();
    try {
      if (in.available() > 0) {
        int bytesRead = 0;
        while ((bytesRead = in.read(readBuffer)) != -1) {
          sb.append(new String(readBuffer, 0, bytesRead));
        }
      }
    } catch (IOException e) {
      throw e;
    } finally {
      try {
        in.close();
      } catch (IOException e) {
        throw e;
      }
    }

    return sb.toString();
  }

  public static String getContent(File file, String codepage) throws IOException,
      UnsupportedEncodingException {
    return new String(getContent(file).getBytes(), codepage);
  }

  public static String getResourceContent(Class<?> clazz, String resource) {
    URL url = clazz.getResource(resource);

    if (url == null) {
      return null;
    }

    try {
      String conteudo = getContent(url.openStream());
      return conteudo;
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public static byte[] getContentBytes(File file) throws IOException {
    ByteArrayOutputStream ous = null;
    InputStream ios = null;
    try {
      byte[] buffer = new byte[4096];
      ous = new ByteArrayOutputStream();
      ios = new FileInputStream(file);
      int read = 0;
      while ((read = ios.read(buffer)) != -1) {
        ous.write(buffer, 0, read);
      }
    } finally {
      try {
        if (ous != null)
          ous.close();
      } catch (IOException e) {
      }

      try {
        if (ios != null)
          ios.close();
      } catch (IOException e) {
      }
    }
    return ous.toByteArray();
  }

  public static String getContent(File file) throws IOException {
    InputStream in;
    try {
      in = new BufferedInputStream(new FileInputStream(file));
      return getContent(in);
    } catch (IOException e) {
      throw e;
    }
  }

  public static byte[] getUrlContentBytes(String stringUrl) {
    try {
      URL url = new URL(stringUrl);
      return MyStreamUtils.readContentBytes(url.openStream());
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  /**
   * Returns content for the given URL
   * @param stringUrl URL
   * @return Response content
   */
  public static String getContent(String stringUrl) {
    if (stringUrl.equalsIgnoreCase("clipboard")) {
      try {
        return getFromClipboard();
      } catch (Exception e) {
        //it's ok.
      }
    }
    return getContent(stringUrl, null);
  }
  
  /**
   * Returns content for the given URL
   * @param stringUrl URL
   * @param requestProperties Properties
   * @return Response content
   */
  public static String getContent(String stringUrl, Map<String, String> requestProperties) {
    try {
      URL url = new URL(stringUrl);
      URLConnection conn = url.openConnection();
      
      if (requestProperties != null) {
        for (Entry<String, String> entry : requestProperties.entrySet()) {
          conn.setRequestProperty(entry.getKey(), entry.getValue());
        }
      }
      
      InputStream is = conn.getInputStream();
      if ("gzip".equals(conn.getContentEncoding())) {
         is = new GZIPInputStream(is);
      }

      return MyStreamUtils.readContent(is);
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }



  @Deprecated
  public static boolean blank(String value) {
    return isBlank(value);
  }

  public static boolean isBlank(String value) {
    int ii = 0;
    for (int ll = (value == null) ? 0 : value.length(); ii < ll; ++ii) {
      if (!(Character.isWhitespace(value.charAt(ii)))) {
        return false;
      }
    }
    return true;
  }

  public static String trim(String value) {
    return ((value == null) ? null : value.trim());
  }

  public static String deNull(String value) {
    return ((value == null) ? "" : value);
  }

  public static String truncate(String s, int maxLength) {
    return truncate(s, maxLength, "");
  }

  public static String truncate(String s, int maxLength, String append) {
    if ((s == null) || (s.length() <= maxLength)) {
      return s;
    }
    return s.substring(0, maxLength - append.length()) + append;
  }

  public static String capitalize(String s) {
    if (isBlank(s)) {
      return s;
    }
    char c = s.charAt(0);
    if (Character.isUpperCase(c)) {
      return s;
    }
    return String.valueOf(Character.toUpperCase(c)) + s.substring(1);
  }

  public static String toUSLowerCase(String s) {
    return ((isBlank(s)) ? s : s.toLowerCase(Locale.US));
  }

  public static String toUSUpperCase(String s) {
    return ((isBlank(s)) ? s : s.toUpperCase(Locale.US));
  }

  public static String sanitize(String source, CharacterValidator validator) {
    if (source == null) {
      return null;
    }
    int nn = source.length();
    StringBuilder buf = new StringBuilder(nn);
    for (int ii = 0; ii < nn; ++ii) {
      char c = source.charAt(ii);
      if (validator.isValid(c)) {
        buf.append(c);
      }
    }
    return buf.toString();
  }

  public static String replace(String source, String before, String after) {
    int pos = source.indexOf(before);
    if (pos == -1) {
      return source;
    }

    StringBuilder sb = new StringBuilder(source.length() + 32);

    int blength = before.length();
    int start = 0;
    while (pos != -1) {
      sb.append(source.substring(start, pos));
      sb.append(after);
      start = pos + blength;
      pos = source.indexOf(before, start);
    }
    sb.append(source.substring(start));

    return sb.toString();
  }

  public static String pad(String value, int width) {
    if (width <= 0) {
      throw new IllegalArgumentException("Pad width must be greater than zero.");
    }
    if (value.length() >= width) {
      return value;
    }
    return value + spaces(width - value.length());
  }

  public static String leftpad(String value, int width) {
    return prepad(value, width);
  }
  
  public static String prepad(String value, int width) {
    return prepad(value, width, ' ');
  }
  
  public static String prepad(String value, int width, char append) {
    if (width <= 0) {
      throw new IllegalArgumentException("Pad width must be greater than zero.");
    }
    if (value.length() >= width) {
      return value;
    }
    return fill(append, width - value.length()) + value;
  }

  public static String spaces(int count) {
    return fill(' ', count);
  }

  public static String fill(char c, int count) {
    if (count < 0) {
      count = 0;
    }

    char[] sameChars = new char[count];
    Arrays.fill(sameChars, c);
    return new String(sameChars);
  }

  public static boolean isNumber(String value) {
    try {
      Double.parseDouble(value);
      return true;
    } catch (NumberFormatException localNumberFormatException) {
    }
    return false;
  }

  public static boolean isInteger(String value) {
    try {
      Integer.parseInt(value);
      return true;
    } catch (NumberFormatException localNumberFormatException) {
    }
    return false;
  }

  public static String format(float value) {
    return _ffmt.format(value);
  }

  public static String format(double value) {
    return _ffmt.format(value);
  }

  public static String coordsToString(int x, int y) {
    StringBuilder buf = new StringBuilder();
    coordsToString(buf, x, y);
    return buf.toString();
  }

  public static void coordsToString(StringBuilder buf, int x, int y) {
    if (x >= 0) {
      buf.append("+");
    }
    buf.append(x);
    if (y >= 0) {
      buf.append("+");
    }
    buf.append(y);
  }

  public static String encode(String s) {
    try {
      return ((s != null) ? URLEncoder.encode(s, "UTF-8") : null);
    } catch (UnsupportedEncodingException uee) {
      throw new RuntimeException("UTF-8 is unknown in this Java.");
    }
  }

  public static String decode(String s) {
    try {
      return ((s != null) ? URLDecoder.decode(s, "UTF-8") : null);
    } catch (UnsupportedEncodingException uee) {
      throw new RuntimeException("UTF-8 is unknown in this Java.");
    }
  }

  public static String hexlate(byte[] bytes, int count) {
    if (bytes == null) {
      return "";
    }

    count = Math.min(count, bytes.length);
    char[] chars = new char[count * 2];

    for (int i = 0; i < count; ++i) {
      int val = bytes[i];
      if (val < 0) {
        val += 256;
      }
      chars[(2 * i)] = XLATE.charAt(val / 16);
      chars[(2 * i + 1)] = XLATE.charAt(val % 16);
    }

    return new String(chars);
  }

  public static String hexlate(byte[] bytes) {
    return ((bytes == null) ? "" : hexlate(bytes, bytes.length));
  }

  public static byte[] unhexlate(String hex) {
    if ((hex == null) || (hex.length() % 2 != 0)) {
      return null;
    }

    hex = hex.toLowerCase();
    byte[] data = new byte[hex.length() / 2];
    for (int ii = 0; ii < hex.length(); ii += 2) {
      int value = (byte) (XLATE.indexOf(hex.charAt(ii)) << 4);
      value += XLATE.indexOf(hex.charAt(ii + 1));

      data[(ii / 2)] = (byte) value;
    }

    return data;
  }

  public static String md5hex(String source) {
    return digest("MD5", source);
  }

  public static String sha1hex(String source) {
    return digest("SHA-1", source);
  }

  public static byte[] parseByteArray(String source) {
    StringTokenizer tok = new StringTokenizer(source, ",");
    byte[] vals = new byte[tok.countTokens()];
    for (int i = 0; tok.hasMoreTokens(); ++i) {
      try {
        vals[i] = Byte.parseByte(tok.nextToken().trim());
      } catch (NumberFormatException nfe) {
        return null;
      }
    }
    return vals;
  }

  public static short[] parseShortArray(String source) {
    StringTokenizer tok = new StringTokenizer(source, ",");
    short[] vals = new short[tok.countTokens()];
    for (int i = 0; tok.hasMoreTokens(); ++i) {
      try {
        vals[i] = Short.parseShort(tok.nextToken().trim());
      } catch (NumberFormatException nfe) {
        return null;
      }
    }
    return vals;
  }

  public static int[] parseIntArray(String source) {
    StringTokenizer tok = new StringTokenizer(source, ",");
    int[] vals = new int[tok.countTokens()];
    for (int i = 0; tok.hasMoreTokens(); ++i) {
      try {
        vals[i] = Integer.parseInt(tok.nextToken().trim());
      } catch (NumberFormatException nfe) {
        return null;
      }
    }
    return vals;
  }

  public static long[] parseLongArray(String source) {
    StringTokenizer tok = new StringTokenizer(source, ",");
    long[] vals = new long[tok.countTokens()];
    for (int i = 0; tok.hasMoreTokens(); ++i) {
      try {
        vals[i] = Long.parseLong(tok.nextToken().trim());
      } catch (NumberFormatException nfe) {
        return null;
      }
    }
    return vals;
  }

  public static float[] parseFloatArray(String source) {
    StringTokenizer tok = new StringTokenizer(source, ",");
    float[] vals = new float[tok.countTokens()];
    for (int i = 0; tok.hasMoreTokens(); ++i) {
      try {
        vals[i] = Float.parseFloat(tok.nextToken().trim());
      } catch (NumberFormatException nfe) {
        return null;
      }
    }
    return vals;
  }

  public static double[] parseDoubleArray(String source) {
    StringTokenizer tok = new StringTokenizer(source, ",");
    double[] vals = new double[tok.countTokens()];
    for (int i = 0; tok.hasMoreTokens(); ++i) {
      try {
        vals[i] = Double.parseDouble(tok.nextToken().trim());
      } catch (NumberFormatException nfe) {
        return null;
      }
    }
    return vals;
  }

  public static String[] parseStringArray(String source) {
    return parseStringArray(source, false);
  }

  public static String[] parseStringArray(String source, boolean intern) {
    int tcount = 0;
    int tpos = -1;
    int tstart = 0;

    if (source.length() == 0) {
      return new String[0];
    }

    source = replace(source, ",,", "%COMMA%");

    while ((tpos = source.indexOf(",", tpos + 1)) != -1) {
      ++tcount;
    }

    String[] tokens = new String[tcount + 1];
    tpos = -1;
    tcount = 0;

    while ((tpos = source.indexOf(",", tpos + 1)) != -1) {
      tokens[tcount] = source.substring(tstart, tpos);
      tokens[tcount] = replace(tokens[tcount].trim(), "%COMMA%", ",");
      if (intern) {
        tokens[tcount] = tokens[tcount].intern();
      }
      tstart = tpos + 1;
      ++tcount;
    }

    tokens[tcount] = source.substring(tstart);
    tokens[tcount] = replace(tokens[tcount].trim(), "%COMMA%", ",");

    return tokens;
  }

  public static String join(Object[] values) {
    return join(values, false);
  }

  public static String join(Object[] values, boolean escape) {
    return join(values, ", ", escape);
  }

  public static String join(Object[] values, String separator) {
    return join(values, separator, false);
  }

  public static String joinEscaped(String[] values) {
    return join(values, true);
  }

  public static String[] split(String source, String sep) {
    if (isBlank(source)) {
      return new String[0];
    }

    int tcount = 0;
    int tpos = -1;
    int tstart = 0;

    while ((tpos = source.indexOf(sep, tpos + 1)) != -1) {
      ++tcount;
    }

    String[] tokens = new String[tcount + 1];
    tpos = -1;
    tcount = 0;

    while ((tpos = source.indexOf(sep, tpos + 1)) != -1) {
      tokens[tcount] = source.substring(tstart, tpos);
      tstart = tpos + 1;
      ++tcount;
    }

    tokens[tcount] = source.substring(tstart);

    return tokens;
  }

  public static String toMatrixString(int[] values, int colCount, int fieldWidth) {
    StringBuilder buf = new StringBuilder();
    StringBuilder valbuf = new StringBuilder();

    for (int i = 0; i < values.length; ++i) {
      valbuf.setLength(0);
      valbuf.append(values[i]);

      int spaces = fieldWidth - valbuf.length();
      for (int s = 0; s < spaces; ++s) {
        buf.append(" ");
      }

      buf.append(valbuf);

      if ((i % colCount != colCount - 1) || (i == values.length - 1)) {
        continue;
      }
      buf.append(lineSeparator);
    }

    return buf.toString();
  }

  public static String intervalToString(long millis) {
    StringBuilder buf = new StringBuilder();
    boolean started = false;

    long days = millis / 86400000L;
    if (days != 0L) {
      buf.append(days).append("d ");
      started = true;
    }

    long hours = millis / 3600000L % 24L;
    if ((started) || (hours != 0L)) {
      buf.append(hours).append("h ");
      started = true;
    }

    long minutes = millis / 60000L % 60L;
    if ((started) || (minutes != 0L)) {
      buf.append(minutes).append("m ");
      started = true;
    }

    long seconds = millis / 1000L % 60L;
    if ((started) || (seconds != 0L)) {
      buf.append(seconds).append("s ");
      started = true;
    }

    long restMillis = millis % 1000L;
    if ((started) || restMillis > 0) {
      buf.append(restMillis).append("ms");
    }

    return buf.toString().trim();
  }

  public static String shortClassName(Object object) {
    return ((object == null) ? "null" : shortClassName(object.getClass()));
  }

  public static String shortClassName(Class<?> clazz) {
    return shortClassName(clazz.getName());
  }

  public static String shortClassName(String name) {
    int didx = name.lastIndexOf(".");
    if (didx == -1) {
      return name;
    }
    didx = name.lastIndexOf(".", didx - 1);
    if (didx == -1) {
      return name;
    }
    return name.substring(didx + 1);
  }

  public static String unStudlyName(String name) {
    boolean seenLower = false;
    StringBuilder nname = new StringBuilder();
    int nlen = name.length();
    for (int i = 0; i < nlen; ++i) {
      char c = name.charAt(i);

      if (Character.isUpperCase(c)) {
        if (seenLower) {
          nname.append("_");
        }
        seenLower = false;
        nname.append(c);
      } else {
        seenLower = true;
        nname.append(Character.toUpperCase(c));
      }
    }
    return nname.toString();
  }

  public static String wordWrap(String str, int width) {
    int size = str.length();
    StringBuilder buf = new StringBuilder(size + size / width);
    int lastidx = 0;
    while (lastidx < size) {
      if (lastidx + width >= size) {
        buf.append(str.substring(lastidx));
        break;
      }
      int lastws = lastidx;
      int ii = lastidx;
      for (int ll = lastidx + width; ii < ll; ++ii) {
        char c = str.charAt(ii);
        if (c == '\n') {
          buf.append(str.substring(lastidx, ii + 1));
          lastidx = ii + 1;
          break;
        }
        if (Character.isWhitespace(c)) {
          lastws = ii;
        }
      }
      if (lastws == lastidx) {
        buf.append(str.substring(lastidx, lastidx + width)).append(lineSeparator);
        lastidx += width;
      } else if (lastws > lastidx) {
        buf.append(str.substring(lastidx, lastws)).append(lineSeparator);
        lastidx = lastws + 1;
      }
    }
    return buf.toString();
  }

  protected static String join(Object[] values, String separator, boolean escape) {
    StringBuilder buf = new StringBuilder();
    int vlength = values.length;
    for (int i = 0; i < vlength; ++i) {
      if (i > 0) {
        buf.append(separator);
      }
      String value = (values[i] == null) ? "" : values[i].toString();
      buf.append((escape) ? replace(value, ",", ",,") : value);
    }
    return buf.toString();
  }

  protected static String digest(String codec, String source) {
    try {
      MessageDigest digest = MessageDigest.getInstance(codec);
      return hexlate(digest.digest(source.getBytes()));
    } catch (NoSuchAlgorithmException nsae) {
      throw new RuntimeException(codec + " codec not available");
    }
  }

  public static abstract interface CharacterValidator {

    public abstract boolean isValid(char paramChar);
  }

  public static class Formatter {

    public String toString(Object object) {
      return ((object == null) ? "null" : object.toString());
    }

    public String getOpenBox() {
      return "(";
    }

    public String getCloseBox() {
      return ")";
    }
  }

  public static class Sort implements Comparator<String> {

    public int compare(String arg0, String arg1) {
      if (arg0.equals(arg1)) {
        return 1;
      }
      return arg0.compareTo(arg1);
    }
  }

  static class Unique implements Comparator<String> {

    public int compare(String arg0, String arg1) {
      if (arg0.equals(arg1)) {
        return 0;
      }
      return 1;
    }
  }

  public static class UniqueSort implements Comparator<String> {

    public int compare(String arg0, String arg1) {
      return arg0.compareTo(arg1);
    }
  }

  public static String cleanCodeText(String text) {

    return trimClean(text);

  }

  public static void doubleOutput(String text, FileWriter fw) {
    doubleOutput(text, fw, System.out);
  }

  public static void doubleOutput(String text, FileWriter fw, PrintStream out) {
    out.println(text);
    // out.flush();
    // out.flush();

    try {
      if (fw != null) {
        fw.write(text + CARRIAGE_RETURN);
        fw.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static File chooseFile() {
    return chooseFile(new File("."));
  }

  public static File chooseFile(String title) {
    return chooseFile(new File("c:\\tmp"), title);
  }

  public static File chooseFile(File dir) {
    return chooseFile(dir, "Selecione um arquivo para salvar");
  }

  public static File chooseFile(File dir, String title) {
    return chooseFile(dir, "Selecione um arquivo para salvar", JFileChooser.FILES_ONLY);
  }

  public static File chooseFile(File dir, String title, int selectionMode) {
    JFileChooser chooser = new JFileChooser();
    chooser.setCurrentDirectory(dir);
    chooser.setDialogTitle(title);
    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    chooser.setDialogType(JFileChooser.SAVE_DIALOG);

    chooser.showSaveDialog(null);
    File saveTo = chooser.getSelectedFile();

    return saveTo;
  }

  public static String fileHash(File f) throws IOException, NoSuchAlgorithmException {

    MessageDigest md = MessageDigest.getInstance("MD5");
    String digest = getDigest(new FileInputStream(f), md, 2048);

    return digest;

  }

  public static String getDigest(InputStream is, MessageDigest md, int byteArraySize)
      throws NoSuchAlgorithmException, IOException {
    md.reset();
    byte[] bytes = new byte[byteArraySize];
    int numBytes;
    while ((numBytes = is.read(bytes)) != -1) {
      md.update(bytes, 0, numBytes);
    }
    byte[] digest = md.digest();
    String result = new String(Hex.encodeHex(digest));

    is.close();
    return result;
  }

  public static String listToString(List<String> list) {
    String ret = "";

    for (String str : list) {
      ret = ret + str + CARRIAGE_RETURN;
    }

    return ret;
  }

  public static void writeSpecificLine(File file, int lineNumber, String content) {
    try {

      BufferedReader lnr = new BufferedReader(new FileReader(file));

      StringBuffer sb = new StringBuffer();

      int lineCursor = 0;
      while (lnr.ready()) {
        if (lineCursor == lineNumber) {
          sb.append(content + CARRIAGE_RETURN);
        }

        String linha = lnr.readLine();
        sb.append(linha + CARRIAGE_RETURN);

        lineCursor++;
      }
      lnr.close();

      FileWriter fw = new FileWriter(file);
      fw.write(sb.toString());
      fw.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /*
   * Removed - use https://github.com/rrice/java-string-similarity public static double
   * similarScore(String str1, String str2) { SimilarityStrategy strategy = new
   * JaroWinklerStrategy(); StringSimilarityService service = new StringSimilarityServiceImpl(
   * strategy);
   * 
   * return service.score(str1, str2); } public static String[] fileSimilar(File dir, String name) {
   * return fileSimilar(dir, name, 50); }
   * 
   * public static String[] fileSimilar(File dir, String name, int adherence) { File mostSimilar =
   * null; double similarity = 0;
   * 
   * for (File arq : dir.listFiles()) { if (arq.isDirectory()) { continue; }
   * 
   * double score = similarScore(arq.getName(), name);
   * 
   * if (score >= (adherence / 100.0) && score > similarity) { mostSimilar = arq; similarity =
   * score; } }
   * 
   * if (mostSimilar == null) { return new String[] { null, "0" }; } return new String[] {
   * mostSimilar.getName(), String.valueOf(similarity) }; }
   */

  public static String trimClean(String text) {

    while (text.contains(CARRIAGE_RETURN)) {
      text = text.replace(CARRIAGE_RETURN, " ");
    }

    while (text.contains(CARRIAGE)) {
      text = text.replace(CARRIAGE, " ");
    }

    while (text.contains(RETURN)) {
      text = text.replace(RETURN, " ");
    }

    while (text.contains("\b")) {
      text = text.replace("\b", " ");
    }

    text = text.replaceAll("\\s+", " ");

    return text.trim();
  }


  public static boolean matches(String pattern, String command) {
    Pattern patternObject = Pattern.compile(pattern);
    Matcher m = patternObject.matcher(command);

    return m.find();
  }



  public static String codePageConvert(String from, String to, String s) {
    try {
      return new String(s.getBytes(from), to);
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static int charCount(String str, char c) {
    int result = 0;
    for (int i = 0; i < str.length(); i++) {
      if (str.charAt(i) == c)
        result++;
    }

    return result;
  }

  public static boolean hasLetter(String s) {
    return HAS_LETTER_PATTERN.matcher(s).find();
  }

  public static String insertSubString(String str, String substr, int pos) {
    String newStr = "";
    if (pos > 0) {
      newStr = str.substring(0, pos);
    }
    newStr += substr;
    newStr += str.substring(pos);

    return newStr;
  }

  public static String[] extractSuffixInt(String str) {
    Pattern regexPattern = Pattern.compile("(.*?)(\\d+)");
    Matcher m = regexPattern.matcher(str);

    if (m.matches()) {
      return new String[] {m.group(1), m.group(2)};
    }

    return new String[] {str, "0"};
  }

  public static String invert(String s) {
    String temp = "";
    for (int i = s.length() - 1; i >= 0; i--)
      temp += s.charAt(i);
    return temp;
  }

  public static List<String> getLinesThatContainsIgnoreCase(String str, String sub) {
    List<String> lines = new ArrayList<String>();

    String[] strArr = str.replace("\r\n", "\n").split("\n");

    for (String line : strArr) {
      if (line.toLowerCase().contains(sub.toLowerCase())) {
        lines.add(line);
      }
    }

    return lines;
  }

  public static List<String> getLinesThatContains(String str, String sub) {
    List<String> lines = new ArrayList<String>();

    String[] strArr = str.replace("\r\n", "\n").split("\n");

    for (String line : strArr) {
      if (line.contains(sub)) {
        lines.add(line);
      }
    }

    return lines;
  }

  public static List<String> getLinesThatNotContains(String str, String sub) {
    List<String> lines = new ArrayList<String>();
    String[] strArr = str.replace("\r\n", "\n").split("\n");
    for (String line : strArr) {
      if (!line.contains(sub)) {
        lines.add(line);
      }
    }
    return lines;
  }

  public static List<String> getLinesThatNotContainsIgnoreCase(String str, String sub) {
    List<String> lines = new ArrayList<String>();
    String[] strArr = str.replace("\r\n", "\n").split("\n");
    for (String line : strArr) {
      if (!line.toLowerCase().contains(sub.toLowerCase())) {
        lines.add(line);
      }
    }
    return lines;
  }

  public static String firstLine(String s) {
    s = s.replace("\r\n", "\n");
    if (s.contains("\n")) {
      return s.split("\n")[0];
    }
    return s;
  }

  public static String lastLine(String s) {
    s = s.replace("\r\n", "\n");

    if (s.contains("\n")) {
      String[] sa = s.split("\n");
      return sa[sa.length - 1];
    }
    return s;
  }

  public static boolean isBinaryExtension(String name) {
    if (name.endsWith(".r") || name.endsWith(".jpeg") || name.endsWith(".jpg")
        || name.endsWith(".gif") || name.endsWith(".png") || name.endsWith(".class")
        || name.endsWith(".swf") || name.endsWith(".svn-base") || name.endsWith(".exe")
        || name.endsWith(".mp3") || name.endsWith(".mp4") || name.endsWith(".dll")
        || name.endsWith(".swc") || name.endsWith(".avi") || name.endsWith(".mpg")
        || name.endsWith(".mkv") || name.endsWith(".mov") || name.endsWith(".bmp")
        || name.endsWith(".lk") || name.endsWith(".msi") || name.endsWith(".r")
        || name.endsWith(".jar") || name.endsWith(".rar") || name.endsWith(".zip")
        || name.endsWith(".gif") || name.endsWith(".gz") || name.endsWith(".wrx")
        || name.endsWith(".swf") || name.endsWith(".fla") || name.endsWith(".ico")
        || name.endsWith(".cur") || name.endsWith(".wrx") || name.endsWith(".db")
        || name.endsWith(".b1") || name.endsWith(".d1")) {
      return true;
    }
    return false;
  }

  public static boolean isEmail(String str) {
    if (EMAIL_PATTERN.matcher(str).matches()) {
      return true;
    }
    return false;
  }

  public static boolean isCapitalized(String str) {
    return Character.isUpperCase(str.charAt(0));
  }

  public static boolean hasInvalidChar(String str, String validChars) {
    Pattern regexChars = Pattern.compile("[{}()\\[\\].+*?^$\\\\|]");
    String checkRegex = "([" + regexChars.matcher(validChars).replaceAll("\\\\$0") + "])+";
    return !str.matches(checkRegex);
  }

  public static boolean hasJapaneseCharacter(String str) {
    for (char c : str.toCharArray()) {
      if (JAPANESE_BLOCKS.contains(UnicodeBlock.of(c))) {
        return true;
      }
    }
    return false;
  }

  public static boolean hasChineseCharacter(String str) {
    for (char c : str.toCharArray()) {
      if (CHINESE_BLOCKS.contains(UnicodeBlock.of(c))) {
        return true;
      }
    }
    return false;
  }

  public static boolean hasArabicCharacter(String str) {
    for (char c : str.toCharArray()) {
      if (ARABIC_BLOCKS.contains(UnicodeBlock.of(c))) {
        return true;
      }
    }
    return false;
  }

  public static void setToClipboard(String aString) {
    StringSelection stringSelection = new StringSelection(aString);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(stringSelection, null);
  }

  public static String getFromClipboard() throws HeadlessException, UnsupportedFlavorException,
      IOException {
    return (String) Toolkit.getDefaultToolkit().getSystemClipboard()
        .getData(DataFlavor.stringFlavor);
  }

  /**
   * Return tabular data
   * @param labels Labels array
   * @param data Data bidimensional array
   * @param padding Total space between fields
   * @return String
   */
  public static String getTabularData(String[] labels, String[][] data, int padding) {
    int[] size = new int[labels.length];
    
    for (int i = 0; i < labels.length; i++) {
      size[i] = labels[i].length() + padding;
    }
    
    for (String[] row : data) {
      for (int i = 0; i < labels.length; i++) {
        if (row[i].length() >= size[i]) {
          size[i] = row[i].length() + padding;
        }
      }
    }
    
    StringBuffer tabularData = new StringBuffer();
    
    for (int i = 0; i < labels.length; i++) {
      tabularData.append(labels[i]);
      tabularData.append(fill(' ', size[i] - labels[i].length()));
    }
    
    tabularData.append("\n");
    
    for (int i = 0; i < labels.length; i++) {
      tabularData.append(fill('=', size[i] - 1)).append(" ");
    }
    
    tabularData.append("\n");
    
    
    for (String[] row : data) {
      for (int i = 0; i < labels.length; i++) {
        tabularData.append(row[i]);
        tabularData.append(fill(' ', size[i] - row[i].length()));
      }
      
      tabularData.append("\n");
    }
    
    return tabularData.toString();
  }
  
  
  
  /**
   * Replace text with HTML entities
   * @param content Content
   * @return Replaced content
   */
  public static String replaceWithHtmlEntities(String content) {
    return replaceWithHtmlEntities(content, escapeStrings);
  }
    
  /**
   * Replace text with HTML entities
   * @param content Content
   * @param map Map to replace
   * @return Replaced content
   */
  public static String replaceWithHtmlEntities(String content, Map<String, Character> map) {
    
    for (Entry<String, Character> entry : map.entrySet()) {
      
      if (content.indexOf(entry.getValue()) != -1) {
        content = content.replace(String.valueOf(entry.getValue()), entry.getKey());
      }
      
    }
    
    return content;
  }
  
  /**
   * Replace HTML entities
   * @param content Content
   * @return Replaced content
   */
  public static String replaceHtmlEntities(String content) {
    return replaceHtmlEntities(content, escapeStrings);
  }
  
  
  /**
   * Replace HTML entities
   * @param content Content
   * @param map Map
   * @return Replaced content
   */
  public static String replaceHtmlEntities(String content, Map<String, Character> map) {
    
    for (Entry<String, Character> entry : escapeStrings.entrySet()) {
      
      if (content.indexOf(entry.getKey()) != -1) {
        content = content.replace(entry.getKey(), String.valueOf(entry.getValue()));
      }
      
    }
    
    return content;
  }
}
