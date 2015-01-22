package com.example.mk.rcwindy;

import android.content.Context;
import android.content.Intent;
import android.database.CursorIndexOutOfBoundsException;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mk.rcwindy.Data.DatabaseHandler;
import com.example.mk.rcwindy.Data.User;


public class HelloActivity extends ActionBarActivity {


    TextView passStrengthLabel;     //Wszelkie komunikaty o sile hasła czy błedach we wpisywaniu
    EditText password;
    EditText repassword;
    EditText username;
    int loggedUserId;
    int response = 0;
    //TODO:Czemu tu nie mozna dac tych Stringow??

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);


        passStrengthLabel = (TextView) findViewById(R.id.passStrengthLabel);
        password = (EditText) findViewById(R.id.password);
        repassword = (EditText) findViewById(R.id.rePassword);
        username = (EditText) findViewById(R.id.loginText);

    //    PasswordStrength ps = new PasswordStrength();
     //   ps.calcStr(repassword.getText().toString());


    }

    //TODO:Prawdopodobnie dodac wyjatki gdy nie ma zadnej bazy danych,

    public void logowanie(View view) {
        String haslo = password.getText().toString();
        String username2 = username.getText().toString();
        passStrengthLabel.setText("");


        if (checkIfUserExists() == false) {
            passStrengthLabel.setText("Podaj prawidłową nazwe użytkownika");
        } else {
            int idLogujacego;
            DatabaseHandler db = new DatabaseHandler(this);
            User user = db.userIdGetter(username2);
            idLogujacego = user.getUserID();

            user = db.getUser(idLogujacego);
            String sprawdzaneHaslo = user.getPassword();
            if (haslo.equals(sprawdzaneHaslo)) {
                loggedUserId = idLogujacego;


                //user = db.getUser(loggedUserId);                    //TOAST Do Wyswietlenia Przywitania
                // String info = "Witaj w domu: "+user.getUserName();
                // Context context = getApplicationContext();
                // int duration = Toast.LENGTH_LONG;

                // Toast toast = Toast.makeText(context, info, duration);
                // toast.show();

                Intent intent = new Intent(this, SpotActivity.class);
                intent.putExtra("logged", loggedUserId);
                Log.d("user id: ", "=" + loggedUserId);
                startActivity(intent);

            } else {
                passStrengthLabel.setText("Hasło niepoprawne");
            }


        }

        //TODO:Sypało sie ale po dodaniu Exception zaczeło działać
    }

    public boolean checkIfUserExists() {


        try {
            String username2 = username.getText().toString();
            DatabaseHandler db = new DatabaseHandler(this);
            User user = db.userIdGetter(username2);
            Log.d("Czy :", "istnieje?");
            String czyIstnieje = null;
            czyIstnieje = user.getUserName();
            Log.d("wartosc:", " user " + czyIstnieje);
            if (czyIstnieje == null) {
                return false;
            } else return true;
        } catch (CursorIndexOutOfBoundsException e) {
            return false;
        }

    }


    //TWORZENIE UŻYTKOWNIKA
    public void dodajUser(View view) {
        String haslo = password.getText().toString();
        String powtorzoneHaslo = repassword.getText().toString();
        String username2 = username.getText().toString();
        passStrengthLabel.setText("");

                                                                                      //TODO:0 Sprawdzic czy user juz istnieje
        if (powtorzoneHaslo.equals(haslo))                                            //TODO:1Dodać warunek sprawdzania siły hasła
                                                                                      //TODO:2 Dodać przejscie do nastepnej aktywnosci
        {
            PasswordStrength psc = new PasswordStrength();

            int silaHasla = psc.getPasswordStrength(repassword.getText().toString());
            if(silaHasla>=2) {

                DatabaseHandler db = new DatabaseHandler(this);
                db.addUser(new User(username2, haslo));
                User user = db.userIdGetter(username2);
                Log.d("Adding:", "new user");
                loggedUserId = user.getUserID();                                    //zdobywanie IDKey-Primary Key

                //Sprawdzenie czy User sie dodał
                user = db.getUser(loggedUserId);
                String info = "Witaj: " + user.getUserName();
                Context context = getApplicationContext();
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, info, duration);
                toast.show();
                Intent intent = new Intent(this, SpotActivity.class);
                intent.putExtra("logged", loggedUserId);
                Log.d("user id: ", "=" + loggedUserId);
                startActivity(intent);
            }
            else{
                passStrengthLabel.setText("Hasło nie jest bezpieczne, pamiętaj że hasło powinno mieć minimum 6 znaków," +
                        "oraz zawierać małe i duże znaki");
            }

        } else {

            passStrengthLabel.setText("Hasła muszą być takie same");
            passStrengthLabel.setTextColor(Color.RED);

        }

            /*                                              Wyswietlenie Userów
        Log.d("Reading: ", "Reading all contacts..");
        User user = db.getUser(1);
        String info = "Witaj: "+user.getUserName();
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, info, duration);
        toast.show();

        List<User> users = db.getAllContacts();

        for (User cn : users) {
            String log = "Id: "+cn.getUserID()+" ,Name: " + cn.getUserName() + " ,Password: " + cn.getPassword();
            // Writing Contacts to log
            Log.d("Name: ", log);
        } */


    }


    //ZMIANA WIDOKU MIEDZY TWORZENIEM USERA A LOGOWANIEM

    public void zmienXML(View view) {

        Button signupButton = (Button) findViewById(R.id.signupButton);
        EditText rePassword = (EditText) findViewById(R.id.rePassword);
        Button loginButton = (Button) findViewById(R.id.loginButton);
        Button signup = (Button) findViewById(R.id.signup);
        TextView passStrengthLabel = (TextView) findViewById(R.id.passStrengthLabel);

        loginButton.setVisibility(view.GONE);
        signupButton.setVisibility(view.GONE);
        rePassword.setVisibility(view.VISIBLE);
        signup.setVisibility(View.VISIBLE);


    }






    //Sprawdzanie Siły Hasła! 3 kroki




}
