package model;

/** Model for an outsourced part. */
public class Outsourced extends Part {
    private String companyName;

    /** Constructor for an outsourced part.
     * @param companyName Name of the company.
     * @param stock Current stock.
     * @param price Price of a part.
     * @param name Name of the part.
     * @param min Minimum inventory level.
     * @param max Maximum inventory level.
     * @param id ID for part. */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);

        this.companyName = companyName;
    }

    /** Method to set the company name for a part.
     * @param companyName the company name for a part. */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /** Method to return the current company name of a part.
     * @return companyName The current company name. */
    public String getCompanyName() {
        return companyName;
    }
}