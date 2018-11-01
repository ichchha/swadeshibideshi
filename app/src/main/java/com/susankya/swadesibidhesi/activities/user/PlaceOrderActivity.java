package com.susankya.swadesibidhesi.activities.user;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.susankya.swadesibidhesi.R;

import org.apmem.tools.layouts.FlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlaceOrderActivity extends AppCompatActivity {
    @BindView(R.id.sizeLayout)LinearLayout sizeLayout;
    @BindView(R.id.itemSizeLayout)FlowLayout itemSizeLayout;
    @BindView(R.id.toolbar)Toolbar toolbar;
    @BindView(R.id.btnNext)Button btnCheckOut;
    String selectedSize = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_shipping_address);
        ButterKnife.bind(this);
        setUpToolbar();
        initViews();

        //init();
    }

    private void setUpToolbar() {
        toolbar.setTitle("Checkout");
        toolbar.setTitleTextColor(getResources().getColor(R.color.toolbarIcons));
        setSupportActionBar(toolbar);

        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // close this activity as oppose to navigating up
        return false;
    }

    private void initViews() {
        List<String> sizeList = new ArrayList<>(Arrays.asList("Red", "Blue","Orange","Pink","Black","White","Grey"));
        setSizeLayout(sizeList);
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlaceOrderActivity.this, "Your order has been received:)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setSizeLayout(final List<String> sizeList) {
        itemSizeLayout.removeAllViews();
        try {
            if (sizeList.size() > 0) {
                for (int i = 0; i < sizeList.size(); i++) {
                    final TextView size = new TextView(this);
                    size.setText(sizeList.get(i));
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                        size.setBackground(getResources().getDrawable(R.drawable.border_grey));
                        size.setTextColor(getResources().getColor(R.color.secondaryText));
                    } else {
                        size.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_grey));
                        size.setTextColor(getResources().getColor(R.color.secondaryText));
                    }

                    // Change Border To accent If Selected
                    try {
                        if (selectedSize.equals(sizeList.get(i))) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                size.setBackground(getResources().getDrawable(R.drawable.border_blue));
                                size.setTextColor(getResources().getColor(R.color.colorGreen));
                            } else {
                                size.setBackgroundDrawable(getResources().getDrawable(R.drawable.border_blue));
                                size.setTextColor(getResources().getColor(R.color.colorGreen));
                            }
                        }
                    } catch (NullPointerException ignore) {
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        size.setTextColor(getResources().getColor(R.color.colorBlack, null));
                    } else {
                        size.setTextColor(getResources().getColor(R.color.colorBlack));
                    }
                    size.setFocusableInTouchMode(false);
                    size.setFocusable(true);
                    size.setClickable(true);
                    size.setTextSize(16);

                    int dpValue = 8; // margin in dips
                    float d = getResources().getDisplayMetrics().density;
                    int margin = (int) (dpValue * d); // margin in pixels
                    FlowLayout.LayoutParams params = new FlowLayout.LayoutParams(FlowLayout.LayoutParams.WRAP_CONTENT,
                            FlowLayout.LayoutParams.WRAP_CONTENT);
                    params.setMargins(margin, margin, 0, 0);
                    size.setLayoutParams(params);
                    itemSizeLayout.addView(size);

                    // Size Click Listener
                    size.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            TextView textView = (TextView) view;
                            selectedSize = textView.getText().toString();
//                            selectedColor = null;
//                            selectedItemPrice = null;
                            setSizeLayout(sizeList); // refresh to set selected

                            // Get Color of Selected Size & Set Color Layout
//                            List<String> colorList = db_handler.getColorBySelectedSize(product.getId(), selectedSize);
//                            setColorLayout(colorList);
                        }
                    });
                }
            } else {
                sizeLayout.setVisibility(View.GONE);
                selectedSize = "-";
            }
        } catch (NullPointerException e) {
            sizeLayout.setVisibility(View.GONE);
            selectedSize = "-";
        }
    }
}
