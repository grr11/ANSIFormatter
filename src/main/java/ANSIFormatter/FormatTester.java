package ANSIFormatter;

import ANSIFormatter.Format.Color;

/**
 * Prints all supported ANSI SGR parameters along with their descriptions.
 */
public class FormatTester {

  public static void main(String[] args) {
    System.out.println(("-------------------------------- faint color test ------------------------------"));
    for (Color color : Color.values()) {
      Format format = new Format()
          .faint()
          .foreground(color);
      System.out.println(format.apply(color.toString()));
    }

    System.out.println(("---------------------------------- color test ----------------------------------"));
    for (Color color : Color.values()) {
      Format format = new Format().foreground(color);
      System.out.println(format.apply(color.toString()));
    }

    System.out.println(("------------------------------- bright color test ------------------------------"));
    for (Color color : Color.values()) {
      Format format = new Format()
          .bright()
          .foreground(color);
      System.out.println(format.apply(color.toString()));
    }

    System.out.println(("--------------------------------- rgb color test -------------------------------"));
    Format.RGB[] rgb = new Format.RGB[1200];
    int r = 255;
    int g = 55;
    int b = 55;
    for (int i = 0; i < 6; i++) {
      for (int j = 0; j < 200; j++) {
        rgb[i * 200 + j] = new Format.RGB(r, g, b);
        if (i == 0) g++;
        if (i == 1) r--;
        if (i == 2) b++;
        if (i == 3) g--;
        if (i == 4) r++;
        if (i == 5) b--;
      }
    }
    {
      Format format = new Format()
          .bright()
          .alternating(rgb);
      StringBuilder stringBuilder = new StringBuilder();
      for (int i = 0; i < 15; i++)
        stringBuilder.append("################################################################################\n");
      System.out.println(format.apply(stringBuilder.toString()));
    }

    System.out.println("------------------------------ faint background test --------------------------");
    for (Color color : Color.values()) {
      Format format = new Format()
          .faint()
          .foreground(color)
          .negated();

      String buffer = new String(new char[8 - color.toString().length()]).replace('\0', ' ');
      String spaces = new String(new char[80 - buffer.length() - color.toString().length()]).replace('\0', ' ');
      System.out.println(color.toString() + buffer + format.apply(spaces));
    }

    System.out.println("--------------------------------- background test ------------------------------");
    for (Color color : Color.values()) {
      Format format = new Format().background(color);

      String buffer = new String(new char[8 - color.toString().length()]).replace('\0', ' ');
      String spaces = new String(new char[80 - buffer.length() - color.toString().length()]).replace('\0', ' ');
      System.out.println(color.toString() + buffer + format.apply(spaces));
    }

    System.out.println("------------------------------ bright background test --------------------------");
    for (Color color : Color.values()) {
      Format format = new Format()
          .bright()
          .foreground(color)
          .negated();

      String buffer = new String(new char[8 - color.toString().length()]).replace('\0', ' ');
      String spaces = new String(new char[80 - buffer.length() - color.toString().length()]).replace('\0', ' ');
      System.out.println(color.toString() + buffer + format.apply(spaces));
    }

    System.out.println("---------------------------------- style test ----------------------------------");
    Format format = new Format().italicised();
    System.out.println("Italicised: " + format.apply("The quick brown fox jumped over the lazy dog."));
    format = new Format().underlined();
    System.out.println("Underlined: " + format.apply("The quick brown fox jumped over the lazy dog."));
    format = new Format().negated();
    System.out.println("Negative: " + format.apply("The quick brown fox jumped over the lazy dog"));
    format = new Format().faint();
    System.out.println("faint: " + format.apply("The quick brown fox jumped over the lazy dog"));
    format = new Format().blink();
    System.out.println("blink: " + format.apply("The quick brown fox jumped over the lazy dog"));
    format = new Format().blinkRapidly();
    System.out.println("blink rapidly: " + format.apply("The quick brown fox jumped over the lazy dog"));
    format = new Format().concealed();
    System.out.println("conceal: " + format.apply("The quick brown fox jumped over the lazy dog"));
    format = new Format().strikethrough();
    System.out.println("strikethrough: " + format.apply("The quick brown fox jumped over the lazy dog"));
    format = new Format().framed();
    System.out.println("framed : " + format.apply("The quick brown fox jumped over the lazy dog"));
    format = new Format().encircled();
    System.out.println("encircled: " + format.apply("The quick brown fox jumped over the lazy dog"));
    format = new Format().overlined();
    System.out.println("overlined: " + format.apply("The quick brown fox jumped over the lazy dog"));
    format = new Format()
        .bright()
        .alternating(
            Color.RED,
            Color.BLUE,
            Color.GREEN,
            Color.YELLOW,
            Color.CYAN,
            Color.MAGENTA,
            Color.WHITE
        );
    System.out.println("alternating: " + format.apply("The quick brown fox jumped over the lazy dog"));
  }
}