import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;

/**
 * A Swing-based graphical user interface for creating and displaying an invoice.
 *
 * Users can enter a product name, unit price, and quantity. Each submitted item
 * is added to an internal {@link Invoice} object and displayed in two listboxes:
 * one showing the history of added items, and the other showing a formatted
 * invoice summary with aligned columns.
 *
 * Numeric input fields are restricted using a regular-expression-based
 * {@link DocumentFilter} to ensure valid integer and decimal entry.
 */
public class InvoiceGUI extends JFrame {

    /** Regular expression allowing only whole numbers (0–9). */
    private static final String INT_PATTERN = "^[0-9]*$";

    /** Regular expression allowing decimal numbers with up to two decimal places. */
    private static final String DOUBLE_PATTERN = "^[0-9]*\\.?[0-9]{0,2}$";

    /** Text field for entering the product name. */
    private JTextField productNameField;

    /** Text field for entering the unit price. */
    private JTextField unitPriceField;

    /** Text field for entering the quantity. */
    private JTextField quantityField;

    /** List model storing the history of added line items. */
    private DefaultListModel<String> purchaseHistory = new DefaultListModel<>();

    /** List model storing the formatted invoice display. */
    private DefaultListModel<String> invoiceModel = new DefaultListModel<>();

    /** Listbox showing the history of added items. */
    private JList<String> historyList;

    /** Listbox showing the formatted invoice summary. */
    private JList<String> invoiceList;

    /** The invoice object that stores all line items. */
    private Invoice invoice = new Invoice();

    /**
     * Constructs the InvoiceGUI window, initializes all Swing components,
     * applies numeric input filters, and arranges the layout.
     */
    public InvoiceGUI() {
        setTitle("Invoice Entry");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        inputPanel.add(productNameField);

        inputPanel.add(new JLabel("Unit Price:"));
        unitPriceField = new JTextField();
        inputPanel.add(unitPriceField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        // Apply numeric validation filters
        applyRegexFilter(quantityField, INT_PATTERN);
        applyRegexFilter(unitPriceField, DOUBLE_PATTERN);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(inputPanel, BorderLayout.CENTER);

        JButton addButton = new JButton("Add Line Item");
        addButton.addActionListener(e -> addLineItem());
        topPanel.add(addButton, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);

        historyList = new JList<>(purchaseHistory);
        invoiceList = new JList<>(invoiceModel);
        invoiceList.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JPanel listPanel = new JPanel(new GridLayout(1, 2));
        listPanel.add(new JScrollPane(historyList));
        listPanel.add(new JScrollPane(invoiceList));

        add(listPanel, BorderLayout.CENTER);
    }

    /**
     * Applies a regular-expression-based filter to a text field, restricting
     * user input to match the provided pattern.
     *
     * @param field the text field to restrict
     * @param regex the regular expression defining allowed input
     */
    private void applyRegexFilter(JTextField field, String regex) {
        ((AbstractDocument) field.getDocument())
                .setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
                    throws BadLocationException {

                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String nextText = currentText.substring(0, offset)
                        + text + currentText.substring(offset + length);

                if (nextText.isEmpty() || nextText.matches(regex)) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }
        });
    }

    /**
     * Reads user input from the text fields, creates a {@link LineItem},
     * adds it to the invoice, updates the history list, clears the input fields,
     * and refreshes the formatted invoice display.
     */
    private void addLineItem() {
        try {
            String name = productNameField.getText();
            double price = Double.parseDouble(unitPriceField.getText());
            int qty = Integer.parseInt(quantityField.getText());

            Product p = new Product(name, price);
            LineItem item = new LineItem(p, qty);
            invoice.addLineItem(item);

            purchaseHistory.addElement("Added: "+ qty + " × " + name);

            productNameField.setText("");
            unitPriceField.setText("");
            quantityField.setText("");

            updateInvoiceList();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid number format.");
        }
    }

    /**
     * Rebuilds the invoice display list using aligned columns for item name,
     * quantity, price, and total. A header and footer are included for clarity.
     */
    private void updateInvoiceList() {
        invoiceModel.clear();

        invoiceModel.addElement(String.format("%-30s %5s %10s %10s",
                "Item", "Qty", "Price", "Total"));
        invoiceModel.addElement("--------------------------------------------------------------");

        for (LineItem item : invoice.getLineItems()) {
            invoiceModel.addElement(String.format(
                    "%-30s %5d %10.2f %10.2f",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    item.getProduct().getUnitPrice(),
                    item.getTotal()
            ));
        }

        invoiceModel.addElement("--------------------------------------------------------------");

        invoiceModel.addElement(String.format("%-30s %5s %10s %10.2f",
                "", "", "TOTAL:", invoice.getTotalAmount()));
    }
}
