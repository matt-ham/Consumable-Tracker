package ca.cmpt213.a4.client.view;

import ca.cmpt213.a4.client.model.Consumable;
import ca.cmpt213.a4.client.control.ConsumableFactory;
import ca.cmpt213.a4.client.control.ConsumableManager;
import com.github.lgooddatepicker.components.DateTimePicker;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;


/**
 * Class that displays dialog
 * which allows user to add a new
 * consumable item (food/drink)
 *
 * @author Matthew Hamilton
 */
public class AddGUI extends JDialog implements ActionListener {

    Consumable consumable;
    ConsumableFactory factory = new ConsumableFactory();

    // Panels
    JPanel fieldPanel;
    JPanel mainPanel;
    JPanel dateTimePanel;

    // Labels
    JLabel nameLabel;
    JLabel notesLabel;
    JLabel priceLabel;
    JLabel weightLabel;
    JLabel dateLabel;

    // text fields
    JTextField nameField;
    JTextField notesField;
    JTextField priceField;
    JTextField weightField;
    DateTimePicker dateField;

    // buttons
    JButton addItemButton;
    JButton cancelAddItem;

    // combo box
    JComboBox consumableChoice;

    String[] consumables = {"Food", "Drink"};

    public AddGUI(JFrame guiFrame) {

        super(guiFrame, true);

        dateField = new DateTimePicker();
        fieldPanel = new JPanel();
        fieldPanel.setMaximumSize(new Dimension(225, 200));
        fieldPanel.setLayout(new GridLayout(4, 2, 0, 1));
        fieldPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

        nameLabel = new JLabel("Name:");
        notesLabel = new JLabel("Notes:");
        priceLabel = new JLabel("Price:");
        weightLabel = new JLabel("Weight:");
        dateLabel = new JLabel("Date:");
        nameField = new JTextField(10);
        notesField = new JTextField(15);
        priceField = new JTextField(15);
        weightField = new JTextField(15);

        // dont allow pasting for numeric fields (characters get in)
        weightField.setTransferHandler(null);
        weightField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                textFieldKeyTyped(e);
            }
        });

        // dont allow pasting for numeric fields (characters get in)
        priceField.setTransferHandler(null);
        priceField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                textFieldKeyTyped(e);
            }
        });
        dateField = new DateTimePicker();
        fieldPanel.add(nameLabel);
        fieldPanel.add(nameField);
        fieldPanel.add(notesLabel);
        fieldPanel.add(notesField);
        fieldPanel.add(priceLabel);
        fieldPanel.add(priceField);
        fieldPanel.add(weightLabel);
        fieldPanel.add(weightField);

        dateTimePanel = new JPanel(new FlowLayout());
        dateTimePanel.add(dateLabel);
        dateTimePanel.add(dateField);

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(12, 8, 12, 0));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        consumableChoice = new JComboBox(consumables);
        consumableChoice.setMaximumSize(new Dimension(100, 100));
        consumableChoice.setSelectedIndex(-1);
        consumableChoice.setAlignmentX(Component.CENTER_ALIGNMENT);
        consumableChoice.addActionListener(e -> {
            if (Objects.requireNonNull(consumableChoice.getSelectedItem()).toString().equals("Drink")) {
                weightLabel.setText("Volume");
            } else {
                weightLabel.setText("Weight");
            }

        });

        addItemButton = new JButton("Add Item");
        cancelAddItem = new JButton("Cancel");
        cancelAddItem.addActionListener(this);
        addItemButton.addActionListener(this);
        addItemButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        cancelAddItem.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(consumableChoice, Component.CENTER_ALIGNMENT);
        mainPanel.add(fieldPanel, Component.CENTER_ALIGNMENT);
        mainPanel.add(dateTimePanel, Component.CENTER_ALIGNMENT);
        mainPanel.add(addItemButton, Component.CENTER_ALIGNMENT);
        mainPanel.add(cancelAddItem, Component.CENTER_ALIGNMENT);


        this.setResizable(false);
        super.add(mainPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Add Item")) {
            if (consumableChoice.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(null, "Please choose a consumable");
            } else if (nameField.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Name field cannot be empty");
            } else if (priceField.getText().isBlank()) {
                JOptionPane.showMessageDialog(null, "Price field cannot be empty");
            } else if (weightField.getText().isBlank()) {
                if (consumableChoice.getSelectedItem().toString().equals("Drink")) {
                    JOptionPane.showMessageDialog(null, "Volume field cannot be empty");
                } else {
                    JOptionPane.showMessageDialog(null, "Weight field cannot be empty");
                }
            } else {
                String consumableType = (String) consumableChoice.getSelectedItem();
                String consumableName = nameField.getText();
                String notes = notesField.getText();
                String consumablePrice = priceField.getText();
                String consumableWeight = weightField.getText();
                LocalDateTime expiry = dateField.getDateTimePermissive();

                if (expiry == null) {
                    JOptionPane.showMessageDialog(null, "Date field cannot be empty");
                } else {
                    int type;

                    double price = Double.parseDouble(consumablePrice);
                    double weight = Double.parseDouble(consumableWeight);

                    if (Objects.equals(consumableType, "Food")) {
                        type = 1;
                    } else {
                        type = 2;
                    }

                    consumable = factory.getInstance(type, consumableName, notes, weight, price, expiry);
                    String item = sendToServer(type, consumableName, notes, weight, price, expiry);
                    try {
                        ConsumableManager.addItemToServer(type, item);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    setVisible(false);
                    dispose();

                }
            }
        } else if (e.getActionCommand().equals("Cancel")) {

            setVisible(false);
            dispose();
        }
    }

    private String sendToServer(int type, String consumableName, String notes, double size, double price, LocalDateTime expiry) {
        String item;
        if (type == 1) {
            item = new JSONObject()
                    .put("foodName", consumableName)
                    .put("notes", notes)
                    .put("price", price)
                    .put("weight", size)
                    .put("expiryDate", expiry.toString())
                    .put("choice", type)
                    .toString();
        } else {
            item = new JSONObject()
                    .put("drinkName", consumableName)
                    .put("notes", notes)
                    .put("price", price)
                    .put("volume", size)
                    .put("expiryDate", expiry.toString())
                    .put("choice", type)
                    .toString();
        }
        return item;
    }

    public Consumable addConsumableDialog() {
        setTitle("Add new consumable");
        pack();
        setVisible(true);
        return consumable;
    }


    //  https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html
    private void textFieldKeyTyped(java.awt.event.KeyEvent evt) {
        char c = evt.getKeyChar();

        if (!Character.isDigit(c) && !(c == '.')) {
            evt.consume();
        }

    }


}

