package kr.ac.tukorea.spgp2024.minigametycoon.game.Object.RestaurantScene;

public class OrderData {
    int OrderID;
    int[] OrderTable;
    Person OrderedCustomer;
    public OrderData(int ID, int[] OrderTable, Person OrderedCustomer){
        this.OrderTable = new int[]{OrderTable[0],OrderTable[1]};
        this.OrderID = ID;
        this.OrderedCustomer = OrderedCustomer;
    }
}
