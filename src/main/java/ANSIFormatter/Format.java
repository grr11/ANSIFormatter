package ANSIFormatter;

public class Format {
  private static final String START_FORMAT = (char)27 + "[";
  private static final String END_FORMAT = (char)27 + "[0m";

  private String style = "";
  private String foregroundColor = Color.DEFAULT.foregroundCode;
  private String backgroundColor = Color.DEFAULT.backgroundCode;
  private String intensity = Intensity.NORMAL.code;
  private String[] colors;

  public String apply(String str) {
    String format;
    if (style.isEmpty())
      format = intensity + ";" + foregroundColor + ";" + backgroundColor + "m";
    else
      format = intensity + ";" + foregroundColor + ";" + backgroundColor + ";" + style + "m";
    if (str == null || str.length() == 0)
      return "";
    if (colors == null || colors.length == 0)
      return new StringBuilder(START_FORMAT).append(format).append(str).append(END_FORMAT).toString();
    else {
      StringBuilder stringBuilder = new StringBuilder(START_FORMAT).append(format);
      int colorIndex = 0;
      for (int i = 0; i < str.length(); i++) {
        if (!Character.isWhitespace(str.charAt(i))) {
          stringBuilder.append(START_FORMAT).append(colors[colorIndex % colors.length]).append("m");
          colorIndex++;
        }
        stringBuilder.append(str.charAt(i));
      }
      stringBuilder.append(END_FORMAT);
      return stringBuilder.toString();
    }
  }

  public Format foreground(Color color) {
    foregroundColor = color.foregroundCode;
    return this;
  }

  public Format background(Color color) {
    backgroundColor = color.backgroundCode;
    return this;
  }

  public Format alternating(Color... colors) {
    this.colors = new String[colors.length];
    for (int i = 0; i < colors.length; i++) {
      this.colors[i] = colors[i].foregroundCode;
    }
    return this;
  }

  public Format bright() {
    this.intensity = Intensity.BRIGHT.code;
    return this;
  }

  public Format faint() {
    this.intensity = Intensity.FAINT.code;
    return this;
  }

  public Format italicised() {
    addStyle(Style.ITALICS);
    return this;
  }

  public Format underlined() {
    addStyle(Style.UNDERLINED);
    return this;
  }

  public Format blink() {
    addStyle(Style.BLINK);
    return this;
  }

  public Format blinkRapidly() {
    addStyle(Style.BLINK_RAPID);
    return this;
  }

  public Format negated() {
    addStyle(Style.NEGATIVE);
    return this;
  }

  public Format concealed() {
    addStyle(Style.CONCEAL);
    return this;
  }

  public Format strikethrough() {
    addStyle(Style.STRIKETHROUGH);
    return this;
  }

  public Format framed() {
    addStyle(Style.FRAMED);
    return this;
  }

  public Format encircled() {
    addStyle(Style.ENCIRCLED);
    return this;
  }

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
