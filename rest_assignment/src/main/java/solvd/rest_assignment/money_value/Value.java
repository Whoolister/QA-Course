package solvd.rest_assignment.money_value;

public class Value implements Comparable<Value>  {
    private String fecha;
    private double compra;
    private double venta;



    public double getCompra() {
        return compra;
    }

    public void setCompra(double compra) {
        this.compra = compra;
    }

    public double getVenta() {
        return venta;
    }

    public void setVenta(double venta) {
        this.venta = venta;
    }

    @Override
    public String toString() {
        return "Valor{" +
                "fecha='" + fecha + '\'' +
                ", compra=" + compra +
                ", venta=" + venta +
                '}';
    }



    @Override
    public int compareTo(Value o) {
        return Double.compare((this.compra + this.venta), (o.compra + o.venta));
    }
}
