package com.edwinacubillos.agendasqlite;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactosAdapter extends RecyclerView.Adapter<ContactosAdapter.ContactosViewHolder> {

    private ArrayList<Contacto> contactoList;

    public ContactosAdapter(ArrayList<Contacto> contactoList) {
        this.contactoList = contactoList;
    }

    @NonNull
    @Override
    public ContactosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.list_item, parent,false);

        return new ContactosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactosViewHolder holder, int position) {
        Contacto contacto = contactoList.get(position);
        holder.bindContactos(contacto);
    }

    @Override
    public int getItemCount() {
        return contactoList.size();
    }

    public class ContactosViewHolder extends RecyclerView.ViewHolder{

        private TextView tNombre, tCorreo, tTelefono;

        public ContactosViewHolder(View itemView) {
            super(itemView);
            tNombre = itemView.findViewById(R.id.tNombre);
            tCorreo = itemView.findViewById(R.id.tCorreo);
            tTelefono = itemView.findViewById(R.id.tTelefono);
        }

        public void bindContactos(Contacto contacto){
            tNombre.setText(contacto.getNombre());
            tCorreo.setText(contacto.getCorreo());
            tTelefono.setText(contacto.getTelefono());
        }
    }

}
