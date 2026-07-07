package com.example.myapplication3;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SurveyDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Encuestas.db";

    public SurveyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SurveyContract.SQL_CREATE_PREGUNTAS);
        db.execSQL(SurveyContract.SQL_CREATE_RESPUESTAS);

        // se insertan las preguntas fijas, el usuario no las escribe
        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (1, '¿Cómo califica la atención recibida?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (2, '¿Recomendaría nuestro servicio?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (3, '¿El personal fue amable?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (4, '¿La institución cumplió sus expectativas?')");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SurveyContract.SQL_DELETE_RESPUESTAS);
        db.execSQL(SurveyContract.SQL_DELETE_PREGUNTAS);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
