package com.example.shoppinglist;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity {
    public static final String filename = "db.json";

   static MainActivity instance;
    private final Main main = new Main();


    public static MainActivity getInstance(){
        return instance;
    }

    @Override
    protected void onDestroy() {
        Utils.writeToFile(main.items,filename,getApplicationContext());
        super.onDestroy();
    }
    @Override
    protected void onPause() {
        Utils.writeToFile(main.items,filename,getApplicationContext());
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        main.items = Utils.readFromFile(filename,getApplicationContext());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;
        final Button main_btn = findViewById(R.id.MainButton);
        final Button clear_btn = findViewById(R.id.ClearButton);
        final Button edit_btn = findViewById(R.id.EditButton);
        final Button read_btn = findViewById(R.id.read);
        final LinearLayout list = findViewById(R.id.List);
        final ConstraintLayout mainLayout = findViewById(R.id.mainLayout);
        mainLayout.setBackgroundResource(R.drawable.customborderwhite);
        main.refreshList(list, getApplicationContext());
        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonShowPopupWindowClick(v,list);
            }
        });

        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Cleared");
                //Utils.writeToFile(main.items,filename,getApplicationContext());
                main.clearList(list,getApplicationContext());
            }
        });

        read_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.readFromFile(filename,getApplicationContext());
                main.refreshList(list,getApplicationContext());
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(main.editable) {
                    edit_btn.setTextColor(Color.parseColor("#000000"));
                    mainLayout.setBackgroundResource(R.drawable.customborderwhite);
                    main.editable = false;
                }
                else {
                    main.editable = true;
                    mainLayout.setBackgroundResource(R.drawable.customborderred);
                    edit_btn.setTextColor(Color.parseColor("#d71919"));
                }
//                onButtonShowPopupWindowClickEdit(v,list,"tomato");
            }
        });

    }

    public void onButtonShowPopupWindowClick(View view,final LinearLayout list) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.add_popup, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -200);

        final Spinner qunatity_spinner = popupView.findViewById(R.id.qantitySpinner);
        final EditText itemInput = popupView.findViewById(R.id.ItemNameInput);
        final Button add_popup_btn = popupView.findViewById(R.id.AddItemButton);

        add_popup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(qunatity_spinner.getSelectedItem().toString());
                String itemName = itemInput.getText().toString();
                if(!itemName.equals("")) {
                    main.addToList(list, getApplicationContext(), new Item(itemName, quantity));
                    Utils.writeToFile(main.items,filename,getApplicationContext());
                }
                popupWindow.dismiss();
            }
        });

    }
    public void onButtonShowPopupWindowClickEdit(View view, final Context context, final LinearLayout list, final Item i) {
        //final Item i = findItem(name);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        final View popupView = inflater.inflate(R.layout.edit_popup, null);

        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, -200);

        final Spinner quantity_spinner = popupView.findViewById(R.id.qantitySpinner);
        final EditText itemInput = popupView.findViewById(R.id.ItemNameInput);
        final Button add_popup_btn = popupView.findViewById(R.id.EditButton);
        final Button del_popup_btn = popupView.findViewById(R.id.DeleteButton);
        if(i ==null){
            popupWindow.dismiss();
            System.out.println("Nothing found : " +i.getName());
            return;
        }
        itemInput.setText(i.getName());
        quantity_spinner.setSelection((i.getQuanity()-1));
        main.removeItem(i);

        add_popup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(quantity_spinner.getSelectedItem().toString());
                String itemName = itemInput.getText().toString();
                i.setName(itemName);
                i.setQuanity(quantity);
                main.addToList(list,context,i);
                main.refreshList(list,context);
                popupWindow.dismiss();
            }
        });

        del_popup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                main.refreshList(list,context);
                popupWindow.dismiss();
            }
        });
    }


}
