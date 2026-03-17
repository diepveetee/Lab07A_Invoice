import java.util.ArrayList;

/**
 * Represents an invoice consisting of multiple line items.
 * Each line item contributes to the overall total amount due.
 */
public class Invoice {

    /** The list of line items included in this invoice. */
    private ArrayList<LineItem> lineItems = new ArrayList<>();

    /**
     * Adds a new line item to the invoice.
     *
     * @param item the line item to add
     */
    public void addLineItem(LineItem item) {
        lineItems.add(item);
    }

    /**
     * Returns the list of all line items in the invoice.
     *
     * @return a list of line items
     */
    public ArrayList<LineItem> getLineItems() {
        return lineItems;
    }

    /**
     * Computes the total amount due for the invoice.
     * The total is the sum of all line item totals.
     *
     * @return the total invoice amount
     */
    public double getTotalAmount() {
        double total = 0;
        for (LineItem item : lineItems) {
            total += item.getTotal();
        }
        return total;
    }
}
