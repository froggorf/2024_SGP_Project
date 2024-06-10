package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene;

public class OrderData {
    public int OrderID;
    public int[] OrderTable;
    public Person OrderedCustomer;
    public OrderData(int ID, int[] OrderTable, Person OrderedCustomer){
        this.OrderTable = new int[]{OrderTable[0],OrderTable[1]};
        this.OrderID = ID;
        this.OrderedCustomer = OrderedCustomer;
    }
}
