package com.boss.milos.pricecounter;

import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by MiloS on 31.08.2016.
 */
public class ViewBuilder {

    private  ScrollingActivity scrollingActivity;
    private int countOfDrinks = 5;
    private ArrayList<Element> elements;

    public ViewBuilder(ScrollingActivity scrollingActivity, ArrayList<Element> elements) {
        this.scrollingActivity = scrollingActivity;
        this.elements = elements;
    }

    /**
     * Method to initialize the whole view, calls other methods to build rows and table layouts
     */
    public void init() {
        LinearLayout itemsLinearLayout = (LinearLayout)scrollingActivity.findViewById(R.id.itemsLinearLayout);
        LinearLayout headerLinearLayout = (LinearLayout)scrollingActivity.findViewById(R.id.headerLinearLayout);

        headerLinearLayout.addView(createFinalPriceView());
        headerLinearLayout.addView(createAddButton());

        for(int i = 0; i < countOfDrinks; i++) {
            itemsLinearLayout.addView(createTableLayoutView(i));
        }
    }

    public View createTableLayoutView(int id) {
        elements.add(new Element());

        TableLayout tableLayout = new TableLayout(scrollingActivity);
        tableLayout.setId(id);
        tableLayout.setStretchAllColumns(true);

        tableLayout.addView(createFirstRow());
        tableLayout.addView(createSecondRow());
        tableLayout.addView(createHorizontalLine());

        return tableLayout;
    }

    /**
     * Creates a TextView that has the final price label and actual price
     * @return
     */
    public View createFinalPriceView() {
        TextView finalPriceView = new TextView(scrollingActivity);
        finalPriceView.setText("Final price: 0.0");
        finalPriceView.setGravity(Gravity.CENTER);
        finalPriceView.setTextSize(20);
        finalPriceView.setId(R.id.final_price);

        return finalPriceView;
    }

    /**
     * Creates a Button that manages adding new items to the list
     * @return the add item button
     */
    public View createAddButton() {
        Button button = new Button(scrollingActivity);
        button.setText("Add new item");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countOfDrinks++;

                LinearLayout linearLayout = (LinearLayout)scrollingActivity.findViewById(R.id.itemsLinearLayout);
                linearLayout.addView(createTableLayoutView(countOfDrinks));
            }
        });

        return button;
    }

    /**
     * Creates a delete Button that deletes the item from the list
     * @return the delete item button
     */
    public View createDeleteButton() {
        final Button deleteButton = new Button(scrollingActivity);
        deleteButton.setText("x");
        deleteButton.setId(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                countOfDrinks--;
                int index;
                try {
                    index = getViewIndex(deleteButton);
                } catch (ChildNotFoundException e) {
                    e.printStackTrace();
                    return;
                }

                elements.remove(index);

                LinearLayout linearLayout = (LinearLayout)scrollingActivity.findViewById(R.id.itemsLinearLayout);
                linearLayout.removeView(linearLayout.getChildAt(index));
                updateFinalPriceView();
            }
        });

        return deleteButton;
    }

    /**
     * Creates an item name EditText that saves the item name of the item
     * @return the item name edit text
     */
    public View createItemNameEditText() {
        final EditText itemName = new EditText(scrollingActivity);
        TableRow.LayoutParams layoutParams = new TableRow.LayoutParams();
        layoutParams.span = 3;
        itemName.setHint("Item Name");
        itemName.setLayoutParams(layoutParams);
        itemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                int index;
                try {
                    index = getViewIndex(itemName);
                } catch (ChildNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
                elements.get(index).setItemName(editable.toString());
            }
        });

        return itemName;
    }

    /**
     * Creates the label TextView
     * @return the text view label
     */
    public View createPriceLabelTextView() {
        TextView priceLabel = new TextView(scrollingActivity);
        priceLabel.setText("Price: ");
        priceLabel.setGravity(Gravity.CENTER);

        return priceLabel;
    }

    /**
     * Creates a price EditText that saves the actual price of an item
     * @return the price edit text
     */
    public View createPriceEditText() {
        final EditText price = new EditText(scrollingActivity);
        price.setHint("0.0");
        price.setGravity(Gravity.CENTER);
        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                int index;
                try {
                    index = getViewIndex(price);
                } catch (ChildNotFoundException e) {
                    e.printStackTrace();
                    return;
                }
                Element element = elements.get(index);
                try {
                    element.setPrice(Double.parseDouble(editable.toString()));
                } catch (NumberFormatException n){
                    element.setPrice(0.0);
                }

                updatePriceForDrinksView(price, element);
            }
        });

        return price;
    }

    /**
     * Creates the first row of a table layout that will consist of Views that are added inside the method
     * @return the first row of a table layout
     */
    public View createFirstRow() {
        TableRow tableRow = new TableRow(scrollingActivity);

        tableRow.addView(createDeleteButton());
        tableRow.addView(createItemNameEditText());
        tableRow.addView(createPriceLabelTextView());
        tableRow.addView(createPriceEditText());

        return tableRow;
    }

    /**
     * Creates a count label TextView
     * @return the count label text view
     */
    public View createCountLabelTextView() {
        TextView countLabel = new TextView(scrollingActivity);
        countLabel.setText("Count: ");
        countLabel.setGravity(Gravity.CENTER);

        return countLabel;
    }

    /**
     * Creates the plus Button which is for increasing the count of an item
     * @return the plus button
     */
    public View createPlusButton() {
        final Button plusButton = new Button(scrollingActivity);
        plusButton.setText("+");
        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Element element = null;
                try {
                    element = elements.get(getViewIndex(plusButton));
                } catch (ChildNotFoundException e) {
                    e.printStackTrace();
                }
                element.increaseCount();
                updateCountView(view, element.getCount());
                updatePriceForDrinksView(view, element);
            }
        });

        return plusButton;
    }

    /**
     * Creates the count TextView that shows actual count of items for single element
     * @return the count text view
     */
    public View createCountTextView() {
        TextView count = new TextView(scrollingActivity);
        count.setText("0");
        count.setGravity(Gravity.CENTER);
        count.setTextSize(16);
        count.setId(R.id.item_count_text);

        return count;
    }

    /**
     * Creates the minus Button which is for decreasing the count of an item
     * @return the minus button
     */
    public View createMinusButton() {
        final Button minusButton = new Button(scrollingActivity);
        minusButton.setText("-");
        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Element element = null;
                try {
                    element = elements.get(getViewIndex(minusButton));
                } catch (ChildNotFoundException e) {
                    e.printStackTrace();
                }
                element.decreaseCount();
                updateCountView(view, element.getCount());
                updatePriceForDrinksView(view,element);
            }
        });

        return minusButton;
    }

    /**
     * Creates the total price TextView label
     * @return the total price label
     */
    public View createTotalLabelTextView() {
        TextView totalLabel = new TextView(scrollingActivity);
        totalLabel.setText("Total:");
        totalLabel.setGravity(Gravity.CENTER);
        return totalLabel;
    }

    /**
     * Creates the total price TextView
     * @return the total price text view
     */
    public View createTotalPriceTextView() {
        TextView totalPrice = new TextView(scrollingActivity);
        totalPrice.setText("0.0");
        totalPrice.setGravity(Gravity.CENTER);
        totalPrice.setTextSize(16);
        totalPrice.setId(R.id.item_total_price_text);
        return totalPrice;
    }

    /**
     * Creates the second row of a table layout that will consist of Views that are added inside the method
     * @return
     */
    public View createSecondRow() {
        TableRow tableRow = new TableRow(scrollingActivity);

        tableRow.addView(createCountLabelTextView());
        tableRow.addView(createPlusButton());
        tableRow.addView(createCountTextView());
        tableRow.addView(createMinusButton());
        tableRow.addView(createTotalLabelTextView());
        tableRow.addView(createTotalPriceTextView());

        return tableRow;
    }

    /**
     * Creates a View that is a simple horizontal line
     * @return the horizontal line view
     */
    public View createHorizontalLine() {
        View view = new View(scrollingActivity);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2));
        view.setBackgroundColor(Color.parseColor("#FF909090"));

        return view;
    }

    /**
     * Updates price View for an item in a list either when the count was changed or the price was changed
     * @param view the View that caused the change of price: either a TextView with the price or a +/- button
     * @param element the element whose price changed
     */
    public void updatePriceForDrinksView(View view, Element element) {
        Double total = element.getPrice() * element.getCount();

        TableRow tableRow = (TableRow)view.getParent();
        TableLayout tableLayout = (TableLayout) tableRow.getParent();
        TextView targetTextView = (TextView) tableLayout.findViewById(R.id.item_total_price_text);

        targetTextView.setText(total.toString());
        updateFinalPriceView();
    }

    /**
     * Updates the actual count of an item
     * @param view the View that caused the change of count: either + or - button
     * @param countOfDrinks the count to change the text of a view to
     */
    public void updateCountView(View view, int countOfDrinks) {
        TextView targetView = (TextView) ((TableRow) (view.getParent())).findViewById(R.id.item_count_text);
        targetView.setText(Integer.toString(countOfDrinks));
        updateFinalPriceView();
    }

    /**
     * Finds an index (order of element (TableLayout) inside of a listview) some View, the View Must be TableLayout or its child (does not have to be direct child)
     * @param view the View we want to know the index for
     * @return the index of element (TableLayout) inside the list view
     * @throws ChildNotFoundException if the index could not be computed
     */
    public int getViewIndex(View view) throws ChildNotFoundException {
        if(view == null) {
            throw new IllegalArgumentException("View is null");
        }else{
            while(!(view instanceof TableLayout)) {
                view = (View)view.getParent();
            }

            LinearLayout linearLayout = (LinearLayout)view.getParent();
            for(int i = 0; i < linearLayout.getChildCount(); i++) {
                View child = linearLayout.getChildAt(i);

                if(view.equals(child)) {
                    return i; //-2 because first two views of linearLayout are add button and final price view
                }
            }
        }
        throw new ChildNotFoundException("Child " + view.toString() + " not found.");
    }

    /**
     * Updates the final price view
     */
    public void updateFinalPriceView() {
        TextView finalPrice = (TextView) scrollingActivity.findViewById(R.id.final_price);
        Double total = 0.0;

        for(int i = 0; i < countOfDrinks; i++) {
            total += elements.get(i).getPrice() * elements.get(i).getCount();
        }

        finalPrice.setText("Final price: " + Double.toString(total));
    }
}
