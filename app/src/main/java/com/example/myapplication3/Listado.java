package com.example.myapplication3;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Listado extends AppCompatActivity {

    private ListView lvHistorial;
    private ArrayList<String> listaResumen = new ArrayList<>();
    SurveyDbHelper dbHelper = new SurveyDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado);

        lvHistorial = findViewById(R.id.lvHistorial);

        cargarHistorial();

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaResumen);
        lvHistorial.setAdapter(adapter);
    }

    // trae todas las respuestas y las junta por fecha, cada fecha es una encuesta
    private void cargarHistorial() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                SurveyContract.RespuestasEntry.TABLE_NAME,
                null,
                null,
                null, null, null,
                SurveyContract.RespuestasEntry.COLUMN_FECHA + " ASC"
        );

        String fechaActual = "";
        String resumen = "";

        while (cursor.moveToNext()) {
            String fecha = cursor.getString(cursor.getColumnIndexOrThrow(SurveyContract.RespuestasEntry.COLUMN_FECHA));
            int idPreg = cursor.getInt(cursor.getColumnIndexOrThrow(SurveyContract.RespuestasEntry.COLUMN_ID_PREG_FK));
            String respuesta = cursor.getString(cursor.getColumnIndexOrThrow(SurveyContract.RespuestasEntry.COLUMN_RESPUESTA));
            String textoPregunta = obtenerTextoPregunta(idPreg);

            if (!fecha.equals(fechaActual)) {
                // si ya habia una encuesta armandose, se guarda antes de empezar la nueva
                if (!fechaActual.equals("")) {
                    listaResumen.add(resumen);
                }
                fechaActual = fecha;
                resumen = fecha + "\n";
            }
            resumen = resumen + textoPregunta + ": " + respuesta + "\n";
        }
        // se agrega la ultima encuesta que quedo armada
        if (!resumen.equals("")) {
            listaResumen.add(resumen);
        }

        cursor.close();
        db.close();
    }

    // busca el texto de una pregunta a partir de su id
    private String obtenerTextoPregunta(int idPregunta) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String texto = "";
        String selection = SurveyContract.PreguntasEntry.COLUMN_ID_PREG + " = ?";
        String[] selectionArgs = { idPregunta + "" };

        Cursor cursor = db.query(
                SurveyContract.PreguntasEntry.TABLE_NAME,
                null,
                selection,
                selectionArgs, null, null, null
        );
        if (cursor.moveToFirst()) {
            texto = cursor.getString(cursor.getColumnIndexOrThrow(SurveyContract.PreguntasEntry.COLUMN_TEXTO));
        }
        cursor.close();
        db.close();
        return texto;
    }

    public void regresar(View vista) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
