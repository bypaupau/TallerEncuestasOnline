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
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (1, '¿Cómo calificarías la atención de secretaría general?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (2, '¿Cómo calificaría nuestra selección de docentes?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (3, '¿Considera el personal de mantenimiento efectivo y amable?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (4, '¿La institución cumplió sus expectativas?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (5, '¿Cómo calificaría la infraestructura de la institución?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (6, '¿Cómo calificaría el Internet de la institución?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (7, '¿Cómo calificaría el cuidado dado a las instalaciones?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (8, '¿Qué tan fácil es usar la plataforma virtual de la institución?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (9, '¿Cómo calificaría el servicio de cafetería?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (10, '¿Está conforme con los horarios de clase asignados?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (11, '¿Recibe la información importante a tiempo por parte de la institución?')");

        db.execSQL("INSERT INTO " + SurveyContract.PreguntasEntry.TABLE_NAME +
                " (" + SurveyContract.PreguntasEntry.COLUMN_ID_PREG + ", " +
                SurveyContract.PreguntasEntry.COLUMN_TEXTO + ") VALUES (12, '¿Recomendaría esta institución a otra persona?')");

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
