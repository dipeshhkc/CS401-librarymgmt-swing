package resources;

import java.awt.Color;
import java.awt.Font;

public class ThemeColor {
	public static final Color primaryColor = convertHexToRGB("#4b49ac");
	public static final Color secondaryColor = new Color(152, 189, 255);
	public static final Color supporting1 = new Color(125, 160, 250);
	public static final Color supporting2 = new Color(121, 120, 233);
	public static final Color supporting3 = new Color(243, 121, 126);
	public static final Color backgroundColor = convertHexToRGB("#f9f9f9");
	public static final Color textColor = new Color(108, 115, 131);
	public static final Color black = new Color(0, 0, 0);
	public static final Color white = new Color(255, 255, 255);
	
	public static final Font titleText = new Font("Roboto Slab", Font.BOLD, 20);
	public static final Font formLabel = new Font("Roboto Slab", Font.BOLD, 15);
	public static final Font normalText = new Font("Roboto Slab", Font.PLAIN, 15);

	public static Color convertHexToRGB(String hexColor) {
		int r = Integer.valueOf(hexColor.substring(1, 3), 16);
		int g = Integer.valueOf(hexColor.substring(3, 5), 16);
		int b = Integer.valueOf(hexColor.substring(5, 7), 16);
		return new Color(r, g, b);
	}

}