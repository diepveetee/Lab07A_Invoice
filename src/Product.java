/**
 * Represents a product that can appear on an invoice.
 * A product has a name and a unit price, which are used by
 * {@link LineItem} to calculate line totals.
 */
public class Product {

    /** The name of the product. */
    private String name;

    /** The price per unit of the product. */
    private double unitPrice;

    /**
     * Constructs a Product with the given name and unit price.
     *
     * @param name the name of the product
     * @param unitPrice the cost of a single unit of the product
     */
    public Product(String name, double unitPrice) {
        this.name = name;
        this.unitPrice = unitPrice;
    }

    /**
     * Returns the name of the product.
     *
     * @return the product name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the unit price of the product.
     *
     * @return the price per unit
     */
    public double getUnitPrice() {
        return unitPrice;
    }
}

