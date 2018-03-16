package com.thereza.posprinterdemo.model;


import com.thereza.posprinterdemo.utility.StaticValue;

/**
 * Created by theReza on 14.03.2018.
 */
public class SalesModel {

    String productShortName;
    int salesAmount;
    double unitSalesCost;

    public SalesModel(String productSName, int amount, double unitSCost){
        this.productShortName = productSName;
        this.salesAmount = amount;
        this.unitSalesCost = unitSCost;
    }

    public SalesModel(){

    }

    public static void generatedMoneyReceipt(){
        SalesModel salesModel = new SalesModel("Napa", 1, 3);
        StaticValue.arrayListSalesModel.add(salesModel);
        SalesModel salesModel1 = new SalesModel("Tofen", 1, 5);
        StaticValue.arrayListSalesModel.add(salesModel1);
        SalesModel salesModel2 = new SalesModel("Renitidin", 1, 1);
        StaticValue.arrayListSalesModel.add(salesModel2);

    }

    public String getProductShortName() {
        return productShortName;
    }

    public int getSalesAmount() {
        return salesAmount;
    }

    public double getUnitSalesCost() {
        return unitSalesCost;
    }


}
