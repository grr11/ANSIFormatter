package ANSIFormatter;

/**
 * The Format class defines an ANSI format that can be repeatedly applied to String objects.
 */
public class Format {
  private static final char ESC = (char) 27;
  private static final String START_SEQ = ESC + "[";
  private static final String END_SEQ = "m";
  private static final String RESET_FORMAT = ESC + "0" + END_SEQ;

  private String style = "";
  private String foregroundColor = Color.DEFAULT.foregroundCode;
  private String backgroundColor = Color.DEFAULT.backgroundCode;
  private String intensity = Intensity.NORMAL.code;
  private String escapeSequence = "";
  private String[] colors;

  private boolean finalized = false;

  /**
   * Applies the format to the given String.
   *
   * @param str The String being formatted
   * @return The formatted String
   */
  public String apply(String str) {
    if (!finalized) {
      if (style.isEmpty())
        escapeSequence = START_SEQ + intensity + ";" + foregroundColor + ";" + backgroundColor + END_SEQ;
      else
        escapeSequence = START_SEQ + intensity + ";" + foregroundColor + ";" + backgroundColor + ";" + style + END_SEQ;
    }

    if (finalized && escapeSequence.isEmpty())
      escapeSequence = START_SEQ + intensity + ";" + foregroundColor + ";" + backgroundColor + END_SEQ;

    if (str == null || str.length() == 0)
      throw new IllegalArgumentException();

    if (colors == null || colors.length == 0)
      return escapeSequence + str + RESET_FORMAT;
    else {
      StringBuilder stringBuilder = new StringBuilder(START_SEQ).append(escapeSequence);
      int colorIndex = 0;
      char curChar;
      for (int i = 0; i < str.length(); i++) {
        curChar = str.charAt(i);
        if (!Character.isWhitespace(curChar)) {
          stringBuilder
              .append(START_SEQ)
              .append(colors[colorIndex % colors.length])
              .append(END_SEQ);
          colorIndex++;
        }
        stringBuilder.append(curChar);
      }
      stringBuilder.append(RESET_FORMAT);
      return stringBuilder.toString();
    }
  }

  /**
   * Makes this Format immutable.
   */
  public void finalize() {
    finalized = true;
  }

  /**
   * Sets the foreground to one of the 8 original colors specified by ANSI.
   * SGR Codes: 30-37
   *
   * @param color The foreground color
   * @return this Format
   */
  public Format foreground(Color color) {
    foregroundColor = color.foregroundCode;
    return this;
  }

  /**
   * Sets the background to one of the 8 original colors specified by ANSI.
   * SGR Codes: 40-47
   *
   * @param color The background color
   * @return this Format
   */
  public Format background(Color color) {
    backgroundColor = color.backgroundCode;
    return this;
  }

  /**
   * Cycles through the given array of colors, one character for each color.
   * SGR Codes: 30-37
   *
   * @param colors The colors to be included
   * @return this Format
   */
  public Format alternating(Color... colors) {
    this.colors = new String[colors.length];
    for (int i = 0; i < colors.length; i++) {
      this.colors[i] = colors[i].foregroundCode;
    }
    return this;
  }

  /**
   * Sets the intensity to bright.
   * SGR Code: 1
   *
   * @return this Format
   */
  public Format bright() {
    this.intensity = Intensity.BRIGHT.code;
    return this;
  }

  /**
   * NOT WIDELY SUPPORTED. Sets the intensity to faint.
   * SGR Code: 2
   *
   * @return this Format
   */
  public Format faint() {
    this.intensity = Intensity.FAINT.code;
    return this;
  }

  /**
   * NOT WIDELY SUPPORTED. Italicises the text.
   * SGR Code: 3
   *
   * @return this Format
   */
  public Format italicised() {
    addStyle(Style.ITALICS);
    return this;
  }

  /**
   * Underlines the text.
   * SGR Code: 4
   *
   * @return this Format
   */
  public Format underlined() {
    addStyle(Style.UNDERLINED);
    return this;
  }

  /**
   * Blinks the text (less than 150 blinks per minute)
   * SGR Code: 5
   *
   * @return this Format
   */
  public Format blink() {
    addStyle(Style.BLINK);
    return this;
  }

  /**
   * NOT WIDELY SUPPORTED. Blinks the text rapidly.
   * SGR Code: 5
   *
   * @return this Format
   */
  public Format blinkRapidly() {
    addStyle(Style.BLINK_RAPID);
    return this;
  }

  /**
   * Swaps the foreground/background colors
   *
   * @return this Format
   */
  public Format negated() {
    addStyle(Style.NEGATIVE);
    return this;
  }

  /**
   * NOT WIDELY SUPPORTED. Conceals the text.
   *
   * @return this Format
   */
  public Format concealed() {
    addStyle(Style.CONCEAL);
    return this;
  }

  /**
   * NOT WIDELY SUPPORTED. Crosses out the text.
   *
   * @return this Format
   */
  public Format strikethrough() {
    addStyle(Style.STRIKETHROUGH);
    return this;
  }

  /**
   * Frames the text.
   *
   * @return this Format
   */
  public Format framed() {
    addStyle(Style.FRAMED);
    return this;
  }

  /**
   * Encircles the text.
   *
   * @return this Format
   */
  public Format encircled() {
    addStyle(Style.ENCIRCLED);
    return this;
  }

  /**
   * Overlines the text
   *
   * @return this Format
   */
  public Format overlined() {
    addStyle(Style.OVERLINED);
    return this;
  }

  private void addStyle(Style style) {
    if (!this.style.isEmpty())
      this.style += ";" + style.code;
    else
      this.style = style.code;
  }

  /**
   * Colors specified by the original ANSI standard.
   */
  public enum Color {
    BLACK(30),
    RED(31),
    GREEN(32),
    YELLOW(33),
    BLUE(34),
    MAGENTA(35),
    CYAN(36),
    WHITE(37),
    DEFAULT(39);

    final String backgroundCode;
    final String foregroundCode;

    Color(int value) {
      foregroundCode = Integer.toString(value);
      backgroundCode = Integer.toString(value + 10);
    }
  }

  private enum Intensity {
    NORMAL("22"),
    BRIGHT("1"),
    FAINT("2");

    final String code;

    Intensity(String code) {
      this.code = code;
    }
  }

  private enum Style {
    ITALICS("3"),
    UNDERLINED("4"),
    BLINK("5"),
    BLINK_RAPID("6"),
    NEGATIVE("7"),
    CONCEAL("8"),
    STRIKETHROUGH("9"),
    FRAMED("51"),
    ENCIRCLED("52"),
    OVERLINED("53");

    final String code;

    Style(String code) {
      this.code = code;
    }
  }

}
