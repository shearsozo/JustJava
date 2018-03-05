/**
 * IMPORTANT: Make sure you are using the correct package name.
 * This example uses the package name:
 * package com.example.android.justjava
 * If you get an error when copying this code into Android studio, update it to match teh package name found
 * in the project's AndroidManifest.xml file.
 **/

package com.example.agape.justjava;



import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.agape.justjava.R;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the  plus button is click.
     */
    public void increment(View view) {
        if (quantity == 100) {
            //Show an error message as a toast
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            //exit method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the  minus button is click.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            //Show an error message as a toast
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //exit method early because there's nothing left to do
            return;
        }

        quantity = quantity - 1;
        display(quantity);

    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = findViewById(R.id.name_field);
        //find the user's name
        String name = nameField.getText().toString();

        //figure out if user wants whipped cream topping
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        //figure out if user wants chocolate topping
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/html");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
            startActivity(Intent.createChooser(intent, "Send Email"));
        }
    }

    /**
     * Calculates the price of the order.
     *
     * @retun total order
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        //price of 1 cup of coffee
        int basePrice = 5;

        //add $1 if user wants whipped cream
        if (addWhippedCream){
            basePrice = basePrice + 1;
        }

        //add $2 if user wants chocolate
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }

    /**
     *@param name of user input
     * @param price of the order
     * @param addWhippedCream is whether or not the user wants to add whipped cream topping
     * @param addChocolate is whether or not the users wants to add chocolate topping
     * @return
     */

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream) + addWhippedCream;
        priceMessage += "\n" + getString(R.string.order_summary_chocolate) + addChocolate;
        priceMessage += "\n" + getString(R.string.order_summary_quantity) + quantity;
        priceMessage += "\n" + getString(R.string.order_summary_total) + price;
            NumberFormat.getCurrencyInstance().format(price);
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int numberOfCoffee) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffee);
    }


}
