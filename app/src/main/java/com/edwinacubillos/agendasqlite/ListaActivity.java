package com.edwinacubillos.agendasqlite;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ListaActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Contacto> listContactos;
    private ContactosAdapter contactosAdapter;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

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
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        myRef.child("contactos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Contacto contacto = snapshot.getValue(Contacto.class);
                        listContactos.add(contacto);

                    }
                }
                contactosAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });













    /*    ContactosSQLiteHelper contactosSQLiteHelper;
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
        }*/
    }
}
