package com.edwinacubillos.agendasqlite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Contacto> listContactos;
    private ContactosAdapter contactosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        listContactos = new ArrayList<>();

        contactosAdapter = new ContactosAdapter(listContactos);
        recyclerView.setAdapter(contactosAdapter);

        loadData();

    }

    private void loadData() {
        ContactosSQLiteHelper contactosSQLiteHelper;
        SQLiteDatabase dbContactos;

        contactosSQLiteHelper = new ContactosSQLiteHelper(
                this,
                "contactosBD",
                null,
                1);

        dbContactos = contactosSQLiteHelper.getWritableDatabase();

        Cursor c = dbContactos.rawQuery(
                "SELECT * FROM contactos",
                null);

        if(c.moveToFirst()){
            do{
                Contacto contacto = new Contacto(
                        c.getInt(0),
                        c.getString(1),
                        c.getString(2),
                        c.getString(3));
                listContactos.add(contacto);
            }while (c.moveToNext());
            contactosAdapter.notifyDataSetChanged();
        } else{
            Toast.makeText(this,"No hay contactos",Toast.LENGTH_SHORT).show();
        }
    }
}
