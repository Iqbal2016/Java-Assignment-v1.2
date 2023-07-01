
package com.iqbal.tools;

import javax.swing.JOptionPane;

/**
 *
 * @author user
 */
public class Utils {

    public static void stdout(Object o) {
        System.out.println(o);
    }
    public static String escapeSQLVar(String sql) {
        return escapeSQLVar(sql, true);
    }

    // source, stackoverflow.com
    public static String escapeSQLVar(String sql, boolean escapeDoubleQuotes) {
        StringBuilder sBuilder = new StringBuilder(sql.length() * 11/10);

        int stringLength = sql.length();

        for (int i = 0; i < stringLength; ++i) {
            char c = sql.charAt(i);

            switch (c) {
            case 0: /* Must be escaped for 'mysql' */
                sBuilder.append('\\');
                sBuilder.append('0');

                break;

            case '\n': /* Must be escaped for logs */
                sBuilder.append('\\');
                sBuilder.append('n');

                break;

            case '\r':
                sBuilder.append('\\');
                sBuilder.append('r');

                break;

            case '\\':
                sBuilder.append('\\');
                sBuilder.append('\\');

                break;

            case '\'':
                sBuilder.append('\\');
                sBuilder.append('\'');

                break;

            case '"': /* Better safe than sorry */
                if (escapeDoubleQuotes) {
                    sBuilder.append('\\');
                }

                sBuilder.append('"');

                break;

            case '\032': /* This gives problems on Win32 */
                sBuilder.append('\\');
                sBuilder.append('Z');

                break;

            case '\u00a5':
            case '\u20a9':
                // escape characters interpreted as backslash by mysql
                // fall through

            default:
                sBuilder.append(c);
            }
        }

        return sBuilder.toString();
    }

    public static void alert(String msg) {
        JOptionPane.showMessageDialog(null, msg,
                "Alert", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (Exception e) {

        }
    }

    public static String prompt(String title) {
        return Utils.prompt(title, "");
    }

    public static String prompt(String title, String def) {
        String str = null;
        if (def.trim().equals("")) {
            str = JOptionPane.showInputDialog(null, title, AppInfo.APP_NAME,
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            str = (String) JOptionPane.showInputDialog(null, title, AppInfo.APP_NAME,
                    JOptionPane.INFORMATION_MESSAGE, null, null, def);
        }
        return str;
    }

    public static boolean confirm(String msg) {
        Object[] btn = new String[]{"Yes", "No"};
        int opt = chooseOption(btn, msg, "Confirmation");
        return opt == 0;
    }

    public static int chooseOption(Object[] buttons, String msg, String title) {
        int opt = JOptionPane.showOptionDialog(null, msg,
                title, JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null,
                buttons, null);
        return opt;
    }
}
