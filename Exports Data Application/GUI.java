/*
 * Coder : Phantom-fs
*/

//make sure all files are in same package
package ExportsCSV;

import javax.swing.*;
import java.awt.*;

public class GUI
{
    ExportsCSVReading ex = new ExportsCSVReading();
    JFrame frame = new JFrame("Countries Export Data");

    public GUI()
    {
        JLabel label = new JLabel("Countries Export Data");
        label.setFont(new Font("Arial", Font.BOLD, 30));
        label.setBounds(220, 10, 400, 80);
        frame.add(label);

        //EXIT BUTTON
        JButton exit = new JButton("X");
        exit.setFont(new Font("Arial", Font.BOLD, 15));
        exit.setForeground(Color.RED);
        exit.setBounds(700, 2, 44, 30);
        frame.add(exit);

        exit.addActionListener(e -> System.exit(0));

        //creating objects of JButton class
        JButton button1 = new JButton("Countries : exports and export value");
        JButton button2 = new JButton("Countries : Exports");
        JButton button3 = new JButton("Countries : Export Value");

        JButton button4 = new JButton("All Countries");

        JButton button5 = new JButton("Find Countries which EXPORTS a given ITEM");
        JButton button6 = new JButton("Find a Country's EXPORTS");
        JButton button7 = new JButton("Find a Country's EXPORT VALUE");
        JButton button8 = new JButton("Countries: Export Value is GREATER than a VALUE");

        JButton button9 = new JButton("Countries : exports IMPORTANT ITEMS");
        JButton button10 = new JButton("Calculations : Exports over no. & Total Export Value");

        //setting bounds of button
        button1.setBounds(50, 100, 300, 40);
        button2.setBounds(400, 100, 300, 40);
        button3.setBounds(50, 150, 300, 40);
        button4.setBounds(400, 150, 300, 40);
        button5.setBounds(50, 200, 300, 40);
        button6.setBounds(400, 200, 300, 40);
        button7.setBounds(50, 250, 300, 40);
        button8.setBounds(400, 250, 300, 40);
        button9.setBounds(50, 300, 300, 40);
        button10.setBounds(400, 300, 300, 40);

        //adding button to frame
        frame.add(button1);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.add(button5);
        frame.add(button6);
        frame.add(button7);
        frame.add(button8);
        frame.add(button9);
        frame.add(button10);

        //setting the bounds of frame
        frame.setSize(760, 400);

        //setting the layout of frame
        frame.setLayout(null);

        //setting the visibility of frame
        frame.setVisible(true);

        //adding action listener to buttons

        //Countries : exports and export value
        button1.addActionListener(e -> ex.printFullCSV());

        //Countries: Exports
        button2.addActionListener(e -> ex.printCountryAndExports());

        //Countries : Export Value
        button3.addActionListener(e -> ex.printCountryAndValue());

        //All Countries
        button4.addActionListener(e -> ex.printCountries());

        //Find Countries which EXPORT a given ITEM
        button5.addActionListener(e ->
        {
            JOptionPane.showMessageDialog(frame, "Please enter the export name in LOWERCASE, eg. gold", "Important", JOptionPane.INFORMATION_MESSAGE);
            String export = JOptionPane.showInputDialog("Enter the EXPORT ITEM");
            ex.printExportOfInterest(export);
        });

        //Find a Country's EXPORT
        button6.addActionListener(e ->
        {
            JOptionPane.showMessageDialog(frame, "Please enter the COUNTRY name with first letter in UPPERCASE, rest LOWERCASE, eg. Portugal", "Important", JOptionPane.INFORMATION_MESSAGE);
            String country = JOptionPane.showInputDialog("Enter the COUNTRY whose EXPORT you want to find");
            ex.exportsByCountry(country);
        });

        //Find a Country's EXPORT VALUE
        button7.addActionListener(e ->
        {
            JOptionPane.showMessageDialog(frame, "Please enter the COUNTRY name with first letter in UPPERCASE, rest LOWERCASE, eg. Portugal", "Important", JOptionPane.INFORMATION_MESSAGE);
            String country = JOptionPane.showInputDialog("Enter the COUNTRY whose EXPORT VALUE you want to find");
            ex.exportsValueByCountry(country);
        });

        //Find Countries whose Export Value is GREATER than a given VALUE
        button8.addActionListener(e ->
        {
            JOptionPane.showMessageDialog(frame, "Please enter the VALUE in INTEGER, don't add $ sign, eg. 1000000", "Important", JOptionPane.INFORMATION_MESSAGE);
            String value = JOptionPane.showInputDialog("Enter the VALUE to find countries whose EXPORT VALUE is GREATER than : ");
            ex.exportsByValue(value);
        });

        //Countries: exports IMPORTANT ITEMS
        button9.addActionListener(e -> ex.printSomeImportantExports());

        //Calculations : Number of Countries, Total Export Value, Average Export Value
        button10.addActionListener(e ->
        {
            JOptionPane.showMessageDialog(frame, "Please enter the number of EXPORTS in INTEGER (preferably < 15), eg. 8", "Important", JOptionPane.INFORMATION_MESSAGE);
            String export = JOptionPane.showInputDialog("Enter the minimum number of EXPORT ITEM");
            int exportInt = Integer.parseInt(export);
            ex.someCalculations(exportInt);
        });
    }

    public static void main (String[] args)
    {
        GUI ce = new GUI();
    }
}
