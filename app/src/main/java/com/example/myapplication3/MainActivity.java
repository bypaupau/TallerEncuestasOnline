package com.example.myapplication3;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private LinearLayout contenedorPreguntas;
    private ArrayList<EditText> listaRespuestas = new ArrayList<>();
    private ArrayList<Integer> listaIdPreguntas = new ArrayList<>();
    SurveyDbHelper dbHelper = new SurveyDbHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main); // ESTA LINEA ES LA QUE YA VIENE POR DEFAULT

        contenedorPreguntas = findViewById(R.id.contenedorPreguntas);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // se le suma el padding de 16dp del xml para que no quede pegado a la pantalla
            int extra = (int) (16 * getResources().getDisplayMetrics().density);
            v.setPadding(systemBars.left + extra, systemBars.top + extra, systemBars.right + extra, systemBars.bottom + extra);
            return insets;
        });

        cargarPreguntas();
    }

    // lee las preguntas de la base y arma la pantalla
    private void cargarPreguntas() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                SurveyContract.PreguntasEntry.TABLE_NAME,
                null,
                null,
                null, null, null,
                SurveyContract.PreguntasEntry.COLUMN_ID_PREG + " ASC"
        );

        while (cursor.moveToNext()) {
            int idPregunta = cursor.getInt(cursor.getColumnIndexOrThrow(SurveyContract.PreguntasEntry.COLUMN_ID_PREG));
            String texto = cursor.getString(cursor.getColumnIndexOrThrow(SurveyContract.PreguntasEntry.COLUMN_TEXTO));

            TextView txtPregunta = new TextView(this);
            txtPregunta.setText(texto);
            txtPregunta.setTextSize(16);
            txtPregunta.setPadding(0, 16, 0, 0);

            EditText txtRespuesta = new EditText(this);
            txtRespuesta.setHint("Escribe tu respuesta");

            // margen para separar cada pregunta de la siguiente
            LinearLayout.LayoutParams parametros = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            parametros.setMargins(0, 0, 0, 12);

            contenedorPreguntas.addView(txtPregunta);
            contenedorPreguntas.addView(txtRespuesta, parametros);

            listaRespuestas.add(txtRespuesta);
            listaIdPreguntas.add(idPregunta);
        }
        cursor.close();
        db.close();
    }

    // boton guardar encuesta, inserta una fila por cada respuesta
    public void guardarEncuesta(View vista) {
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
        String fecha = formato.format(new Date());

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (int i = 0; i < listaRespuestas.size(); i++) {
            ContentValues values = new ContentValues();
            values.put(SurveyContract.RespuestasEntry.COLUMN_ID_PREG_FK, listaIdPreguntas.get(i));
            values.put(SurveyContract.RespuestasEntry.COLUMN_RESPUESTA, listaRespuestas.get(i).getText().toString());
            values.put(SurveyContract.RespuestasEntry.COLUMN_FECHA, fecha);
            db.insert(SurveyContract.RespuestasEntry.TABLE_NAME, null, values);
        }
        db.close();

        Toast.makeText(getApplicationContext(), "Encuesta guardada", Toast.LENGTH_LONG).show();

        // se limpian los campos para que quede lista para otra persona
        for (EditText campo : listaRespuestas) {
            campo.setText("");
        }
    }

    public void verHistorial(View vista) {
        Intent intent = new Intent(this, Listado.class);
        startActivity(intent);
    }
}
