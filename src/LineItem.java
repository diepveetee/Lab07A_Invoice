/**
 * Represents a single line item on an invoice.
 * A line item combines a product with a quantity and can
 * compute the total cost for that item.
 */
public class LineItem {

    /** The quantity of the product being purchased. */
    private int quantity;

    /** The product associated with this line item. */
    private Product product;

    /**
     * Constructs a LineItem with the given product and quantity.
     *
     * @param product the product being purchased
     * @param quantity the number of units of the product
     */
    public LineItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    /**
     * Computes the total cost for this line item.
     * The total is calculated as:
     * quantity × product.unitPrice
     *
     * @return the total price for this line item
     */
    public double getTotal() {
        return quantity * product.getUnitPrice();
    }

    /**
     * Returns the product associated with this line item.
     *
     * @return the product
     */
    public Product getProduct() {
        return product;
    }

    /**
     * Returns the quantity of the product in this line item.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }
}
