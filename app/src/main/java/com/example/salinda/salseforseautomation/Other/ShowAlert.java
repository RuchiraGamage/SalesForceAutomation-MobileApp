package com.example.salinda.salseforseautomation.Other;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salinda.salseforseautomation.activity.PaymentTabViewActivity;
import com.example.salinda.salseforseautomation.adapter.ProductAdapter;
import com.example.salinda.salseforseautomation.model.ProductsModel;
import com.example.salinda.salseforseautomation.session.ItemSession;
import com.example.salinda.salseforseautomation.session.ProductSession;

public class ShowAlert extends AppCompatActivity{
    Context context;
    private ProductSession productSession;
    private ProductAdapter adapter;
    double amount;

    public ShowAlert(Context context){
        this.context = context;
    }

    public void ShowLoginAlert(final String title, String message, boolean icon){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle(title);

        // Setting Dialog Message
        alertDialog.setMessage(message);

        // Setting Icon to Dialog
        alertDialog.setCancelable(icon);

        // Setting OK Button
        alertDialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(title.equals("App Starting Fail..")){
                    ((Activity)context).finish();
                }
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void BillCancelAlert(boolean icon){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);


        // Setting Dialog Title
        alertDialog.setTitle("Bill Cancel");

        // Setting Dialog Message
        alertDialog.setMessage("Not Empty Bill.. If You Want Cancel");

        // Setting Icon to Dialog
        alertDialog.setCancelable(icon);

        // Setting OK Button
        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                new PaymentTabViewActivity().BillCancel(context);
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void inputDialogAlert(final ProductsModel product, final String orderType) {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        final LinearLayout layout = new LinearLayout(context);
        final LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);

        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 2);

        final EditText et = new EditText(context);
        et.setInputType(InputType.TYPE_CLASS_PHONE);
        //etStr = et.getText().toString();
        final TextView tv1 = new TextView(context);
        tv1.setText("Rs. 0.00");

        LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        tv1Params.bottomMargin = 5;
        layout.addView(tv1, tv1Params);
        layout.addView(et, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));

        alertDialogBuilder.setView(layout);
        alertDialogBuilder.setTitle(product.getName());
        alertDialogBuilder.setMessage("Enter Quantity");

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                productSession = new ProductSession(context);
                try{
                    Editable text = et.getText();
                    String sText = text.toString();
                    amount = Integer.parseInt(String.valueOf(sText))*product.getPrice();
                    tv1.setText("Rs. "+String.valueOf(amount));
                }catch (Exception e){
                    tv1.setText("Rs. 0.00");
                }
                //tv1.setText(Integer.parseInt(text)*2);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });

        // Setting Negative "Cancel" Button
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        // Setting Positive "OK" Button
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                int qt = Integer.parseInt(String.valueOf(et.getText()));
                ItemSession itemSession = new ItemSession(context);
                if(product.getQuantity() > qt){
                    try{
                        if(qt > 0){
                            itemSession.setItem(product.getId(), qt, orderType, product.getName(), product.getBrand(), product.getPrice());
                            //itemSession.incrementItemCount();
                            dialog.cancel();
                        }else {
                            dialog.cancel();
                        }
                    }catch (Exception e){

                    }
                }else {
                    Toast.makeText(context, "Not Available that Quantity", Toast.LENGTH_SHORT).show();
                }

            }
        });
        alertDialogBuilder.show();
    }

    public void monthSelectAlert(){
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        final LinearLayout layout = new LinearLayout(context);
        final LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(parms);

        layout.setGravity(Gravity.CLIP_VERTICAL);
        layout.setPadding(2, 2, 2, 2);

        final EditText year = new EditText(context);
        year.setInputType(InputType.TYPE_CLASS_PHONE);
        year.setHint("yyyy");
        final EditText month = new EditText(context);
        month.setInputType(InputType.TYPE_CLASS_PHONE);
        month.setHint("mm");

        LinearLayout.LayoutParams tv1Params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        tv1Params.bottomMargin = 5;
        layout.addView(year, tv1Params);
        layout.addView(month, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        alertDialogBuilder.setView(layout);
        alertDialogBuilder.setTitle("Summery");
        alertDialogBuilder.setMessage("Enter Quantity");

        // Setting Negative "Cancel" Button
        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        // Setting Positive "OK" Button
        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialogBuilder.show();
    }
}
