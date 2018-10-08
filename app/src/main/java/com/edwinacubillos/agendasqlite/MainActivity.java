package com.edwinacubillos.agendasqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

  //  private ContactosSQLiteHelper contactosSQLiteHelper;
  //  private SQLiteDatabase dbContactos;

    private EditText eNombre, eTelefono, eCorreo;

    private ContentValues dataBD;

    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*      contactosSQLiteHelper = new ContactosSQLiteHelper(
                this,
                "contactosBD",
                null,
                1);

        dbContactos = contactosSQLiteHelper.getWritableDatabase();*/

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("contactos");

        eNombre =findViewById(R.id.eNombre);
        eCorreo = findViewById(R.id.eCorreo);
        eTelefono = findViewById(R.id.eTelefono);
    }

    public void guardarClicked(View view) {
        Contacto contacto = new Contacto(myRef.push().getKey(),
                eNombre.getText().toString(),
                eTelefono.getText().toString(),
                eCorreo.getText().toString());

        myRef.child(contacto.getId()).setValue(contacto);
        Toast.makeText(this, "Contacto almacenado", Toast.LENGTH_SHORT).show();
     /*   dataBD = new ContentValues();
        dataBD.put("nombre",eNombre.getText().toString());
        dataBD.put("telefono", eTelefono.getText().toString());
        dataBD.put("correo",eCorreo.getText().toString());

        dbContactos.insert("contactos",null,dataBD);*/

        cleanWidgtes();
    }

    private void cleanWidgtes() {
        eNombre.setText("");
        eCorreo.setText("");
        eTelefono.setText("");
    }

    public void buscarClicked(View view) {
     /*   Cursor c = dbContactos.rawQuery(
                "SELECT * FROM contactos WHERE nombre = '"+eNombre.getText().toString()+"'",
                null);

        if(c.moveToFirst()){
            eCorreo.setText(c.getString(3));
            eTelefono.setText(c.getString(2));
        } else{
            Toast.makeText(this,"Contacto no encontrado",Toast.LENGTH_SHORT).show();
        }*/
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("-LOKmuZlEBT4Q6g-IwhK").exists()){
                    Contacto contacto = dataSnapshot.child("-LOKmuZlEBT4Q6g-IwhK").getValue(Contacto.class);
                    eNombre.setText(contacto.getNombre());
                    Log.d("dato",dataSnapshot.child("-LOKmuZlEBT4Q6g-IwhK").getValue().toString());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void eliminarClicked(View view) {
    /*    dbContactos.delete("contactos",
                "nombre = '"+eNombre.getText().toString()+"'",
                null);*/
        myRef.child("-LOKmuZlEBT4Q6g-IwhK").removeValue();
    }

    public void actualizarClicked(View view) {
     /*   dataBD = new ContentValues();
        dataBD.put("telefono",eTelefono.getText().toString());
        dataBD.put("correo",eCorreo.getText().toString());

        dbContactos.update("contactos",
                dataBD,
                "nombre='"+eNombre.getText().toString()+"'",
                null);
*/
        cleanWidgtes();
    }

    public void listarClicked(View view) {
        Intent i =  new Intent(this, ListaActivity.class);
        startActivity(i);
    }
}
