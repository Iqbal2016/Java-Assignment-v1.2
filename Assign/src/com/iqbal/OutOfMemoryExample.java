/**
 * 
 */
package com.iqbal;

/**
 * @author iqbal
 */
import java.util.ArrayList;
import java.util.List;

public class OutOfMemoryExample {
    public static void main(String[] args) {
        List<String> records = new ArrayList<>();
        
        try {
            while (true) {
                StringBuffer stringBuffer = new StringBuffer();
                stringBuffer.append("Record ");
                records.add(stringBuffer.toString());
            }
        } catch (OutOfMemoryError error) {
            System.out.println("Out of memory error occurred!");
            error.printStackTrace();
        }
    }
}
