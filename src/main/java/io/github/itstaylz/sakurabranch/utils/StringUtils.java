package io.github.itstaylz.sakurabranch.utils;

import org.bukkit.ChatColor;

public final class StringUtils {

    public static String colorize(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String decimalToRoman(int numb) {
        switch (numb) {
            case 1:
                return "I";
            case 2:
                return "II";
            case 3:
                return "III";
            case 4:
                return "IV";
            case 5:
                return "V";
            case 6:
                return "VI";
            case 7:
                return "VII";
            case 8:
                return "VIII";
            case 9:
                return "IX";
            case 10:
                return "X";
        }
        return Integer.toString(numb);
    }
}
