package com.edwinacubillos.agendasqlite;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ContactosSQLiteHelper contactosSQLiteHelper;
    private SQLiteDatabase dbContactos;

    private EditText eNombre, eTelefono, eCorreo;

    private ContentValues dataBD;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eNombre =findViewById(R.id.eNombre);
        eCorreo = findViewById(R.id.eCorreo);
        eTelefono = findViewById(R.id.eTelefono);

        contactosSQLiteHelper = new ContactosSQLiteHelper(
                this,
                "contactosBD",
                null,
                1);

        dbContactos = contactosSQLiteHelper.getWritableDatabase();
    }

    public void guardarClicked(View view) {
        dataBD = new ContentValues();
        dataBD.put("nombre",eNombre.getText().toString());
        dataBD.put("telefono", eTelefono.getText().toString());
        dataBD.put("correo",eCorreo.getText().toString());

        dbContactos.insert("contactos",null,dataBD);

        Toast.makeText(this, "Contacto almacenado", Toast.LENGTH_SHORT).show();
        cleanWidgtes();
    }

    private void cleanWidgtes() {
        eNombre.setText("");
        eCorreo.setText("");
        eTelefono.setText("");
    }

    public void buscarClicked(View view) {
        Cursor c = dbContactos.rawQuery(
                "SELECT * FROM contactos WHERE nombre = '"+eNombre.getText().toString()+"'",
                null);

        if(c.moveToFirst()){
            eCorreo.setText(c.getString(3));
            eTelefono.setText(c.getString(2));
        } else{
            Toast.makeText(this,"Contacto no encontrado",Toast.LENGTH_SHORT).show();
        }
    }

    public void eliminarClicked(View view) {
        dbContactos.delete("contactos",
                "nombre = '"+eNombre.getText().toString()+"'",
                null);

    }

    public void actualizarClicked(View view) {
        dataBD = new ContentValues();
        dataBD.put("telefono",eTelefono.getText().toString());
        dataBD.put("correo",eCorreo.getText().toString());

        dbContactos.update("contactos",
                dataBD,
                "nombre='"+eNombre.getText().toString()+"'",
                null);

        cleanWidgtes();
    }

    public void listarClicked(View view) {
        Intent i =  new Intent(this, ListaActivity.class);
        startActivity(i);
    }
}
