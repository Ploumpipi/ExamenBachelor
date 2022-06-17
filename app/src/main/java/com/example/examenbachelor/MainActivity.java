package com.example.examenbachelor;
import com.example.examenbachelor.bean.Note;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;


import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    private HelperNFT dbNFT;
    private Button btnConfirm;
    private Button btnCreateAccount;
    private EditText Pseudo;
    private EditText Password;
    private static final int MENU_ITEM_VIEW = 111;
    private static final int MENU_ITEM_EDIT = 222;
    private static final int MENU_ITEM_CREATE = 333;
    private static final int MENU_ITEM_DELETE = 444;

    private static final int MY_REQUEST_CODE = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbNFT = new HelperNFT(this);
        dbNFT.createDefaultUsersIfNeed();

        this.btnConfirm = (Button) findViewById(R.id.connexion);
        this.btnCreateAccount = (Button) findViewById(R.id.inscription);

        this.Pseudo = (EditText) findViewById(R.id.Pseudo);
        this.Password = (EditText) findViewById(R.id.MotDePasse);


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Inscription.class);
                startActivity(intent);
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pseudo = Pseudo.getText().toString();
                String pwd = Password.getText().toString();

                if(pseudo.equals("")||pwd.equals("")) {
                    Toast.makeText(MainActivity.this, "Veuillez compléter tous les champs", Toast.LENGTH_SHORT).show();
                }else{
                        /*Boolean checkInfos = dbNFT.checkUsersInformation(pseudo,pwd);
                        if(checkInfos == true){*/
                    if(pseudo.equals("aze") && pwd.equals("aze")){
                                Toast.makeText(MainActivity.this, "Connexion réussie", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(MainActivity.this, AffichageNFT.class);
                                startActivity(intent);
                            }else{
                                //Toast.makeText(MainActivity.this, "Problème de connexion", Toast.LENGTH_SHORT).show();
                                Toast.makeText(MainActivity.this, pwd + pseudo, Toast.LENGTH_SHORT).show();
                        }
                        }
                    }
            });

        Helper db = new Helper(this);
        db.createDefaultNotesIfNeed();


        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()   // or .detectAll() for all detectable problems
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .detectLeakedClosableObjects()
                .penaltyLog()
                //.penaltyDeath()
                .build());


        try {
            URL url = new URL("https://api.coingecko.com/api/v3/simple/price?ids=ethereum&vs_currencies=eur%2Cbtc");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            Log.i("mec", "je suis avant");
            conn.getResponseCode();
            if(conn.getResponseCode() == HttpsURLConnection.HTTP_OK){
                Log.i("Hey", "Mec cool");
                InputStream inputStream = null;
                try {
                    inputStream = conn.getInputStream();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                // ...que l'on transforme ici en String par simplicité d'usage (note :
                // il peut s'agit d'autre chose qu'un String pour
                // d'autres webservices, comme des images)
                String data = readStringData(inputStream);

                Log.i("Request", data);

                JSONObject js = new JSONObject(data);

                Log.i("btc", js.getJSONObject("ethereum").getString("btc"));
                Log.i("eur", js.getJSONObject("ethereum").getString("eur"));

            }
            else {
                Log.i("Hey", "ça marche pas");
                String response = "FAILED"; // See documentation for more info on response handling
            }

        } catch (IOException | JSONException e) {
            //TODO Handle problems..
        }
        //Log.i("Testbdd", db.getNote(1).toString());

    }



    private String readStringData(InputStream stream)  {
        BufferedReader reader = null;
        StringBuilder result = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(stream, "UTF-8"));

            String line;
            while((line = reader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
            //return null;

        } catch (IOException e) {
            Log.e(getClass().getSimpleName(), "not working", e);

        } finally {
            // On ferme tout les flux dans tout les cas
            if(reader != null){
                try {
                    reader.close();

                } catch (IOException exp2) {
                    Log.e(getClass().getSimpleName(), "not working again", exp2);
                }
            }
        }
        return null;
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo menuInfo)    {

        super.onCreateContextMenu(menu, view, menuInfo);
        menu.setHeaderTitle("Select The Action");

        // groupId, itemId, order, title
        menu.add(0, MENU_ITEM_VIEW , 0, "View Note");
        menu.add(0, MENU_ITEM_CREATE , 1, "Create Note");
        menu.add(0, MENU_ITEM_EDIT , 2, "Edit Note");
        menu.add(0, MENU_ITEM_DELETE, 4, "Delete Note");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo
                info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        //final Note selectedNote = (Note) this.listView.getItemAtPosition(info.position);

        if(item.getItemId() == MENU_ITEM_VIEW){
            //Toast.makeText(getApplicationContext(),selectedNote.getNoteContent(),Toast.LENGTH_LONG).show();
        }
        else if(item.getItemId() == MENU_ITEM_CREATE){
            Intent intent = new Intent(this, AffichageNFT.class);

            // Start AddEditNoteActivity, (with feedback).
            this.startActivityForResult(intent, MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_EDIT ){
            Intent intent = new Intent(this, AffichageNFT.class);
            //intent.putExtra("note", selectedNote);

            // Start AddEditNoteActivity, (with feedback).
            this.startActivityForResult(intent,MY_REQUEST_CODE);
        }
        else if(item.getItemId() == MENU_ITEM_DELETE){
            // Ask before deleting.
            new AlertDialog.Builder(this)
                    //.setMessage(selectedNote.getNoteTitle()+". Are you sure you want to delete?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //deleteNote(selectedNote);
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
        else {
            return false;
        }
        return true;
    }

    // Delete a record
    private void deleteNote(Note note)  {
        Helper db = new Helper(this);
        db.deleteNote(note);
        //this.noteList.remove(note);
        // Refresh ListView.
        //this.listViewAdapter.notifyDataSetChanged();
    }

    // When AddEditNoteActivity completed, it sends feedback.
    // (If you start it using startActivityForResult ())
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == MY_REQUEST_CODE) {
            boolean needRefresh = data.getBooleanExtra("needRefresh", true);
            // Refresh ListView
            if (needRefresh) {
                //this.noteList.clear();
                Helper db = new Helper(this);
                List<Note> list = db.getAllNotes();
                //this.noteList.addAll(list);


                // Notify the data change (To refresh the ListView).
                //this.listViewAdapter.notifyDataSetChanged();
            }
        }
    }

}