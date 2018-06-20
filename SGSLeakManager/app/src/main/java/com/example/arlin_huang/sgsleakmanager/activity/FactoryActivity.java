package com.example.arlin_huang.sgsleakmanager.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.arlin_huang.sgsleakmanager.R;
import com.example.arlin_huang.sgsleakmanager.liteclass.Factories;

public class FactoryActivity extends AppCompatActivity {

    public static final String FACTORY_ID = "factory_id";
    public static final String FACTORY_NAME = "factory_name";
    public static final String FACTORY_TENENTNAME = "factory_tenantname";

    EditText name;
    EditText attr;
    EditText tenantName;
    private Toolbar factoolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        factoolbar = (Toolbar) findViewById(R.id.fac_toolbar);
//        setSupportActionBar(factoolbar);
        setContentView(R.layout.activity_factory);
        final Intent intent = getIntent();
        final Factories factory = (Factories) getIntent().getParcelableExtra("mFactory");

        name = (EditText) findViewById(R.id.name);
        attr = (EditText) findViewById(R.id.attr);
        tenantName = (EditText) findViewById(R.id.tenantName);

        if (factory != null) {
            name.setText(factory.getName());
            attr.setText(factory.getAttr());
            tenantName.setText(factory.getTenantName());

        } else {

        }
        final Button saveFactory = (Button) findViewById(R.id.save_factory);
        saveFactory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (factory != null) {

                    updateFactory(factory,intent);
                } else {
                    createFactory(intent);
                }
            }
        });


    }

    private void createFactory(final Intent intent) {
        Factories factories = new Factories();
        factories.setName(name.getText().toString());
        factories.setAttr(attr.getText().toString());
        factories.setTenantName(tenantName.getText().toString());
        factories.save();
        setResult(RESULT_OK, intent);
        finish();

    }

    private void updateFactory(final Factories factory,final Intent intent) {

        Factories factories = new Factories();
        factories.setName(name.getText().toString());
        factories.setAttr(attr.getText().toString());
        factories.setTenantName(tenantName.getText().toString());
        factories.update(factory.getId());
        setResult(RESULT_OK, intent);
        finish();

    }
}
