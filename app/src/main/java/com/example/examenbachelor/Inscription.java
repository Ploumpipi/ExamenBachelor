package com.example.examenbachelor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Inscription extends AppCompatActivity {

    private HelperNFT dbNFT;
    private Button btnConfirm;
    private EditText pseudo;
    private EditText password;
    private EditText repassword;
    private EditText mail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        this.pseudo = (EditText) findViewById(R.id.NewPseudo);
        this.password = (EditText) findViewById(R.id.NewPassWord);
        this.repassword = (EditText) findViewById(R.id.ValideNewPassword);
        this.btnConfirm = (Button) findViewById(R.id.ValideInscription);
        this.mail = (EditText) findViewById(R.id.Mail);
        dbNFT = new HelperNFT(this);

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = pseudo.getText().toString();
                String pwd = password.getText().toString();
                String email = mail.getText().toString();
                String repwd = repassword.getText().toString();

                if(user.equals("")||pwd.equals("")||repwd.equals("")) {
                    Toast.makeText(Inscription.this, "Veuillez compléter tous les champs 2", Toast.LENGTH_SHORT).show();
                }else{
                    if(pwd.equals(repwd)){
                        Boolean checkUser = dbNFT.checkPseudo(user);
                        if(checkUser == false){
                            Boolean insert = dbNFT.insertData(user, pwd, email);
                            if(insert==true){
                                Toast.makeText(Inscription.this, "Inscription réussie", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Inscription.this, AffichageNFT.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(Inscription.this, "Problème de connexion 2", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(Inscription.this, "L'utilisateur existe déjà", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Inscription.this, "Les mots de passe ne sont pas les mêmes", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}