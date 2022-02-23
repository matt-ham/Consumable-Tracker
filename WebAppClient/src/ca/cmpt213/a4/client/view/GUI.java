package ca.cmpt213.a4.client.view;


import ca.cmpt213.a4.client.model.Consumable;
import ca.cmpt213.a4.client.control.ConsumableManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * Class that displays list of consumables
 * through 4 JScrollpane's, which can be switched
 * by pressing corresponding JTabbedPane.
 *
 * @author Matthew Hamilton
 */
public class GUI implements ActionListener {

    // Scroll Panes
    JScrollPane allItems;
    JScrollPane expiredItems;
    JScrollPane nonExpiredItems;
    JScrollPane expiringItems;

    JFrame applicationFrame;
    JTabbedPane tabbedOptions;

    private final ConsumableManager manager;

    public GUI() {


        // Components:
        manager = new ConsumableManager();
        try {
            ConsumableManager.loadArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        applicationFrame = new JFrame("Consumable Item Tracker");
        applicationFrame.setSize(525, 550);
        applicationFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        tabbedOptions = new JTabbedPane();


        allItems = new JScrollPane();
        tabbedOptions.addTab("All", allItems);

        expiredItems = new JScrollPane();
        tabbedOptions.addTab("Expired", expiredItems);

        nonExpiredItems = new JScrollPane();
        tabbedOptions.addTab("Non Expired", nonExpiredItems);

        expiringItems = new JScrollPane();
        tabbedOptions.addTab("Expiring in 7 days", expiringItems);

        applicationFrame.add(tabbedOptions);

        JButton addItem = new JButton("Add Item");
        addItem.addActionListener(this);
        applicationFrame.add(addItem, BorderLayout.SOUTH);


        changeScrollPane();
        tabbedOptions.setSelectedIndex(0);
        applicationFrame.setResizable(false);
        applicationFrame.setVisible(true);

        // https://stackoverflow.com/questions/9093448/how-to-capture-a-jframes-close-button-click-event
        applicationFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    ConsumableManager.saveItem();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.exit(0);
            }
        });
    }


    public JPanel allItems() {
        List<Consumable> itemList = manager.allItems();
        int rows = 1;
        JPanel finalPanel = new JPanel();
        GridLayout layout = new GridLayout(rows, 1, 0, 2);
        finalPanel.setLayout(layout);
        finalPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));


        if (itemList.size() == 0) {
            JLabel emptyList = new JLabel("No Consumable items to display");

            finalPanel.add(emptyList, Component.CENTER_ALIGNMENT);
        } else {
            Collections.sort(itemList);

            int foodIndex = 1;
            int idx = 0;
            for (Consumable item : itemList) {
                JLabel testLabel = new JLabel();
                JPanel newFrame = new JPanel();
                JButton delete = new JButton("Delete");
                int finalIdx = idx;
                delete.addActionListener(e -> {
                    manager.removeItem(item, finalIdx);
                    changeScrollPane();
                });
                idx++;
                JTextArea newText = new JTextArea();
                newFrame.setBorder(BorderFactory.createTitledBorder("Item # " + foodIndex));
                newText.append(item.toString());
                testLabel.setText(item.toString());
                foodIndex++;
                newFrame.add(delete);
                newFrame.add(testLabel);
                finalPanel.add(newFrame);
                rows++;
                layout.setRows(rows);
            }
        }
        return finalPanel;
    }

    public JPanel expiredItems() {

        List<Consumable> itemList = manager.expiredItems();
        int rows = 1;
        JPanel finalPanel = new JPanel();
        GridLayout layout = new GridLayout(rows, 1, 0, 2);
        finalPanel.setLayout(layout);
        finalPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        if (itemList.size() == 0) {
            JLabel emptyList = new JLabel("No Consumable items to display");

            finalPanel.add(emptyList, Component.CENTER_ALIGNMENT);
        } else {
            Collections.sort(itemList);

            int foodIndex = 1;
            int idx = 0;
            for (Consumable item : itemList) {
                JLabel testLabel = new JLabel();
                JPanel newFrame = new JPanel();
                JButton delete = new JButton("Delete");
                int finalIdx = idx;
                delete.addActionListener(e -> {
                    manager.removeItem(item, finalIdx);
                    changeScrollPane();
                });
                idx++;
                JTextArea newText = new JTextArea();
                newFrame.setBorder(BorderFactory.createTitledBorder("Item # " + foodIndex));
                newText.append(item.toString());
                testLabel.setText(item.toString());
                foodIndex++;
                newFrame.add(delete);
                newFrame.add(testLabel);
                finalPanel.add(newFrame);
                rows++;
                layout.setRows(rows);
            }
        }
        return finalPanel;
    }

    public JPanel expiringIn7Items() {

        List<Consumable> itemList = manager.expiryIn7Items();

        JPanel finalPanel = new JPanel();
        int rows = 1;
        GridLayout layout = new GridLayout(rows, 1, 0, 2);
        finalPanel.setLayout(layout);
        finalPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        if (itemList.size() == 0) {
            JLabel emptyList = new JLabel("No Consumable items to display");

            finalPanel.add(emptyList, Component.CENTER_ALIGNMENT);
        } else {
            Collections.sort(itemList);

            int foodIndex = 1;
            int idx = 0;
            for (Consumable item : itemList) {
                JLabel testLabel = new JLabel();
                JPanel newFrame = new JPanel();
                JButton delete = new JButton("Delete");
                int finalIdx = idx;
                delete.addActionListener(e -> {
                    manager.removeItem(item, finalIdx);
                    changeScrollPane();
                });
                idx++;
                JTextArea newText = new JTextArea();
                newFrame.setBorder(BorderFactory.createTitledBorder("Item # " + foodIndex));
                newText.append(item.toString());
                testLabel.setText(item.toString());
                foodIndex++;
                newFrame.add(delete);
                newFrame.add(testLabel);
                finalPanel.add(newFrame);
                rows++;
                layout.setRows(rows);
            }
        }
        return finalPanel;
    }

    public JPanel nonExpiredItems() {

        List<Consumable> itemList = manager.nonExpired();

        JPanel finalPanel = new JPanel();
        int rows = 1;
        GridLayout layout = new GridLayout(rows, 1, 0, 2);
        finalPanel.setLayout(layout);
        finalPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        if (itemList.size() == 0) {
            JLabel emptyList = new JLabel("No Consumable items to display");

            finalPanel.add(emptyList, Component.CENTER_ALIGNMENT);
        } else {
            Collections.sort(itemList);

            int foodIndex = 1;
            int idx = 0;
            for (Consumable item : itemList) {
                JLabel testLabel = new JLabel();
                JPanel newFrame = new JPanel();
                JButton delete = new JButton("Delete");
                int finalIdx = idx;
                delete.addActionListener(e -> {
                    manager.removeItem(item, finalIdx);
                    changeScrollPane();
                });
                idx++;
                JTextArea newText = new JTextArea();
                newFrame.setBorder(BorderFactory.createTitledBorder("Item # " + foodIndex));
                newText.append(item.toString());
                testLabel.setText(item.toString());
                foodIndex++;
                newFrame.add(delete);
                newFrame.add(testLabel);
                finalPanel.add(newFrame);
                rows++;
                layout.setRows(rows);
            }
        }
        return finalPanel;
    }

    // Once add item dialog closes, update all jscrollpanes
    // To show newly added consumable
    public void changeScrollPane() {

        //  #1 remove all scroll panes
        tabbedOptions.remove(allItems);
        tabbedOptions.remove(expiredItems);
        tabbedOptions.remove(nonExpiredItems);
        tabbedOptions.remove(expiringItems);

        // #2 create new scroll panes (passing in method with sorted list)
        allItems = new JScrollPane(allItems());
        expiredItems = new JScrollPane(expiredItems());
        nonExpiredItems = new JScrollPane(nonExpiredItems());
        expiringItems = new JScrollPane(expiringIn7Items());

        // #3 add to the tab IN ORDER
        tabbedOptions.add("All", allItems);
        tabbedOptions.add("Expired", expiredItems);
        tabbedOptions.add("Non Expired", nonExpiredItems);
        tabbedOptions.add("Expiring in 7 days", expiringItems);

    }

    private void addConsumable(Consumable consumable) {
        if (consumable != null) {
            manager.addItem(consumable);
            changeScrollPane();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Item")) {
            AddGUI dialog = new AddGUI(applicationFrame);
            addConsumable(dialog.addConsumableDialog());
        }
    }
}