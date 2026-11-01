package br.com.autocheck.ui

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import br.com.autocheck.data.LocalDatabase
import br.com.autocheck.data.Vehicle
import br.com.autocheck.data.Consultation
import br.com.autocheck.domain.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/** Aplicativo Android nativo, sem rede, sem permissões sensíveis e sem dependências visuais externas. */
internal fun MainActivity.vehicles(body: LinearLayout) {
        heading(body,"Meus veículos","Tudo é armazenado apenas neste dispositivo.")
        body.addView(button("+ Adicionar veículo"){vehicleDialog()});gap(body,14)
        val vehicles = database.vehicles()
        if(vehicles.isEmpty()) notice(body,"Nenhum veículo cadastrado. O diagnóstico ainda pode ser usado sem um carro selecionado.")
        vehicles.forEach { v ->
            val p=panel()
            p.addView(label(v.name,19f,true));gap(p,6)
            p.addView(label("${v.engine.ifBlank { "Motor não informado" }}  •  ${v.mileage} km",13f,false,C.muted));gap(p,9)
            if(v.id==selectedVehicle) p.addView(label("● VEÍCULO ATIVO",11f,true,C.accent))
            gap(p,10)
            p.addView(button(if(v.id==selectedVehicle)"Selecionado" else "Usar este veículo",v.id!=selectedVehicle){
                selectedVehicle = v.id;prefs.edit().putLong("selected_vehicle",v.id).apply();render()
            });gap(p,8)
            p.addView(button("Editar dados",false){vehicleDialog(v)});gap(p,8)
            p.addView(button("Excluir veículo",false){
                AlertDialog.Builder(this).setTitle("Excluir veículo?")
                    .setMessage("O veículo será apagado. Relatórios já salvos continuarão com o nome registrado na consulta.")
                    .setNegativeButton("Cancelar",null).setPositiveButton("Excluir") {_,_->
                        database.deleteVehicle(v.id)
                        if(selectedVehicle==v.id) {selectedVehicle=-1;prefs.edit().remove("selected_vehicle").apply()}
                        render()
                    }.show()
            })
            body.addView(p);gap(body,11)
        }
        gap(body,4)
        notice(body,"Não armazenamos a placa do veículo nem solicitamos documentos pessoais na primeira versão.")
    }
internal fun MainActivity.vehicleDialog(v: Vehicle? = null) {
        val form = col(14)
        val make = field("Marca (ex.: Fiat)",v?.make ?: "")
        val model = field("Modelo (ex.: Siena)",v?.model ?: "")
        val year = field("Ano (ex.: 2010)",v?.year?.toString() ?: "",true)
        val engine = field("Motor (ex.: 1.0 Fire)",v?.engine ?: "")
        val mileage = field("Quilometragem (ex.: 125000)",v?.mileage?.toString() ?: "",true)
        listOf(make,model,year,engine,mileage).forEach {form.addView(it);gap(form,9)}
        val dialog = AlertDialog.Builder(this).setTitle(if(v==null)"Adicionar veículo" else "Editar veículo")
            .setView(ScrollView(this).apply{addView(form)})
            .setNegativeButton("Cancelar",null).setPositiveButton("Salvar",null).create()
        dialog.setOnShowListener {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                val y=year.text.toString().toIntOrNull()
                val km=mileage.text.toString().toIntOrNull()
                if(make.text.isBlank() || model.text.isBlank() || y==null || y !in 1950..2030 || km==null || km<0) {
                    Toast.makeText(this,"Informe marca, modelo, ano válido e quilometragem",Toast.LENGTH_LONG).show();return@setOnClickListener
                }
                val id=database.saveVehicle(make.text.toString(),model.text.toString(),y,engine.text.toString(),km,v?.id)
                if(selectedVehicle==-1L){selectedVehicle=id;prefs.edit().putLong("selected_vehicle",id).apply()}
                dialog.dismiss();render()
            }
        }
        dialog.show()
    }
