package com.thereza.posprinterdemo.utility;

import android.content.Context;

import com.thereza.posprinterdemo.R;
import com.thereza.posprinterdemo.activity.MainActivity;

import com.thereza.posprinterdemo.model.SalesModel;


/**
 * This class is responsible to generate a static sales receipt and to print that receipt
 */
public class PrintReceipt {

	public static boolean  printBillFromOrder(Context context){
		if(MainActivity.BLUETOOTH_PRINTER.IsNoConnection()){
			return false;
		}

		byte[] cc = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
		byte[] bb = new byte[]{0x1B,0x21,0x08};  // 1- only bold text
		byte[] bb2 = new byte[]{0x1B,0x21,0x20}; // 2- bold with medium text
		byte[] bb3 = new byte[]{0x1B,0x21,0x10}; // 3- bold with large text

		double totalBill=0.00, netBill=0.00, totalVat=0.00;

		//LF = Line feed
		MainActivity.BLUETOOTH_PRINTER.Begin();
		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);//CENTER
		MainActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//30 * 0.125mm
		MainActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x1B);//normal
		MainActivity.BLUETOOTH_PRINTER.BT_Write(bb2);
		MainActivity.BLUETOOTH_PRINTER.BT_Write("LAZ FARMA");

		MainActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);//CENTER
		MainActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//30 * 0.125mm
		MainActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal
		MainActivity.BLUETOOTH_PRINTER.BT_Write("\nMobile No : 01745485894");
		MainActivity.BLUETOOTH_PRINTER.BT_Write("\nAddress : Mohammadpur");
		
		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);
		MainActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);
		MainActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);

        //BT_Write() method will initiate the printer to start printing.
        MainActivity.BLUETOOTH_PRINTER.BT_Write("\n\nInvoice No: " + "12457334" +
				"\nDate: " + "13/03/2018" +
				"\nTime: " + "10.00 AM");
		
		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.BT_Write("\n"+context.getResources().getString(R.string.print_line)+"\n");
		MainActivity.BLUETOOTH_PRINTER.LF();
		
		MainActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 0);//LEFT
		MainActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//50 * 0.125mm
		MainActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal font

		//static sales record are generated
		SalesModel.generatedMoneyReceipt();

		for(int i=0;i<StaticValue.arrayListSalesModel.size();i++){
			SalesModel salesModel = StaticValue.arrayListSalesModel.get(i);

			MainActivity.BLUETOOTH_PRINTER.LF();
			MainActivity.BLUETOOTH_PRINTER.BT_Write("\n"+salesModel.getProductShortName()+"    "
					+ salesModel.getSalesAmount() + "    "
					+ salesModel.getUnitSalesCost() +"   "
					+ StaticValue.CURRENCY);
			MainActivity.BLUETOOTH_PRINTER.LF();
			
			totalBill=totalBill + (salesModel.getUnitSalesCost() * salesModel.getSalesAmount());
		}
		
		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.BT_Write("\n"+context.getResources().getString(R.string.print_line)+"\n");
		
		
		MainActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 2);//RIGHT
		MainActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);	//50 * 0.125mm
		MainActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte)0x00);//normal font

		totalVat=Double.parseDouble(Utility.doubleFormatter(totalBill*(StaticValue.VAT/100)));
		netBill=totalBill+totalVat;
		
		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.BT_Write("Total Bill:" + Utility.doubleFormatter(totalBill) + "" + StaticValue.CURRENCY+"\n");
		
		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.BT_Write(Double.toString(StaticValue.VAT) + "% VAT:" + Utility.doubleFormatter(totalVat) + "" +
				StaticValue.CURRENCY+"\n");
		
		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);//center
		MainActivity.BLUETOOTH_PRINTER.BT_Write(context.getResources().getString(R.string.print_line)+"\n");

		
		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.SetLineSpacing((byte) 30);
		MainActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 2);//Right
		MainActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x9);
		MainActivity.BLUETOOTH_PRINTER.BT_Write("Net Bill:" + Utility.doubleFormatter(netBill) + "" + StaticValue.CURRENCY+"\n");
		
		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);//center
		MainActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal font
		MainActivity.BLUETOOTH_PRINTER.BT_Write(context.getResources().getString(R.string.print_line));

		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.SetAlignMode((byte) 1);//center
		MainActivity.BLUETOOTH_PRINTER.SetFontEnlarge((byte) 0x00);//normal font
		MainActivity.BLUETOOTH_PRINTER.BT_Write("");


		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.LF();
		MainActivity.BLUETOOTH_PRINTER.LF();
		return true;
	}
}
